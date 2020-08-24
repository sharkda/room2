package com.turtle8.room2.viewModels

import android.util.Log
import androidx.lifecycle.*
import com.turtle8.room2.database.FragmentState
import com.turtle8.room2.database.FragmentStateDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OneViewModel(
    private val fragmentStateDao: FragmentStateDao
    //private val savedStateHandle: SavedStateHandle
) : ViewModel() {

//    private var _position:Int =
//        savedStateHandle.get<Int>(key_position) ?: 0

    private var _posMutableLiveData = MutableLiveData<Int>()
    fun setPage(value:Int){
        _posMutableLiveData.value = value
        viewModelScope.launch {
            insertPage(value)
        }
    }
    private suspend fun insertPage(value:Int){
        withContext(Dispatchers.IO){
            fragmentStateDao.insert(
                FragmentState(LOG_TAG, value)
            )
        }
    }
    val pagePositionLiveData:LiveData<Int>
    get() = _posMutableLiveData


    init {
        Log.d(LOG_TAG, "init...")
        viewModelScope.launch {
            _posMutableLiveData.value = dbGetPageNo()
        }
    }
    override fun onCleared() {
        Log.d(LOG_TAG, "onCleared...")
        viewModelScope.launch {
            fragmentStateDao.insert(FragmentState(LOG_TAG, _posMutableLiveData.value!!))
        }
        super.onCleared()
    }

    private suspend fun dbGetPageNo():Int{
        var pageNo:Int
        return withContext(Dispatchers.IO){
            pageNo = fragmentStateDao.getPageNo(LOG_TAG) ?: 0
            pageNo
        }
    }
    val fragmentStateListLiveData: LiveData<List<FragmentState>>
    get() = fragmentStateDao.listStates()

    private suspend fun roomSavePage(){
        Log.d(LOG_TAG, "roomSavePage ${_posMutableLiveData.value}")
        withContext(Dispatchers.IO){
            fragmentStateDao.insert(FragmentState(LOG_TAG, _posMutableLiveData.value!!))
        }
    }

//    var position:Int
//    get()= _position
//    set(value){
//        _position = value
//        Log.d(LOG_TAG, "set value = ${value}")
//        if (value > 0) savedStateHandle.set(key_position, value)
//    }


    companion object{
        const val LOG_TAG = "OneViewModel"
        const val key_position = "key_position"
    }
}