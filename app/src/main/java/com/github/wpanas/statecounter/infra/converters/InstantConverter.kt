package com.github.wpanas.statecounter.infra.converters

import androidx.room.TypeConverter
import java.time.Instant

object InstantConverter {
    @TypeConverter
    @JvmStatic
    fun fromTimestamp(timestamp: Long?): Instant? {
        return timestamp?.let {
            return Instant.ofEpochMilli(it)
        }
    }

    @TypeConverter
    @JvmStatic
    fun toTimestamp(instant: Instant?): Long? {
        return instant?.toEpochMilli()
    }
}