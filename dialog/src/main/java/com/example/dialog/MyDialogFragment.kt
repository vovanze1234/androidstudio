package com.example.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.annotation.NonNull
import androidx.fragment.app.DialogFragment

class MyDialogFragment : DialogFragment() {
    @NonNull
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle("Здравствуй МИРЭА!")
            .setMessage("Успех близок?")
            .setPositiveButton("Иду дальше", DialogInterface.OnClickListener { dialog, id ->
                (activity as? MainActivity)?.onOkClicked()
            })
            .setNeutralButton("На паузе", DialogInterface.OnClickListener { dialog, id ->
                (activity as? MainActivity)?.onNeutralClicked()
            })
            .setNegativeButton("Нет", DialogInterface.OnClickListener { dialog, id ->
                (activity as? MainActivity)?.onCancelClicked()
            })
        return builder.create()
    }
}
