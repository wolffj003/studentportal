package com.example.studentportal

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_portal.*

const val EXTRA_PORTAL = "EXTRA_PORTAL"


class AddPortal : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_portal)

        initViews()
    }


    private fun initViews() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Add a new portal"

        btnConfirm.setOnClickListener{ onConfirmClick() }
    }


    private fun onConfirmClick() {
        val etTitleText: String = etTitle.text.toString() // Looks nicer
        val etURLText: String = etURL.text.toString()

        if (etTitleText.isNotBlank() and etURLText.isNotBlank()) {
            val portal = Portal(etTitleText, etURLText)
            val resultIntent = Intent()

            resultIntent.putExtra(EXTRA_PORTAL, portal)

            setResult(Activity.RESULT_OK, resultIntent)
            finish()

        } else Toast.makeText(this, "Please fill out both fields.", Toast.LENGTH_SHORT).show()
    }
}
