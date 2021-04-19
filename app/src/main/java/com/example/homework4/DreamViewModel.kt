package com.example.homework4

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class DreamViewModel (private val repository: DreamRepository) : ViewModel() {

    val getAllDreams: LiveData<List<Dream>> = repository.getAllDreams.asLiveData()

    fun insert (dream:Dream) = viewModelScope.launch{
        repository.insert(dream)
    }

    fun updateDream(id:Int, title:String, content:String, reflection:String, emotion:String) = viewModelScope.launch{
        repository.updateDream(id, title, content, reflection, emotion)
    }

    fun delete(id:Int) = viewModelScope.launch{
        repository.delete(id)
    }

    fun getDreamById(id:Int): LiveData<Dream> = repository.getDreamById(id).asLiveData()
}
class DreamViewModelFactory(private val repository: DreamRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DreamViewModel::class.java)) {
            return DreamViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown view model class")
    }

}