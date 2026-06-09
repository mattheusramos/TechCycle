package com.trabalho.elixo.utils

object MaskUtils {
    fun formatarTelefone(v: String): String {
        val digits = v.filter { it.isDigit() }

        val maxDigits = if (digits.length > 11) digits.substring(0, 11) else digits

        return buildString {
            val len = maxDigits.length
            if (len > 0) append("(")
            if (len <= 2) {
                append(maxDigits)
            } else {
                append(maxDigits.substring(0, 2)).append(") ")
                if (len <= 7) {
                    append(maxDigits.substring(2))
                } else {
                    append(maxDigits.substring(2, 7)).append("-").append(maxDigits.substring(7))
                }
            }
        }
    }
}