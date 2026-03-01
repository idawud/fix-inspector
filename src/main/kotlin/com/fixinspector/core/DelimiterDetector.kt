package com.fixinspector.core

object DelimiterDetector {
    private val SOH = '\u0001'
    private val candidates = listOf('|', '^', ';', '\u0001', '\n', ' ')

    fun detect(text: String): Char {
        if (text.contains(SOH)) return SOH

        // Count occurrences of candidate delimiters in contexts like "<num>="
        val counts = candidates.associateWith { ch ->
            // split on ch and count tokens that look like tag=value
            text.split(ch).count { token -> token.trim().matches(Regex("^\\d+\\s*=.*")) }
        }

        // pick the candidate with max count
        val (bestChar, bestCount) = counts.maxByOrNull { it.value } ?: (Pair('|', 0))

        // If bestCount is zero, fallback to pipe
        return if (bestCount > 0) bestChar else '|'
    }
}

