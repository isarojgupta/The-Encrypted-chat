package com.example.theencryptedchat.RegisterLogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.theencryptedchat.Message.LatestMessageActivity
import com.example.theencryptedchat.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

        private  val TAG = "LoginActivityResult"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        login_btn.setOnClickListener {
         userLoginAuthenticationWithFirebase()
        }
        create_an_account_textView.setOnClickListener {
            Log.d("MainActivity:","try to go in register activity")
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()


        }

    }
    private fun userLoginAuthenticationWithFirebase() {
        val usernameLogin = email_editText.text.toString()
        val passwordLogin = password_editText.text.toString()

        Log.d(TAG, "Email or username:$usernameLogin")
        Log.d(TAG, "password :$passwordLogin")
        if (usernameLogin.isEmpty() || passwordLogin.isEmpty()) {

            Toast.makeText(
                this@LoginActivity,
                "Please enter username or password",
                Toast.LENGTH_LONG
            ).show()
            return
        }
        FirebaseAuth.getInstance().signInWithEmailAndPassword(usernameLogin, passwordLogin)
            .addOnCompleteListener(this) {

                    task ->

                if (task.isSuccessful) {

                    Log.d(TAG, "SuccessFully Logged In")
                    /*start the new activity because after the authentication user must
            *   be shift on to the message activity*/
                    val intent = Intent(this@LoginActivity, LatestMessageActivity::class.java)
                    Log.d(TAG, "user moved to the Latest message Activity")
                    startActivity(intent)
                    finish()
                } else  {
                    Log.d(TAG, "authentication Failed check after some time",task.exception)

                    Toast.makeText(
                        this@LoginActivity,
                        "authentication failed with $usernameLogin \n $passwordLogin \n please check again",
                        Toast.LENGTH_LONG
                    ).show()

                }
            }
    }
}