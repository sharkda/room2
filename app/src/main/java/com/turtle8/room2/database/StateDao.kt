package com.turtle8.room2.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FragmentStateDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(fragmentState:FragmentState)

    @Update
    fun update(fragmentState: FragmentState)

    @Query("SELECT page_no FROM fragment_state where fragment = :fragment")
    fun getPageNo(fragment:String):Int

    @Delete
    fun delete(fragmentState: FragmentState)

    @Query("DELETE from FRAGMENT_STATE where fragment = :fragment")
    fun deleteFragment(fragment:String)

    @Query("DELETE FROM fragment_state")
    fun eraseState()

    @Query("SELECT * from fragment_state")
    fun listStates(): LiveData<List<FragmentState>>
}