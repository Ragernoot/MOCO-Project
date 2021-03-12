package com.example.testforcoronaapp.utils

import java.lang.StringBuilder

abstract class SomeAlgorithms {
    companion object {
        fun stringChanger(jsonObjectString: String): String {
            // Algorithm from https://stackoverflow.com/questions/57865562/java-replaceall-with-except-first-and-last-occurrence
            val sb = StringBuilder(jsonObjectString)
            val first: Int = jsonObjectString.indexOf("\"")
            val last: Int = jsonObjectString.lastIndexOf("\"")
            if (first != last) {
                var i = first + 1
                while (i < last) {
                    if (sb[i] == '\'') {
                        sb.insert(i, '\'')
                        i++
                    }
                    i++
                }
            }
            return sb.toString()
        }
    }
}
