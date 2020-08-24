package com.turtle8.room2

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.turtle8.room2.database.FragmentState
import com.turtle8.room2.database.FragmentStateDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class OneViewModel(
    private val fragmentStateDao: FragmentStateDao,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var _position:Int =
        savedStateHandle.get<Int>(key_position) ?: 0

    override fun onCleared() {
        Log.d(LOG_TAG, "onCleared...")
        super.onCleared()
    }

    val readPageNo:LiveData<Int>
    get() = getPageNo()
    private fun getPageNo():Int{
        Log.d(LOG_TAG, "getPageNo()")
        
    }
    val fragmentStateListLiveData: LiveData<List<FragmentState>>
    get() = fragmentStateDao.listStates()

    private suspend fun roomSavePage(){
        Log.d(LOG_TAG, "roomSavePage ${position}")
        withContext(Dispatchers.IO){
            fragmentStateDao.insert(FragmentState(LOG_TAG, position))
        }
    }

    var position:Int
    get()= _position
    set(value){
        _position = value
        Log.d(LOG_TAG, "set value = ${value}")
        if (value > 0) savedStateHandle.set(key_position, value)
    }
    init {
        Log.d(LOG_TAG, "init...")
    }

    companion object{
        const val LOG_TAG = "OneViewModel"
        const val key_position = "key_position"
    }
}