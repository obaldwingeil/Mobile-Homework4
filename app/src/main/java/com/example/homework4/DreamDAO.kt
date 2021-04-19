package com.example.homework4

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DreamDAO {
    @Query("SELECT * FROM dream_table ORDER BY id ASC")
    fun getAllDreams() : Flow<List<Dream>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(dream:Dream)

    @Query("UPDATE dream_table SET title=:title, content=:content, reflection=:reflection, emotion=:emotion WHERE id=:id")
    suspend fun updateDream(id:Int, title:String, content:String, reflection:String, emotion:String)

    @Query("DELETE FROM dream_table WHERE id=:id")
    suspend fun delete(id:Int)

    @Query("SELECT * FROM dream_table WHERE id=:id")
    fun getDreamById(id:Int) : Flow<Dream>

}