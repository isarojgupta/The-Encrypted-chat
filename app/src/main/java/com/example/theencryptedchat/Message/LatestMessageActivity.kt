package com.example.theencryptedchat.Message

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.theencryptedchat.R
import com.example.theencryptedchat.RegisterLogin.LoginActivity
import com.google.firebase.auth.FirebaseAuth

class LatestMessageActivity : AppCompatActivity() {

    private val TAG: String = "LatestActivityResult"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_latest_message)
      /* at the starting
        *    check the user has authentication or signing IN or not
         *       if not then user moved to LogIn Activity  by executing this method userVerificationSignIn()
       */
      userVerificationSignIn()
    }

    /* Defining the userVerificationSignIn()  method to check that user is authenticate or not
     *   by using uid which is generating by Firebase itSelf
     */
    private fun userVerificationSignIn(){
        val uid = FirebaseAuth.getInstance().uid
        if(uid ==  null){
            Log.d(TAG,"user is not signIn ")
            val intent = Intent(this, LoginActivity::class.java)
            Log.d(TAG,"moved to LogIn Activity")
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
        else{
            Log.d(TAG,"User verified")
            Toast.makeText(this@LatestMessageActivity,
                "User Verified",
                  Toast.LENGTH_SHORT).show()
        }
    }
     /*
     Executing this method to get the option of menu which is chose by user
     By the option as per method executed the option will be execute
     * */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId){

            R.id.sign_out -> {
    /*if user wants to sign out then execute this line
            and user move to login Activity
     */
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this, LoginActivity::class.java)
                Log.d(TAG,"moved to LogIn Activity")
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)

            }
            /*if user wants to send message a new contact then this code is going to
            *       Execute.
            *       Also i have used back button over the activity
            *           which i have added in the manifest file
            *               as mata data form
            * */
            R.id.select_contact ->{

                Log.d(TAG,"select the New Message Activity")
                val intent = Intent(this@LatestMessageActivity, NewMessageActivity::class.java)
                Log.d(TAG,"Moved to New Message Activity")    
                startActivity(intent)
            }
        }

        return super.onOptionsItemSelected(item)
    }
/* Executing this method to inflate(Set) the menu resource file on the right corner side
*        */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
       menuInflater.inflate(R.menu.menu_sign_out,menu)
        return super.onCreateOptionsMenu(menu)
    }
}