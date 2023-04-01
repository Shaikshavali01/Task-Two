package com.shaikshavali.tasktwo

import android.annotation.SuppressLint
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

    private lateinit var rvAdapter: RvItemAdapter

    private var userDao: UserDAO? = null
    private lateinit var usersList: ArrayList<UserEntity>


    @SuppressLint("NotifyDataSetChanged")
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

        var arList = ArrayList<UserEntity>()
        lifecycleScope.launch {

            userDao?.fetchAllUsers()?.collect {
                arList = ArrayList(it)
                //fetching all users into arrayList

                listToRv(arList, userDao!!)

            }

        }
        usersList = arList
        //initialising the lateinit variable

        tvEdit = binding?.tvedit

        rvAdapter = RvItemAdapter(usersList)
        binding?.rview1?.adapter = rvAdapter
        rview?.layoutManager = LinearLayoutManager(this)


        tvEdit?.setOnClickListener {

            if (binding?.tvedit?.text?.equals("Edit") == true) {
                binding?.tvedit?.text = getString(R.string.cancel_)
                //changing the"Edit" to "Cancel"
                displayCancel("Cancel")
                // Log.e("In onClickListener ", "Changing to cancel and variable is ${rvAdapter.isEditCancelClicked}")

            } else {
                binding?.tvedit?.text = getString(R.string.edit)

                displayCancel("Edit")
                //Log.e("In onclickListener ", "Changing to edit and variable is ${rvAdapter.isEditCancelClicked}")


            }

        }

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun displayCancel(str: String) {
        rvAdapter.isEditCancelClicked = str == "Cancel"

        // Logic is whenever I click the "edit" button in screen it invokes
        // this method and change the variable to true and it should display
        //  the (-) delete image to the user...

        // Log.e("Display Cancel","Display cancel is clicked variable is  ${rvAdapter.isEditCancelClicked}")
        rvAdapter.notifyDataSetChanged()
        //notifying the adapter that the isEditCancelClicked is changed and
        // again updating the recycler view adapter
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

            rvAdapter = RvItemAdapter(usersList)
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
        tvEdit?.text = getString(R.string.edit)
        Toast.makeText(this, "Deleted ${userEntity.name}", Toast.LENGTH_SHORT).show()

    }


    override fun onDestroy() {
        super.onDestroy()
        //assigning the binding to null
        binding = null
    }


}