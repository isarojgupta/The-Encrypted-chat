package com.example.theencryptedchat.Message

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.theencryptedchat.ChatLogActivity
import com.example.theencryptedchat.R
import com.example.theencryptedchat.models.Users
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.activity_new_message.*
import kotlinx.android.synthetic.main.user_item_layout.view.*


class NewMessageActivity: AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_message)
        /*
        set the title of new message activity
        */

        supportActionBar?.title = "Select People"

        /*Adding GroupAdapter which is third party layout generator*/
//        val adapter = GroupAdapter<GroupieViewHolder>()
//
//        Log.d("NewMessageActivityDebuging","viewgroup call")
//        /* create a userItem item class to pass of object and
//         * whole list of user Item to view the item in layout
//        */
////        adapter.add(userItem())
////        adapter.add(userItem())
////        adapter.add(userItem())
////        adapter.add(userItem())
////        adapter.add(userItem())
////        adapter.add(userItem())
//
//            Log.d(TAG,"viewGroup call")
//            new_message_recycler_view_container.adapter = adapter

            Log.d(TAG,"fetching start")

        fetchUsers()
    }
    /*using fetchUser() method to 
    * by executing this method we will get the user data */

    private fun fetchUsers(){
        Log.d(TAG,"userItem called")
        val ref = FirebaseDatabase.getInstance().getReference("/users")
//    user listener to get the data from the firebase database
        ref.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
        /*creating a variable to store the adapter and attach it with the fetching data*/
                
                val adapter = GroupAdapter<GroupieViewHolder>()
                /*
                * Fetching the from data base using snapshot*/
             Log.d(TAG,"Fetch data starting")
                snapshot.children.forEach {

                    Log.d(TAG,it.toString())

                    val user = it.getValue(Users::class.java)

                    if (user != null) {
                        adapter.add(userItem(user))
                    }
                }
                /*set the adapter data which is fetch  from   firebase Database add to layout of userMenu layout*/
                new_message_recycler_view_container.adapter = adapter


                adapter.setOnItemClickListener{item ,view ->

                    val userItem = item as userItem

                    val intent = Intent(this@NewMessageActivity,ChatLogActivity::class.java)
                    /*Using putExtra method to get the username which we are going to  use in chatlog Activity
                    * to show the action bar title in activity as per people select */
                    intent.putExtra(USER_KEY,userItem.user)
                    startActivity(intent)
                    finish()


                }

            }

            override fun onCancelled(error: DatabaseError)
            {
                //               wil try it later
            }

        })
  }

    companion object {
        const val USER_KEY = "USER_KEY";
        private const val TAG = "NewMessageActivityDebuging"
    }

}

/*Use this class to get the user details(Items)
*  to add in adapter of activity*/

class userItem(val user: Users): Item<GroupieViewHolder>(){

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {

            viewHolder.itemView.username_new_message_to_chat.text = user.username
        Picasso.get().load(user.photoUrl).into(viewHolder.itemView.profile_pic_for_new_chat)
    }

    override fun getLayout(): Int {
        return R.layout.user_item_layout
    }

}


/*Using CustomAdapter is good but it is giving some difficult so as per using
*  like custom adapter instead
*   use "Groupie" third party recycler view layout */
/*

class CustomAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

}*/


