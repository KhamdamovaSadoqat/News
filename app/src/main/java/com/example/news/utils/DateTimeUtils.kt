package com.example.news.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateTimeUtils {

    /**
     * Formats date which is Date format to String format
     *
     * @param date date which needs to be converted to String
     * @param format format which needs to be used
     *
     * @return String?
     */
    fun dateToText(date: Date?, format: String): String? {
        return if (date != null) {
            val sdf = SimpleDateFormat(format, Locale.getDefault())
            sdf.format(date)
        } else
            null

    }

    /**
     * Formats the String formatted date to Date format
     *
     * @param dateText date in the String format
     * @param format format which needs to be used
     *
     * @return Date?
     */
    fun textToDate(dateText: String?, format: String): Date? {
        return if (dateText != null) {
            val sdf = SimpleDateFormat(format, Locale.getDefault())
            try {
                sdf.parse(dateText)
            } catch (e: ParseException) {
                null
            }
        } else
            null
    }

    /**
     * Formats the String formatted date to another String format
     * 2021-11-29T13:22:31
     * @param dateText date in the String format
     *
     * @return String?
     * DD.MM.YYYY hh.mm
     */
    fun stringToDateString(dateText: String): String{
        val date = dateText.split("T")
        val day = date[0].split("-")
        val time = date[1].split(":")
        return "${day[2]}.${day[1]}.${day[0]} ${time[0]}:${time[1]}"
    }
}