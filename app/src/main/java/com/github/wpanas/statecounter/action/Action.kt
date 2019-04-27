package com.github.wpanas.statecounter.action

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant
import java.util.*

@Entity(tableName = "actions")
data class Action(@PrimaryKey val id: UUID, val createdAt: Instant, val value: Int) {
    companion object {
        fun of(value: Int): Action =
            Action(UUID.randomUUID(), Instant.now(), value)
    }
}