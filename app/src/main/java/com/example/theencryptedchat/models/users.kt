package com.example.theencryptedchat.models

import android.os.Parcelable


class  Users(val username:String,val uid:String,val photoUrl:String): Parcelable {
    constructor(): this("","","")

}