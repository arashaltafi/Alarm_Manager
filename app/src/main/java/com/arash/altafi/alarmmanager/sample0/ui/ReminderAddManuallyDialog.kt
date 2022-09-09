package com.arash.altafi.alarmmanager.sample0.ui

import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.arash.altafi.alarmmanager.R
import com.arash.altafi.alarmmanager.databinding.DialogAlarmAddManuallyBinding
import com.arash.altafi.alarmmanager.sample0.base.BaseBottomSheetDialogFragment
import com.arash.altafi.alarmmanager.sample0.repository.ReminderEntity
import com.arash.altafi.alarmmanager.sample0.utils.*
import com.arash.altafi.alarmmanager.sample0.viewModel.ReminderViewModel
import dagger.hilt.android.AndroidEntryPoint
import saman.zamani.persiandate.PersianDate
import java.util.*

@AndroidEntryPoint
class ReminderAddManuallyDialog : BaseBottomSheetDialogFragment<DialogAlarmAddManuallyBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> DialogAlarmAddManuallyBinding
        get() = DialogAlarmAddManuallyBinding::inflate

    private val reminderViewModel by viewModels<ReminderViewModel>()

    private val args by navArgs<ReminderAddManuallyDialogArgs>()

    //    private var onTime = System.currentTimeMillis() + 3600000L // one hour later
    private var onTime: Long = 0L

    private val currentTimeMs: Long
        get() = System.currentTimeMillis()

    override fun viewHandler(view: View, savedInstanceState: Bundle?) {

        binding?.apply {

            if (args.isEdit) {
                btnYes.text = getString(R.string.edit)
                tvTitle.text = getString(R.string.edit_reminder)
                etTitle.editText?.setText(args.title)
                etDate.editText?.setText(PersianDate(args.datetime).getDateString())
                etTime.editText?.setText(PersianDate(args.datetime).getTimeString())
                onTime = args.datetime
            }
            else {
                onTime = System.currentTimeMillis()
                etDate.editText?.setText(PersianDate(onTime).getDateString())
                etTime.editText?.setText(PersianDate(onTime).getTimeString())
            }

            etDate.editText?.setOnClickListener {
                activityContext.showCalendarDialog {
                    if (it != null) {
                        val pd = PersianDate(onTime)
                        TimePickerDialog(
                            requireContext(),
                            { _, hourOfDay, minute ->
                                onTime = PersianDate(it).time + (hourOfDay * 3600000L) + (minute * 60000L)
                                PersianDate(it).time
                                etDate.editText?.setText(PersianDate(onTime).getDateString())
                                etTime.editText?.setText(PersianDate(onTime).getTimeString())
                            },
                            pd.hour,
                            pd.minute,
                            true
                        ).show()
                    }
                }
            }

            etTime.editText?.setOnClickListener {
                val pd = PersianDate(onTime)
                TimePickerDialog(
                    requireContext(),
                    { _, hourOfDay, minute ->
                        onTime = PersianDate(onTime).time + (hourOfDay * 3600000L) + (minute * 60000L)
                        etTime.editText?.setText(PersianDate(onTime).getTimeString())
                    },
                    pd.hour,
                    pd.minute,
                    true
                ).show()
            }

            btnNo.setOnClickListener {
                close(false)
            }

            btnYes.setOnClickListener {
                val title = etTitle.textString()
                val pd = PersianDate(onTime)

                if (title.isEmpty()) {
                    Toast.makeText(context, R.string.please_enter_title , Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                if (onTime <= System.currentTimeMillis()) {
                    Toast.makeText(context, R.string.set_time_error_past , Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                val titleNotification = PersianDate().dayName() +
                        " ${PersianDate().shDay} " +
                        PersianDate().monthName() +
                        " ${PersianDate().shYear}, ساعت " +
                        PersianDate().getTimeString()

                reminderViewModel.checkPermission {
                    if (it) {

                        if (args.isEdit)
                            reminderViewModel.remove(args.uniqueId, currentTimeMs, true)

                        reminderViewModel.add(
                            ReminderEntity(
                                uniqueId = UUID.randomUUID().toString(),
                                unixTime = onTime,
//                                title = getString(R.string.reminder),
                                title = titleNotification,
                                text = title,
                                channelId = "com.arash.altafi.alarmmanager"
                            ), currentTimeMs
                        )

                        lifecycle.addObserver(object : LifecycleEventObserver {
                            override fun onStateChanged(
                                source: LifecycleOwner,
                                event: Lifecycle.Event
                            ) {
                                if (event == Lifecycle.Event.ON_RESUME) {
                                    val popMessage = if (args.isEdit)
                                        getString(R.string.reminder_edit_message).applyValue(
                                            pd.getDateStringWithClock()
                                        )
                                    else
                                        getString(R.string.reminder_set_message).applyValue(
                                            pd.getDateStringWithClock()
                                        )

                                    popSuccess(popMessage)

                                    close(true)
                                }
                            }

                        })

                    } else {
                        popError(R.string.permission_nedded_message)
                    }
                }
            }

        }
    }

    private fun close(bool: Boolean) {
        findNavController().apply {
            setBackStackLiveData(
                "BACK_FROM_REMINDER_ADD",
                bool
            )
        }
    }

    override fun dismiss() {
        reminderViewModel.unregister()
        close(false)
        super.dismiss()
    }


}