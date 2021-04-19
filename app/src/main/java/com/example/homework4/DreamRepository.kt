package com.example.homework4

import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

class DreamRepository (private val dreamDAO: DreamDAO) {

    val getAllDreams: Flow<List<Dream>> = dreamDAO.getAllDreams()

    suspend fun insert (dream:Dream){
        dreamDAO.insert(dream)
    }

    suspend fun updateDream(id:Int, title:String, content:String, reflection:String, emotion:String){
        dreamDAO.updateDream(id, title, content, reflection, emotion)
    }

    suspend fun delete(id:Int){
        dreamDAO.delete(id)
    }

    fun getDreamById(id:Int):Flow<Dream>{
        return dreamDAO.getDreamById(id)
    }
}