package com.example.gesbulletin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class PdfListTeacherActivity : AppCompatActivity() {

    private var categoryId = ""
    private var category = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf_list_teacher)

        val intent = intent
        categoryId = intent.getStringExtra("categoryId")!!
        category = intent.getStringExtra("category")!!
    }
}