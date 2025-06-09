
package com.example.demo2.Utils

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.icu.util.Calendar
import android.view.View
import android.widget.EditText
import android.widget.RadioGroup


object Utils {

    /**
     * This is for checking input validation of the fields in the form
     */

    fun validateFields(sugarField: EditText, sleepField: EditText, activityGroup: RadioGroup): Boolean {
        var isValid = true

        val sugarText = sugarField.text.toString()
        if (sugarText.isEmpty() || sugarText.toIntOrNull() == null) {
            sugarField.error = "Please enter a valid number"
            isValid = false
        }

        val sleepText = sleepField.text.toString()
        if (sleepText.isEmpty() || sleepText.toIntOrNull() == null) {
            sleepField.error = "Please enter a valid number"
            isValid = false
        }

        if (activityGroup.checkedRadioButtonId == -1) {
            isValid = false
        }

        return isValid
    }

    fun getDefaultDate(): String {
        val calendar = Calendar.getInstance()
        return String.format("%02d-%02d-%04d", calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.YEAR))
    }

    fun getDefaultTime(): String {
        val calendar = Calendar.getInstance()
        return String.format("%02d:%02d", calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE))
    }

    /**
     * In kotlin/java everything is passed as reference type
     * Changing anywhere will have effect on everywhere
     */

    fun createTimeBox(context: Context, view: View) {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(context, { _, selectedHour, selectedMinute ->
            val selectedTime = String.format("%02d:%02d", selectedHour, selectedMinute)

            (view as EditText).setText(selectedTime)
        }, hour, minute, true)

        timePickerDialog.show()
    }

    fun createDateBox(context: Context, view: View) {
        val calendar = java.util.Calendar.getInstance()
        val year = calendar.get(java.util.Calendar.YEAR)
        val month = calendar.get(java.util.Calendar.MONTH)
        val day = calendar.get(java.util.Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(context, { _, selectedYear, selectedMonth, selectedDay ->
            val selectedDate = String.format("%02d-%02d-%04d", selectedDay, selectedMonth , selectedYear)
            (view as EditText).setText(selectedDate) // casting it into EditText object
        }, year, month, day)

        datePickerDialog.show()
    }


}
