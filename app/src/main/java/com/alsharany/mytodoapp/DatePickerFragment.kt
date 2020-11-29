package com.alsharany.mytodoapp

import android.app.DatePickerDialog
import android.app.Dialog
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import java.util.*

private const val ARG_DATE = "date"

class DatePickerFragment : DialogFragment() {
    lateinit var str: String

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        str = arguments?.getString(ARG_DATE)!!
        val dateListener =
            DatePickerDialog.OnDateSetListener { _: DatePicker, year: Int, month: Int, day: Int ->
                val resultDate: Date = GregorianCalendar(year, month, day).time
                targetFragment?.let { fragment ->
                    if (str == "createDate")
                        (fragment as DateCallbacks).onCreateDateSelected(resultDate)
                    else if (str == "endDate")
                        (fragment as DateCallbacks).onEndDateSelected(resultDate)
                }

            }
        val calendar = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

            Calendar.getInstance()
        } else {
            TODO("VERSION.SDK_INT < N")
        }
        val date = arguments?.getSerializable(ARG_DATE) as Date

        calendar.time = date
        val initialYear = calendar.get(Calendar.YEAR)
        val initialMonth = calendar.get(Calendar.MONTH)
        val initialDay = calendar.get(Calendar.DAY_OF_MONTH)
        return DatePickerDialog(
            requireContext(),
            dateListener,
            initialYear,
            initialMonth,
            initialDay
        )
    }

    companion object {
        fun newInstance(date: Date, s: String): DatePickerFragment {

            val args = Bundle().apply {
                putSerializable(ARG_DATE, date)
                putString(ARG_DATE, s)
            }
            return DatePickerFragment().apply {
                arguments = args
            }

        }
    }

    interface DateCallbacks {
        fun onCreateDateSelected(createDate: Date)
        fun onEndDateSelected(endDate: Date)

    }
}