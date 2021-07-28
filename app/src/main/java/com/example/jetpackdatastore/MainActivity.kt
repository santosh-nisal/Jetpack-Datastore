package com.example.jetpackdatastore

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import com.example.jetpackdatastore.databinding.ActivityMainBinding
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {
    private lateinit var activityMainActivity: ActivityMainBinding
    private lateinit var appPreferences: AppPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainActivity = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainActivity.root)
        initUI()
        initListeners()
    }

    @SuppressLint("SetTextI18n")
    private fun initUI() {
        appPreferences = AppPreferences(this)

        appPreferences.studentName.asLiveData().observe(this) { empName ->
            activityMainActivity.tvLastEnteredName.text = "Last Entered Name: $empName"
        }
    }

    private fun initListeners() {
        activityMainActivity.btnSaveName.setOnClickListener {
            val empName = activityMainActivity.etStudentName.text.toString().trim()
            if (empName.isEmpty()) {
                Toast.makeText(this@MainActivity, "Name cannot be empty", Toast.LENGTH_SHORT).show()
            } else {
                runBlocking {
                    appPreferences.saveStudentName(empName)
                }
            }
        }
    }
}