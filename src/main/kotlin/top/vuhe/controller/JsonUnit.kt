package top.vuhe.controller

import com.google.gson.GsonBuilder

object JsonUnit {
    private val gson = GsonBuilder()
        .disableHtmlEscaping()
        .setPrettyPrinting()
        .create()

    fun toJson(obj: Any): String {
        return gson.toJson(obj)
    }

    fun <T> fromJson(str: String, clazz: Class<T>): T {
        return gson.fromJson(str, clazz)
    }
}