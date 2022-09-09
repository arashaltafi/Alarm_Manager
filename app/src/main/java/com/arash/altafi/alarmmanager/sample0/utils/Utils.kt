package com.arash.altafi.alarmmanager.sample0.utils

object Utils {

    fun setPersianDigits(src: String?): String? {
        val result = StringBuilder("")
        var unicode = 0
        if (src != null) {
            for (i in src.indices) {
                unicode = src[i].code
                if (unicode in 48..57) {
                    result.append((unicode + 1728).toChar())

                    // Log.e("unicode", unicode + "");
                    // Log.e("persian character", (unicode + 1728) + " " + (char) (unicode + 1728));
                } else {
                    result.append(src[i])
                }
            }
        }
        return result.toString()
    }

}