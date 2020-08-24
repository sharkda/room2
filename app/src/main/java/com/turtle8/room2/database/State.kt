package com.turtle8.room2.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fragment_state")
data class FragmentState(
    @PrimaryKey @ColumnInfo (name = "fragment") val fragment : String,
    @ColumnInfo(name = "page_no") var PageNo: Int
)
