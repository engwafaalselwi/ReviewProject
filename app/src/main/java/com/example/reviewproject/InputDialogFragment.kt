package com.example.reviewproject
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.CheckBox
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import java.util.*

class InputDialogFragment:DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view=activity?.layoutInflater?.inflate(R.layout.new_dailog_student,null)
        val nameEditText=view?.findViewById(R.id.de_name_std) as EditText
        val numEditText=view?.findViewById(R.id.de_num_std) as EditText
        val passCheckBox=view?.findViewById(R.id.student_pass) as CheckBox


        return AlertDialog.Builder(requireContext(),R.style.ThemeOverlay_AppCompat_Dialog_Alert)
            .setTitle("Add New Student")
            .setView(view)
            .setPositiveButton("Add"){ dialog,_ ->
                val std=Students(UUID.randomUUID(),
                    nameEditText.text.toString(),
                    numEditText.text.toString().toInt(),
                    passCheckBox.isChecked)
                targetFragment?.let { fragment ->
                    (fragment as Callbacks).onStudentAdded(std)
                }
            }.setNegativeButton("Cancel"){dialog,_ ->
                dialog.cancel()
            }.create()

    }

    interface Callbacks {
        fun onStudentAdded(student: Students)
        fun onStudentDeleted(item : Int)
    }
}