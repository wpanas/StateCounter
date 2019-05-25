package com.github.wpanas.statecounter.action

import androidx.room.TypeConverter
import com.github.wpanas.statecounter.action.Action.ActionType

object ActionTypeConverter {
    private val map = ActionType.values().associateBy(ActionType::ordinal)

    @TypeConverter
    @JvmStatic
    fun fromInt(type: Int?): ActionType? = map[type]

    @TypeConverter
    @JvmStatic
    fun fromActionType(type: ActionType?) = type?.ordinal
}
