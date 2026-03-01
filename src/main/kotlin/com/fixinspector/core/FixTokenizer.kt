package com.fixinspector.core

data class Token(val tag: Int?, val rawValue: String, val text: String, val startIndex: Int, val endIndex: Int)

object FixTokenizer {
    private val tagValueRegex = Regex("^(\\d+)\\s*=(.*)")

    fun tokenize(text: String, delimiter: Char): List<Token> {
        val tokens = mutableListOf<Token>()
        var index = 0

        // Special-case: if delimiter is newline, split on lines but allow multiple tokens per line
        val rawTokens = if (delimiter == '\n') text.split(Regex("\\r?\\n")) else text.split(delimiter)

        for (raw in rawTokens) {
            val start = index
            val tokenText = raw
            val match = tagValueRegex.find(tokenText)
            if (match != null) {
                val tagStr = match.groupValues[1]
                val value = match.groupValues[2]
                val tagInt = tagStr.toIntOrNull()
                tokens.add(Token(tagInt, value, tokenText, start, start + tokenText.length))
            } else {
                // Non-conforming token - rawValue same as text
                tokens.add(Token(null, tokenText, tokenText, start, start + tokenText.length))
            }
            index += tokenText.length + 1 // +1 for delimiter char assumed
        }

        return tokens
    }
}

