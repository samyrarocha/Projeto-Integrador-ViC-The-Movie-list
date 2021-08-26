package com.example.projeto_integrador.features.feed.presentation

import android.app.AlertDialog
import android.app.Dialog
import android.app.TaskStackBuilder.create
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.widget.ImageViewCompat
import androidx.fragment.app.DialogFragment
import com.example.projeto_integrador.R
import com.google.android.material.button.MaterialButton
import java.lang.IllegalStateException

class ErrorDialogFragment: DialogFragment(R.layout.error_layout) {

    var listener: Listener? = null

    interface Listener {
        fun onDialogButtonClicked()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.Theme_App_Dialog_FullScreen)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        requireDialog().window?.setWindowAnimations(R.style.DialogAnimation)
        val closeButton: AppCompatImageView = view.findViewById(R.id.closeErrorDialog)
        closeButton.setOnClickListener {
            dismiss()
        }

        val tryAgainButton: MaterialButton = view.findViewById(R.id.tryAgainButton)
        tryAgainButton.setOnClickListener {
            listener?.onDialogButtonClicked()
            dismiss()
        }
    }

}