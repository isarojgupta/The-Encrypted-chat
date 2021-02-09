package com.example.theencryptedchat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.theencryptedchat.Message.NewMessageActivity
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.activity_chat_log.*

class ChatLogActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_log)


        /*getting the username from new message activity
        * and storing in a variable called 'username'
        * set as title bar*/
           val username = intent.getStringExtra(NewMessageActivity.USER_KEY)
            supportActionBar?.title = username



        val adapter = GroupAdapter<GroupieViewHolder>()
        adapter.add(chatFromPerson())
        adapter.add(chatToPerson())
        adapter.add(chatFromPerson())
        adapter.add(chatToPerson())
        adapter.add(chatFromPerson())
        adapter.add(chatToPerson())
        adapter.add(chatFromPerson())
        adapter.add(chatFromPerson())
        adapter.add(chatFromPerson())



        recycler_view_chat_log.adapter = adapter


    }
}


class chatFromPerson: Item<GroupieViewHolder>(){
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {

    }

    override fun getLayout(): Int {

        return R.layout.chat_data
    }


}

class chatToPerson: Item<GroupieViewHolder>(){
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {

    }

    override fun getLayout(): Int {

        return R.layout.chat_user_own_data
    }


}