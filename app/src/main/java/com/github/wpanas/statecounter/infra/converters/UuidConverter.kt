package com.github.wpanas.statecounter.infra.converters

import androidx.room.TypeConverter
import java.util.*

object UuidConverter {
    @TypeConverter
    @JvmStatic
    fun fromString(string: String?): UUID? = string?.let {
        return UUID.fromString(it)
    }

    @TypeConverter
    @JvmStatic
    fun toUuid(uuid: UUID?): String? = uuid?.toString()
}