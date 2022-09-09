package com.arash.altafi.alarmmanager.sample0.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.arash.altafi.alarmmanager.databinding.DialogConfirmBinding
import com.arash.altafi.alarmmanager.sample0.base.BaseBottomSheetDialogFragment
import com.arash.altafi.alarmmanager.sample0.utils.setBackStackLiveData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConfirmDialogFragment : BaseBottomSheetDialogFragment<DialogConfirmBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> DialogConfirmBinding
        get() = DialogConfirmBinding::inflate

    private val args by navArgs<ConfirmDialogFragmentArgs>()

    override fun viewHandler(view: View, savedInstanceState: Bundle?) {
        binding?.apply {

            if (args.message != null)
                tvMessage.text = args.message

            btnYes.setOnClickListener {
                close(true)
            }

            btnNo.setOnClickListener {
                close(false)
            }
        }
    }

    override fun initObservers() {}

    private fun close(bool: Boolean) {
        findNavController().apply {
            setBackStackLiveData(
                "BACK_FROM_CONFIRM",
                Pair(bool, args.dataAny)
            )
        }
    }

    override fun dismiss() {
        close(false)
        super.dismiss()
    }

}