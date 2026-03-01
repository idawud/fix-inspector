package com.fixinspector.core

object FixParser {
    fun parse(text: String): ParsedMessage {
        val delimiter = DelimiterDetector.detect(text)
        val tokens = FixTokenizer.tokenize(text, delimiter)
        val nodes = mutableListOf<MessageNode>()
        val issues = mutableListOf<ParseIssue>()

        var index = 0
        for (t in tokens) {
            if (t.tag == null) {
                issues.add(ParseIssue(Severity.WARNING, "Malformed token: '${t.text}'", t.startIndex))
                // skip or attempt to parse further
                continue
            }
            val spec = FixSpecLoader.getField(t.tag)
            val pf = ParsedField(t.tag, t.rawValue, spec, index++)
            nodes.add(pf)
        }

        return ParsedMessage(nodes, issues)
    }
}

