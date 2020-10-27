package com.example.theencryptedchat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_login.*

import kotlinx.android.synthetic.main.activity_main.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        login_btn.setOnClickListener {
            val usernameLogin = email_editText.text.toString()
            val passwordLogin = password_editText.text.toString()

            Log.d("MainActivity: ", "Email or username:$usernameLogin")
            Log.d("MainActivity: ", "password :$passwordLogin")

        }
        create_an_account_textView.setOnClickListener {
            Log.d("MainActivity:","try to go in register activity")
            startActivity(Intent(this,RegisterActivity::class.java))
            finish()


        }






    }
}