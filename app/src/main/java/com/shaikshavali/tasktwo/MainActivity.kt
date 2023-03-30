package com.shaikshavali.tasktwo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shaikshavali.tasktwo.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null

    private var rview: RecyclerView? = null
    private var nxtBtn: ImageButton? = null
    private var tvEdit: TextView? = null
    //declaring necessary variables

    private var userDao: UserDAO? = null
    private var usersList: ArrayList<UserEntity>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        //create view binding

        rview = binding?.rview1



        nxtBtn = binding?.imgbtnext

        nxtBtn?.setOnClickListener {
            val intent = Intent(this, UserDetails::class.java)
            startActivity(intent)
            //adding the new user....

        }



        userDao = (application as UserApp).db.userDao()
        //creating the DAO variable

        lifecycleScope.launch {
            userDao?.fetchAllUsers()?.collect {

                usersList = ArrayList(it)
                //fetching all users into arrayList

                listToRv(usersList!!, userDao!!)

            }
        }

        tvEdit = binding?.tvedit

        tvEdit?.setOnClickListener {

            val rvItemAdapter =
                RvItemAdapter(usersList!!)
            //need to be done
            rvItemAdapter.displayCancel()

//            val userEntity = usersList?.get(0)
//            usersList?.remove(userEntity)
//
//            Log.e("User List ","removed and added to list of ${userEntity?.name}")
//
//            if (userEntity != null) {
//                usersList?.add(0,userEntity)
//            }
//
//            rvItemAdapter.notifyItemRemoved(0)

        // "here i tried to delete the user from array list and notify the adapter but it didn't worked"


        }


    }

    private fun nextActivityOnClickingCard(
        usersList: ArrayList<UserEntity>,
        rvItemAdapter: RvItemAdapter
    ) {

        rview?.layoutManager = LinearLayoutManager(this)
        rview?.adapter = rvItemAdapter

        rvItemAdapter.onItemClick = {
            //It is the RvItemAdapter's  lambda method

            val intent = Intent(this, UserDetails::class.java)
            intent.putExtra("UserEntity", it)
            //carry forward the user's data into edit page we use parcelable....

            startActivity(intent)

        }

    }

    private fun listToRv(
        usersList: ArrayList<UserEntity>,         //converting arrayList to Item in RecyclerView
        userDAO: UserDAO
    ) {
        if (usersList.isNotEmpty()) {
            //checking whether the is null !!

            val rvAdapter = RvItemAdapter(usersList)
            binding?.rview1?.layoutManager = LinearLayoutManager(this)
            //Setting the layout manager to item in recycler view

            binding?.rview1?.adapter = rvAdapter
            // Assigning the created adapter to recycler view's adapter

            rvAdapter.onDeleteClick = {
                deleteUser(it)
                //deleting the user after clicking the cancel image
            }

            nextActivityOnClickingCard(usersList, rvAdapter)
            //On clicking the card the details of user should filled in user page
        }

    }

    private fun deleteUser(userEntity: UserEntity) {
        lifecycleScope.launch {
            userDao?.delete(userEntity)
            //deleting the User
        }
        Toast.makeText(this, "Deleted ${userEntity.name}", Toast.LENGTH_SHORT).show()

    }


    override fun onDestroy() {
        super.onDestroy()
        //assigning the binding to null
        binding = null
    }


}