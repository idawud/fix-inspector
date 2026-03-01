package com.fixinspector.core

import java.io.InputStreamReader
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

object FixSpecLoader {
    private val mapper = jacksonObjectMapper()

    // Simple in-memory map of tag -> FieldSpec
    private var fields: Map<Int, FieldSpec> = emptyMap()

    fun loadFromResource(path: String = "/fix-spec.json") {
        val stream = FixSpecLoader::class.java.getResourceAsStream(path)
            ?: throw IllegalStateException("Resource not found: $path")
        val text = InputStreamReader(stream).readText()
        // parse minimal structure
        val node: Map<String, Any> = mapper.readValue(text)
        val fieldsNode = node["fields"] as? Map<String, Any> ?: emptyMap()
        val parsed = mutableMapOf<Int, FieldSpec>()
        for ((tagStr, v) in fieldsNode) {
            val tag = tagStr.toIntOrNull() ?: continue
            val obj = v as? Map<String, Any> ?: continue
            val name = obj["name"] as? String ?: "Unknown"
            val type = obj["type"] as? String
            val desc = obj["description"] as? String
            val enumsRaw = obj["enums"] as? Map<String, Any>
            val enums = enumsRaw?.mapValues { (_, ev) ->
                val eObj = ev as? Map<String, Any>
                val eName = eObj?.get("name") as? String ?: ""
                val eDesc = eObj?.get("description") as? String
                EnumSpec(eName, eDesc)
            }
            parsed[tag] = FieldSpec(tag, name, type, desc, enums)
        }

        fields = parsed.toMap()
    }

    fun getField(tag: Int): FieldSpec? = fields[tag]
}

