package com.zitherharp.music.core

import com.zitherharp.music.model.Video
import org.json.JSONArray
import org.json.JSONObject
import java.io.Serializable
import java.net.URL

abstract class Spreadsheet(val id: String): Serializable {
    protected lateinit var chineseName: String
    protected lateinit var vietnameseName: String

    protected lateinit var chineseDescription: String
    protected lateinit var vietnameseDescription: String

    companion object {
        const val EMPTY_CHAR = ""
        const val SPACE_CHAR = " "
        const val SPLIT_CHAR = "/"
        const val CONCAT_CHAR = " - "
        const val COMBINE_CHAR = " & "

        const val ID = 0
        const val VIETNAMESE_NAME = 2
        const val CHINESE_NAME = 3
        const val VIETNAMESE_DESCRIPTION = 4
        const val CHINESE_DESCRIPTION = 5

        lateinit var jsonValue: JSONArray

        fun getJsonValues(name: String): JSONArray {
            val url = "https://sheets.googleapis.com/v4/spreadsheets/" +
                    "1znQOtTDJz0UqDs0uB2MQZV3wN0l_J0TrU44d9chH2SI/values/" +
                    "$name?key=AIzaSyAD91OiEeWRoqhsw0peq94qg5joZe47r_s"
            val jsonString = URL(url).readText()
            val jsonObject = JSONObject(jsonString)
            return jsonObject.getJSONArray("values")
        }

        fun JSONArray.requireLong(index: Int): Long = try { getLong(index) } catch (e: Exception) { 0L }
        fun JSONArray.requireString(index: Int): String = try { getString(index) } catch (e: Exception) { EMPTY_CHAR }

        fun List<Spreadsheet>.getId(splitChar: String = SPLIT_CHAR): String {
            var id = EMPTY_CHAR
            forEach {
                id += it.id + splitChar
            }
            return id.removeSuffix(splitChar)
        }

        fun List<Spreadsheet>.getName(language: Language, splitChar: String = SPLIT_CHAR): String {
            var name = EMPTY_CHAR
            forEach {
                name += splitChar + when (language) {
                    Language.CHINESE -> it.chineseName
                    Language.VIETNAMESE -> it.vietnameseName
                }
            }
            return name.removePrefix(splitChar)
        }
    }

    fun getName(language: Language) = when (language) {
        Language.CHINESE -> chineseName
        Language.VIETNAMESE -> vietnameseName
    }

    fun getDescription(language: Language) = when (language) {
        Language.CHINESE -> chineseDescription
        Language.VIETNAMESE -> vietnameseDescription
    }
}