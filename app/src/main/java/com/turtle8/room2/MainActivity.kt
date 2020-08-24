package com.turtle8.room2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log

class MainActivity : AppCompatActivity() {

    companion object{
        const val LOG_TAG = "MainActivity"
        const val Key_Page_No = "Key_Page_No"
    }
    private var pageNo:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState != null){
            pageNo = savedInstanceState.getInt(Key_Page_No) ?: 0
            Log.d(LOG_TAG, "onCreate ${Key_Page_No} reads ${pageNo}")
        }else{
            Log.d(LOG_TAG, "onCreate savedInstanceState is null !")
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        Log.d(LOG_TAG, "onSaveInstanceState ${pageNo}")
        outState.putInt(Key_Page_No, pageNo)
        super.onSaveInstanceState(outState)
    }

}