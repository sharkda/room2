package com.turtle8.room2.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.turtle8.room2.database.FragmentStateDao
import java.lang.IllegalArgumentException

class OneViewModelFactory(private val fragmentStateDao:FragmentStateDao):
    ViewModelProvider.Factory{
    @Suppress("unchecked_cast")
    override fun<T: ViewModel?>create(modelClass:Class<T>):T{
        if (modelClass.isAssignableFrom(OneViewModel::class.java)){
            //return QuizViewModel(_charCursor, mpsCharDao,mpsCharAltDao,  application) as T
            return OneViewModel(fragmentStateDao) as T
        }
        throw IllegalArgumentException("unknown ViewModel class")
    }
}
