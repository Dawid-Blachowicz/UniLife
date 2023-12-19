package com.blach.unilife.ui.utils

import android.content.Context
import com.blach.unilife.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object DateFormatter {
    fun getMonthNameInPolish(context: Context, monthNumber: Int): String {
        return when (monthNumber) {
            1 -> context.getString(R.string.month_january)
            2 -> context.getString(R.string.month_february)
            3 -> context.getString(R.string.month_march)
            4 -> context.getString(R.string.month_april)
            5 -> context.getString(R.string.month_may)
            6 -> context.getString(R.string.month_june)
            7 -> context.getString(R.string.month_july)
            8 -> context.getString(R.string.month_august)
            9 -> context.getString(R.string.month_september)
            10 -> context.getString(R.string.month_october)
            11 -> context.getString(R.string.month_november)
            12 -> context.getString(R.string.month_december)
            else -> context.getString(R.string.unknown)
        }
    }

    fun getWeekDayNameInPolish(context: Context, dayNumber: Int): String {
        return when(dayNumber) {
            1 -> context.getString(R.string.monday)
            2 -> context.getString(R.string.tuesday)
            3 -> context.getString(R.string.wednesday)
            4 -> context.getString(R.string.thursday)
            5 -> context.getString(R.string.friday)
            6 -> context.getString(R.string.saturday)
            7 -> context.getString(R.string.sunday)
            else -> context.getString(R.string.unknown)
        }
    }

    fun getDateInDayMonthYear(date: LocalDate): String{
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        return date.format(formatter)
    }
}
