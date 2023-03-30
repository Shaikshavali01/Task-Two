package com.shaikshavali.tasktwo

import android.app.Application

class UserApp : Application() {
    //in androidManifest.xml we have to define it as application name
    val db by lazy {
        // db object is created whenever it is necessary

        UserDatabase.getInstance(this)
        // Getting the instance of the database.......
    }
}