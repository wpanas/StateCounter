package com.github.wpanas.statecounter.action

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ActionDao {

    @Query("SELECT * FROM actions ORDER BY createdAt DESC")
    fun getAllActions(): LiveData<List<Action>>

    @Query("SELECT * FROM actions WHERE type = :type ORDER BY createdAt DESC")
    fun getActionsByType(type: Action.ActionType): LiveData<List<Action>>

    @Insert
    fun insert(action: Action)

    @Delete
    fun delete(action: Action)
}