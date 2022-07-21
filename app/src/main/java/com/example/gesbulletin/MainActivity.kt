package com.example.gesbulletin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.gesbulletin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    //view binding
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //handle click login
        binding.loginBtn.setOnClickListener {

        }

        //handle click register
        binding.registerBtn.setOnClickListener{

        }
    }
}