package com.shaikshavali.tasktwo


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.shaikshavali.tasktwo.databinding.Recyclerview1Binding

class RvItemAdapter(private val items: ArrayList<UserEntity>) :         //creating the Recycler view's Adapter
    RecyclerView.Adapter<RvItemAdapter.ViewHolder>() {

    var onItemClick: ((UserEntity) -> Unit)? = null

    //on click method for editing the particular user it take UserEntity as parameter and return type is Unit
    var onDeleteClick: ((UserEntity) -> Unit)? = null

    //On Click's method for deleting the particular user     ''            ''           ''
    private var isEditCancelClicked: Boolean = true


    fun displayCancel() {
        isEditCancelClicked = true
        // Logic is whenever I click the "edit" button in screen it invokes
        // this method and change the variable to true and it should display
        //  the (-) delete image to the user... but it is not working
        //  so I directly put the variable to true and it displays every time
        //  I have to implement this........
        // Log.e("Display Cancel","Display cancel is clicked,,")

    }

    class ViewHolder(binding: Recyclerview1Binding) :
        RecyclerView.ViewHolder(binding.root) {     //creating the View Holder class for the parameter of the
        //main class

        val imgUser = binding.imguser

        val tvName = binding.tvitname
        val tvClgName = binding.tvitclgname
        val tvGender = binding.tvitgender
        val tvPassYear = binding.tvitbtech
        val tvDob = binding.tvitdob

        //necessary variables in the Recycler views xml file
        val tvDateTime = binding.tvitdatetime
        val tvStar1 = binding.tvstar1
        val tvStar2 = binding.tvstar2
        val tvStar3 = binding.tvstar3
        val tvStar4 = binding.tvstar4
        val tvStar5 = binding.tvstar5
        //for rating purpose....

        val cardView = binding.rvcard
        val imgCancel = binding.imgcancel


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //overriding the methods
        return ViewHolder(
            Recyclerview1Binding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val context = holder.itemView.context
        val item = items[position]
        //getting the particular item from arrayList of items

        holder.tvName.text = item.name
        holder.tvClgName.text = item.clgName
        holder.tvGender.text = item.gender
        holder.tvPassYear.text = item.passYear
        holder.tvDob.text = item.dob
        holder.tvDateTime.text = item.dateTime
        //assigning the users data to the card of the recycler View

        if (item.gender == "Male") {
            holder.imgUser.background = ContextCompat.getDrawable(context, R.drawable.usermale)
        } else {                                                                       //Decision for the image of the Male or Female based on Users Gender
            holder.imgUser.background = ContextCompat.getDrawable(context, R.drawable.userfemale)
        }

        when (item.rating) {                                          // Filling the stars in card Based on the Users Rating
            1 -> {
                holder.tvStar1.background = ContextCompat.getDrawable(context, R.drawable.fullstar)
            }
            2 -> {
                holder.tvStar1.background = ContextCompat.getDrawable(context, R.drawable.fullstar)
                holder.tvStar2.background = ContextCompat.getDrawable(context, R.drawable.fullstar)
            }
            3 -> {
                holder.tvStar1.background = ContextCompat.getDrawable(context, R.drawable.fullstar)
                holder.tvStar2.background = ContextCompat.getDrawable(context, R.drawable.fullstar)
                holder.tvStar3.background = ContextCompat.getDrawable(context, R.drawable.fullstar)
            }

            4 -> {
                holder.tvStar1.background = ContextCompat.getDrawable(context, R.drawable.fullstar)
                holder.tvStar2.background = ContextCompat.getDrawable(context, R.drawable.fullstar)
                holder.tvStar3.background = ContextCompat.getDrawable(context, R.drawable.fullstar)
                holder.tvStar4.background = ContextCompat.getDrawable(context, R.drawable.fullstar)
            }

            5 -> {
                holder.tvStar1.background = ContextCompat.getDrawable(context, R.drawable.fullstar)
                holder.tvStar2.background = ContextCompat.getDrawable(context, R.drawable.fullstar)
                holder.tvStar3.background = ContextCompat.getDrawable(context, R.drawable.fullstar)
                holder.tvStar4.background = ContextCompat.getDrawable(context, R.drawable.fullstar)
                holder.tvStar5.background = ContextCompat.getDrawable(context, R.drawable.fullstar)
            }


        }

        holder.cardView.setOnClickListener {
            onItemClick?.invoke(item)
            //By clicking on item of Rv the Edit page should be opened with his details
            // Log.e("Inside item view","item view is clicked...${item.name}")  // For MY Checking and Confirmation Purpose

        }
        if (isEditCancelClicked)
            holder.imgCancel.visibility = View.VISIBLE
        //Logic need to be implemented but im unable to do it :(  :(
        // My logic is that when the "Edit" is clicked then the cancel image

        else
            holder.imgCancel.visibility = View.GONE
        //should VISIBLE otherwise it should GONE


        holder.imgCancel.setOnClickListener {
            onDeleteClick?.invoke(item)
            //Invoking the lambda method for Deleting the particular User
            //Log.e("Inside Cancel","Delete image is clicked for...${item.name}")
            // For Verification Purpose

        }

    }


}