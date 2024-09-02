package com.localmanagestore.CommonUtils.Utils.Strings

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class UtilsStrings {
    fun getDateCurrentToString(format: String = "yyyy/MM/dd"): String {
        val sdf = SimpleDateFormat(format)
        val currentDate = sdf.format(Date())
        return currentDate
    }
}