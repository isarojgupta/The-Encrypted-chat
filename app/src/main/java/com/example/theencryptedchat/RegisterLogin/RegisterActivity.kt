package com.example.theencryptedchat.RegisterLogin

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import com.example.theencryptedchat.R
import com.example.theencryptedchat.RegisterLogin.LoginActivity
import com.example.theencryptedchat.models.Users
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_register.*
import java.util.*

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        register_btn_register.setOnClickListener {

            performRegister()

        }

        already_have_an_account_register.setOnClickListener {
            Log.d("RegisterActivityResult","try to go in the login activity")
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        select_photo_btn.setOnClickListener{
            Log.d("RegisterActivityResult","try to select photo")
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(
                    intent,
                    0
            )
        }

    }
    var selectPhotoUri: Uri? = null
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode ==0 && resultCode == Activity.RESULT_OK && data != null){
//            proceed and check what the selected image was
            Log.d("RegisterActivityResult","photo was selected")

            selectPhotoUri = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver,selectPhotoUri)
            val bitMapDrawble = BitmapDrawable(bitmap)
            select_photo_btn.setBackgroundDrawable(bitMapDrawble)
            Log.d("RegisterActivityResult","photo set")
        }
    }

    private fun performRegister(){
        val usernameRegister = username_editText_register.text.toString()
        val emailRegister  = email_editText_register.text.toString()
        val passwordRegister = password_editText_register.text.toString()


        if(emailRegister.isEmpty() || passwordRegister.isEmpty()){
            Toast.makeText(this@RegisterActivity,"please enter email or password whatever you keep empty",Toast.LENGTH_LONG).show()
            return
        }
        Log.d("RegisterActivityResult:","email is: $emailRegister")
        Log.d("RegisterActivityResult:","password is: $passwordRegister")
//        create user in firebase
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(emailRegister,passwordRegister).addOnCompleteListener(this){


            if (!it.isSuccessful) return@addOnCompleteListener

//            else if successful
            Log.d("RegisterActivityResult","successfully create user with uid : ${it.result?.user?.uid}")


            uploadImageToFirebaseStore()

        }
                .addOnFailureListener{
                    Log.d("RegisterActivityResult","Failed to create user")
                    Toast.makeText(this,"Failed to create user ${it.message}",Toast.LENGTH_LONG).show()
                }

    }
    private fun uploadImageToFirebaseStore(){
        if (selectPhotoUri == null) return

        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/images/$filename")

        ref.putFile(selectPhotoUri!!)
                .addOnSuccessListener {

                    Log.d("RegisterActivityResult","successfully upload image : ${it.metadata?.path}")

                    ref.downloadUrl.addOnSuccessListener{
                        Log.d("RegisterActivityResult","Photo location : $it")

                        saveUserToFirebaseDatabase(it.toString())
                    }
                }
    }
    private fun saveUserToFirebaseDatabase(photoUrl: String){
        val uid = FirebaseAuth.getInstance().uid ?:""
        val ref =  FirebaseDatabase.getInstance().getReference("/users/$uid")

        val users = Users(username_editText_register.text.toString(),uid,photoUrl)
        ref.setValue(users)
                .addOnSuccessListener {
                    Log.d("RegisterActivityResult","Finally we saved the user to database")
                    val intent = Intent(this, LoginActivity::class.java)

                    startActivity(intent)
                }
                .addOnFailureListener{
                    Log.d("RegisterActivityResult","Failed to upload data on Database")
                    Toast.makeText(this@RegisterActivity,"Failed to upload data",Toast.LENGTH_LONG).show()
                }


    }

}