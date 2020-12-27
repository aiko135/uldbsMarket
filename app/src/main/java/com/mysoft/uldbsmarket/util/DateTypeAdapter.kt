package com.mysoft.uldbsmarket.util

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import java.util.*

class DateTypeAdapter : TypeAdapter<Date>() {
    override fun write(out: JsonWriter?, value: Date?) {
        if (value == null)
            out!!.nullValue();
        else
            out!!.value(value.time / 1000);
    }

    override fun read(`in`: JsonReader?): Date {
        return if (`in` != null) Date(`in`.nextLong() * 1000) else Date(0L)
    }
}