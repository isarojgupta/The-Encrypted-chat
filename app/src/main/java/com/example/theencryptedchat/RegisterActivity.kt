package com.example.theencryptedchat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        register_btn_register.setOnClickListener {
            val usernameRegister = username_editText_register.text.toString()
            val emailRegister  = email_editText_register.text.toString()
            val passwordRegister = password_editText_register.text.toString()

        }

        already_have_an_account_register.setOnClickListener {
            Log.d("MainActivity:","try to go in the login activity")
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}