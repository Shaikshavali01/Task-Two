package com.shaikshavali.tasktwo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UserEntity::class], version = 1)
abstract class UserDatabase : RoomDatabase() {
//for creating the database we need this abstract class

    abstract fun userDao(): UserDAO
    // It returns the object of the "UserDAO" Interface for CRUD Operations

    companion object {

        @Volatile
        private var INSTANCE: UserDatabase? = null
        //Instance will be created only Once


        fun getInstance(context: Context): UserDatabase {

            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        UserDatabase::class.java,
                        "user_database"
                    ).fallbackToDestructiveMigration()
                        .build()
                    //Getting the Users Database Instance by Singleton Method

                    INSTANCE = instance

                }
                return instance

            }

        }
    }
}