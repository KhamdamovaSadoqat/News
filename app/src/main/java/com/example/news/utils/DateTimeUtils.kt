package com.example.news.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateTimeUtils {

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