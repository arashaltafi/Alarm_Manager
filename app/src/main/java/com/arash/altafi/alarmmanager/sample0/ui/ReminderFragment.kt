package com.arash.altafi.alarmmanager.sample0.ui

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.arash.altafi.alarmmanager.R
import com.arash.altafi.alarmmanager.databinding.FragmentReminderBinding
import com.arash.altafi.alarmmanager.sample0.base.BaseFragment
import com.arash.altafi.alarmmanager.sample0.utils.*
import com.arash.altafi.alarmmanager.sample0.viewModel.ReminderViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ReminderFragment : BaseFragment<FragmentReminderBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentReminderBinding
        get() = FragmentReminderBinding::inflate

    private var uniqueId : String ?= null
    private var title : String ?= null
    private var unixTime : Long ?= null

    @Inject
    lateinit var reminderAdapter: ReminderAdapter

    private val reminderViewModel by activityViewModels<ReminderViewModel>()

    private val currentTimeMs: Long
        get() = System.currentTimeMillis()

    override fun viewHandler(view: View, savedInstanceState: Bundle?) {

        reminderViewModel.getAll(currentTimeMs)

        binding?.apply {

            //set refresh
            srReminder.setOnRefreshListener {

                binding?.rvReminder?.adapter = null

                reminderAdapter.apply {
                    onClickListener = {
                        title = it.text
                        uniqueId = it.uniqueId
                        unixTime = it.unixTime
                    }
                    onClickListenerView = {
                        popupWindowAdapter(it)
                    }
                }.also {
                    binding?.rvReminder?.adapter = it
                }

                srReminder.apply {
                    if (isRefreshing)
                        isRefreshing = false
                }

            }

            fabAdd.setOnClickListener {
                findNavController().navigate(
                    R.id.reminderAddManuallyDialog,
                    ReminderAddManuallyDialogArgs(
                        isEdit = false,
                        0,
                        "",
                        ""
                    ).toBundle()
                )
            }

        }

        reminderAdapter.apply {
            onClickListener = {
                title = it.text
                uniqueId = it.uniqueId
                unixTime = it.unixTime
            }
            onClickListenerView = {
                popupWindowAdapter(it)
            }
        }.also {
            binding?.rvReminder?.adapter = it
        }

        //remove all out time reminders
        reminderViewModel.removeOutTime(currentTimeMs - 2592000000)

    }

    private fun popupWindow(view: View) {
        PopupUtil.showPopup(
            view,
            listOf(
                PopupUtil.PopupItem(
                    R.drawable.ic_delete_red,
                    getString(R.string.delete_all)
                ) {
                    deleteAllReminder()
                }
            ),
            Gravity.BOTTOM.or(Gravity.END),
            setTint = false
        )
    }

    private fun popupWindowAdapter(view: View) {
        PopupUtil.showPopup(
            view,
            listOf(
                PopupUtil.PopupItem(
                    R.drawable.ic_edit,
                    getString(R.string.edit)
                ) {
                    editReminder()
                },
                PopupUtil.PopupItem(
                    R.drawable.ic_delete_red,
                    getString(R.string.remove)
                ) {
                    deleteReminder(title!!, uniqueId!!)
                }
            ),
            Gravity.BOTTOM.or(Gravity.END),
            setTint = false
        )
    }

    private fun editReminder() {
        findNavController().navigate(
            R.id.reminderAddManuallyDialog,
            ReminderAddManuallyDialogArgs(
                isEdit = true,
                title = title!!,
                datetime = unixTime!!,
                uniqueId = uniqueId!!
            ).toBundle()
        )
    }

    private fun deleteAllReminder() {
        if (reminderViewModel.liveReminderList.value?.size!! >= 1)
            showRemoveDialog(getString(R.string.delete_all_reminder_confirm) , "deleteAll")
        else
            popError(getString(R.string.not_reminder_to_delete))
    }

    private fun deleteReminder(title: String , id: String) {
        showRemoveDialog(title, id)
    }

    override fun initObservers() {
        super.initObservers()
        reminderViewModel.liveReminderList.observe(this) {
            reminderAdapter.submitList(it)
        }
    }

    override fun initBackStackObservers() {
        findNavController().getBackStackLiveData<Pair<Boolean, Any>>("BACK_FROM_CONFIRM")
            ?.observe(this) {
                if (it.first) {
                    when(it.second) {
                        "deleteAll" -> {
                            reminderViewModel.removeAll(0)
                        }
                        else -> {
                            reminderViewModel.remove(it.second.toString(), currentTimeMs, true)
                        }
                    }

                }

                requireActivity().intent = Intent() // clear intent
            }

        findNavController().getBackStackLiveData<Boolean>("BACK_FROM_REMINDER_ADD")
            ?.observe(this) {
                if (it) {
                    runAfter(200L, {
                        reminderViewModel.getAll(currentTimeMs)
                    })
                }

                requireActivity().intent = Intent() // clear intent

            }
    }

    private fun showRemoveDialog(title: String, id: String) {
        val message: String = when (title) {
            getString(R.string.delete_all_reminder_confirm) -> getString(R.string.delete_all_reminder_confirm)
            else -> getString(R.string.reminder_do_remove).applyValue(title)
        }
        findNavController().navigate(
            R.id.confirmDialogFragment, ConfirmDialogFragmentArgs(message, id).toBundle()
        )
    }

}
