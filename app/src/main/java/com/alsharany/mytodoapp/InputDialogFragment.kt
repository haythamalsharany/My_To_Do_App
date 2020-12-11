package com.alsharany.mytodoapp

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.provider.Settings.System.DATE_FORMAT
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

private const val DIALOG_CrEATE_DATE = "DialogCreateDate"
private const val DIALOG_END_Date = "DialogEndDate"
private const val REQUEST_CREATE_DATE = 0
private const val REQUEST_END_DATE = 1

open class InputDialogFragment : DialogFragment(), DatePickerFragment.DateCallbacks {
    lateinit var task: Task
    lateinit var taskCreateDateButton: Button
    lateinit var taskEndDateButton: Button
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        task = Task()
        val v = activity?.layoutInflater?.inflate(R.layout.input_dialog_fragment, null)
        val taskTitleEditText = v?.findViewById(R.id.task_title_ed) as EditText
        val taskDescriptionEditeText = v.findViewById(R.id.describ_ed) as EditText
        taskCreateDateButton = v.findViewById(R.id.create_date_btn) as Button
        taskEndDateButton = v.findViewById(R.id.end_date_btn) as Button


        return AlertDialog.Builder(requireContext(), R.style.ThemeOverlay_AppCompat_Dialog_Alert)
            .setView(v)
            .setTitle("create new task")
            .setPositiveButton("Add") { dialog, _ ->
                val t = Task(
                    UUID.randomUUID(),
                    taskTitleEditText.text.toString(),
                    taskDescriptionEditeText.text.toString(),
                    task.createDate,
                    task.endDate,

                )
                targetFragment?.let {
                    (it as InputCallbacks).onTaskAdd(t)
                }

            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.cancel()

            }.create()
    }

    @SuppressLint("SimpleDateFormat")
    fun updateUi() {
        taskCreateDateButton.text= DateFormat.getDateInstance(DateFormat.LONG).format(this.task.createDate)
        taskEndDateButton.text= DateFormat.getDateInstance(DateFormat.LONG).format(this.task.endDate)
        //taskCreateDateButton.text = SimpleDateFormat(DATE_FORMAT).format(task.createDate)
       // taskEndDateButton.text = SimpleDateFormat(DATE_FORMAT).format(task.endDate)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        updateUi()

    }

    override fun onStart() {
        super.onStart()
        taskCreateDateButton.setOnClickListener {
            DatePickerFragment.newInstance(task.createDate, "createDate").apply {
                setTargetFragment(this@InputDialogFragment, REQUEST_CREATE_DATE)
                show(this@InputDialogFragment.requireFragmentManager(), DIALOG_CrEATE_DATE)
            }
        }
        taskEndDateButton.setOnClickListener {
            DatePickerFragment.newInstance(task.endDate, "endDate").apply {
                setTargetFragment(this@InputDialogFragment, REQUEST_END_DATE)
                show(this@InputDialogFragment.requireFragmentManager(), DIALOG_CrEATE_DATE)
            }
        }
    }

    interface InputCallbacks {
        fun onTaskAdd(task: Task)

    }

    companion object {
        fun newInstance(): InputDialogFragment {

            return InputDialogFragment()
        }
    }

    override fun onCreateDateSelected(createDate: Date) {
        task.createDate = createDate
        updateUi()

    }

    override fun onEndDateSelected(endDate: Date) {
        task.endDate = endDate
        updateUi()
    }


}