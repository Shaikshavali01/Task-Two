package com.shaikshavali.tasktwo

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.shaikshavali.tasktwo.databinding.ActivityUserDetailsBinding
import kotlinx.coroutines.launch
import java.util.*

class UserDetails : AppCompatActivity() {
    var binding: ActivityUserDetailsBinding? = null

    private var etName: EditText? = null
    private var etClgname: EditText? = null

    var nameEntered: String? = null
    var clgNameEntered: String? = null

    private var tvRadio1: TextView? = null
    private var tvRadio2: TextView? = null


    private var genderSelected: String = ""
    //for storing the selected Gender

    private var yearSelected: String = ""

    private var etTech: EditText? = null

    private var tvSelectDobDate: TextView? = null
    private var btnDob: Button? = null

    var dobSelected: String? = null

    private var tvSelectDateTime: TextView? = null
    private var btnDateTime: Button? = null

    var dateTimeSelected: String? = null
    //for storing the date and time selected by the user

    private var ratingBar: RatingBar? = null
    var selectedRating: Int? = null
    //All Necessary Variables

    private var rbtn1: TextView? = null
    private var rbtn2: TextView? = null
    private var rbtn3: TextView? = null
    private var rbtn4: TextView? = null
    private var rbtn5: TextView? = null
    //RadioButtons

    var rateMyself: Int = 0


    private var btnSubmit: Button? = null

    var userDao: UserDAO? = null
    var userId: Int? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        //For Full Screen

        binding = ActivityUserDetailsBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        //Using the ViewBinding method

        etName = findViewById(R.id.etname)
        etClgname = findViewById(R.id.etclgname)


        tvRadio1 = findViewById(R.id.radiomale)
        tvRadio2 = findViewById(R.id.radiofemale)


        selectingGender()


        etTech = findViewById(R.id.etbtechpass)

        //Getting all the views from XML File

        tvSelectDobDate = findViewById(R.id.tvselectdobdate)
        btnDob = findViewById(R.id.btndob)

        btnDob?.setOnClickListener {
            selectingDob()
            //for selecting Date from DatePickerDialog
        }

        tvSelectDateTime = findViewById(R.id.tvselectdatetime)
        btnDateTime = findViewById(R.id.btndatetime)

        btnDateTime?.setOnClickListener {
            selectingDobTime(it)
            //For Selecting Date And Time at once in Date picker and Time Picker dialog
        }


        ratingBar = findViewById(R.id.ratingbar)
        ratingBar?.setOnRatingBarChangeListener { _, rating, _ ->

            //When Rating is Changed by User in Stars
            selectedRating = rating.toInt()
            //store the rating in the var for future purpose

        }

        rbtn1 = binding?.radio1
        rbtn2 = binding?.radio2
        rbtn3 = binding?.radio3
        rbtn4 = binding?.radio4
        rbtn5 = binding?.radio5

        //TextView's for rating our self

        findRating()
        //Finding the selected Rating by User

        btnSubmit = findViewById(R.id.submitbtn)
        btnSubmit?.setOnClickListener {
            buttonSubmit()
            //Save Button

        }
        userDao = (application as UserApp).db.userDao()
        //Creating the Dao Object

        val item = intent.getParcelableExtra<UserEntity>("UserEntity")
        //Getting the Users details from the previous page to the Edit Page


        if (item != null) {
            binding?.addUsers?.text = getString(R.string.edituser)
            assigningFields(item)
            //Assigning all the user details to respective field... when it is not null only
        }

    }


    private fun assigningFields(item: UserEntity) {

        userId = item.id
        //getting user Id for future Purpose

        binding?.etname?.setText(item.name)
        binding?.etclgname?.setText(item.clgName)
        //Assigning all the Fields to their respective data...


        if (item.gender == "Male") {
            binding?.radiomale?.background =
                ContextCompat.getDrawable(this, R.drawable.radiobuttontoggled)
            binding?.radiofemale?.background =
                ContextCompat.getDrawable(this, R.drawable.radiobutton)
            //Assigning the gender and we can update it....
            genderSelected = "Male"
        } else {
            binding?.radiofemale?.background =
                ContextCompat.getDrawable(this, R.drawable.radiobuttontoggled)
            binding?.radiomale?.background = ContextCompat.getDrawable(this, R.drawable.radiobutton)
            //Assigning the gender and we can update it....
            genderSelected = "Female"
        }

        binding?.etbtechpass?.setText(item.passYear)
        binding?.tvselectdobdate?.text = item.dob
        binding?.tvselectdatetime?.text = item.dateTime
        binding?.ratingbar?.rating = item.rating.toFloat()
        //Assigning all the Fields to their respective data...

        when (item.rateMyself) {
            //Assigning the rating to the radio button
            1 -> {
                rateMyself = 1
                binding?.radio1?.background = ContextCompat.getDrawable(
                    this, R.drawable.tickkedcheckbox
                )
                binding?.radio2?.background =
                    ContextCompat.getDrawable(this, R.drawable.checkboxesnormal)
                binding?.radio3?.background =
                    ContextCompat.getDrawable(this, R.drawable.checkboxesnormal)
                binding?.radio4?.background =
                    ContextCompat.getDrawable(this, R.drawable.checkboxesnormal)
                binding?.radio5?.background =
                    ContextCompat.getDrawable(this, R.drawable.checkboxesnormal)
            }

            2 -> {
                rateMyself = 2
                binding?.radio2?.background = ContextCompat.getDrawable(
                    this, R.drawable.tickkedcheckbox
                )
                binding?.radio1?.background =
                    ContextCompat.getDrawable(this, R.drawable.checkboxesnormal)
                binding?.radio3?.background =
                    ContextCompat.getDrawable(this, R.drawable.checkboxesnormal)
                binding?.radio4?.background =
                    ContextCompat.getDrawable(this, R.drawable.checkboxesnormal)
                binding?.radio5?.background =
                    ContextCompat.getDrawable(this, R.drawable.checkboxesnormal)
            }

            3 -> {
                rateMyself = 3
                binding?.radio3?.background = ContextCompat.getDrawable(
                    this, R.drawable.tickkedcheckbox
                )
                binding?.radio2?.background =
                    ContextCompat.getDrawable(this, R.drawable.checkboxesnormal)
                binding?.radio1?.background =
                    ContextCompat.getDrawable(this, R.drawable.checkboxesnormal)
                binding?.radio4?.background =
                    ContextCompat.getDrawable(this, R.drawable.checkboxesnormal)
                binding?.radio5?.background =
                    ContextCompat.getDrawable(this, R.drawable.checkboxesnormal)
            }

            4 -> {
                rateMyself = 4
                binding?.radio4?.background = ContextCompat.getDrawable(
                    this, R.drawable.tickkedcheckbox
                )
                binding?.radio2?.background =
                    ContextCompat.getDrawable(this, R.drawable.checkboxesnormal)
                binding?.radio3?.background =
                    ContextCompat.getDrawable(this, R.drawable.checkboxesnormal)
                binding?.radio1?.background =
                    ContextCompat.getDrawable(this, R.drawable.checkboxesnormal)
                binding?.radio5?.background =
                    ContextCompat.getDrawable(this, R.drawable.checkboxesnormal)
            }

            5 -> {
                rateMyself = 5
                binding?.radio5?.background = ContextCompat.getDrawable(
                    this, R.drawable.tickkedcheckbox
                )
                binding?.radio2?.background =
                    ContextCompat.getDrawable(this, R.drawable.checkboxesnormal)
                binding?.radio3?.background =
                    ContextCompat.getDrawable(this, R.drawable.checkboxesnormal)
                binding?.radio4?.background =
                    ContextCompat.getDrawable(this, R.drawable.checkboxesnormal)
                binding?.radio1?.background =
                    ContextCompat.getDrawable(this, R.drawable.checkboxesnormal)
            }
        }
        //Assigning the rating to the radio button

        binding?.submitbtn?.setOnClickListener {
            buttonSubmit()
            //save the updated details.......
        }

    }


    private fun selectingGender() {
        //selecting the gender here....Using Textview here
        tvRadio1?.setOnClickListener {

            tvRadio1?.background = ContextCompat.getDrawable(this, R.drawable.radiobuttontoggled)
            tvRadio2?.background = ContextCompat.getDrawable(this, R.drawable.radiobutton)
            genderSelected = "Male"

        }

        tvRadio2?.setOnClickListener {

            tvRadio2?.background = ContextCompat.getDrawable(this, R.drawable.radiobuttontoggled)
            tvRadio1?.background = ContextCompat.getDrawable(this, R.drawable.radiobutton)
            genderSelected = "Female"
        }

        //selecting the gender here....Using Textview here
    }

    private fun selectingDob() {

        //selecting the DOB using Date picker dialog

        val cal = Calendar.getInstance()
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        val day = cal.get(Calendar.DAY_OF_MONTH)

        val dpg = DatePickerDialog(
            this, DatePickerDialog.OnDateSetListener { view, year, month, sday ->

                val selectedDate = "$sday-${month + 1}-$year"


                tvSelectDobDate?.text = selectedDate

            },
            year,
            month,
            day
        )
        dpg.datePicker.maxDate = System.currentTimeMillis() - 86400000
        dpg.show()
    }
    //selecting the DOB using Date picker dialog


    private fun selectingDobTime(view: View) {
        //selecting the date and time by date and time picker dialogs

        val cal = Calendar.getInstance()
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        val day = cal.get(Calendar.DAY_OF_MONTH)

        val dpg = DatePickerDialog(
            this, DatePickerDialog.OnDateSetListener { view, year, month, sday ->

                val selectedDate = "$sday-${month + 1}-$year"

                val hour = cal.get(Calendar.HOUR_OF_DAY)
                val min = cal.get(Calendar.MINUTE)

                TimePickerDialog(
                    this,
                    TimePickerDialog.OnTimeSetListener { view, hour, min ->
                        val selectedTime = "$hour:$min"



                        tvSelectDateTime?.text = "$selectedDate | $selectedTime"

                    },
                    hour,
                    min,
                    true
                ).show()


            },
            year,
            month,
            day
        )
        dpg.datePicker.maxDate = System.currentTimeMillis() - 86400000
        dpg.show()

        //selecting the date and time by date and time picker dialogs
    }

    private fun findRating() {
        //selecting the rating in textViews and working as radio buttons logic

        rbtn1?.setOnClickListener {

            rateMyself = 1
            rbtn1?.background = ContextCompat.getDrawable(this, R.drawable.checkboxes)
            rbtn2?.background = ContextCompat.getDrawable(this, R.drawable.checkboxesnormal)
            rbtn3?.background = ContextCompat.getDrawable(this, R.drawable.checkboxesnormal)
            rbtn4?.background = ContextCompat.getDrawable(this, R.drawable.checkboxesnormal)
            rbtn5?.background = ContextCompat.getDrawable(this, R.drawable.checkboxesnormal)


        }

        rbtn2?.setOnClickListener {

            rbtn2?.background = ContextCompat.getDrawable(this, R.drawable.checkboxes)

            rbtn1?.background = ContextCompat.getDrawable(this, R.drawable.checkboxesnormal)
            rbtn3?.background = ContextCompat.getDrawable(this, R.drawable.checkboxesnormal)
            rbtn4?.background = ContextCompat.getDrawable(this, R.drawable.checkboxesnormal)
            rbtn5?.background = ContextCompat.getDrawable(this, R.drawable.checkboxesnormal)

            rateMyself = 2


        }
        rbtn3?.setOnClickListener {

            rateMyself = 3
            rbtn3?.background = ContextCompat.getDrawable(this, R.drawable.checkboxes)

            rbtn1?.background = ContextCompat.getDrawable(this, R.drawable.checkboxesnormal)
            rbtn2?.background = ContextCompat.getDrawable(this, R.drawable.checkboxesnormal)
            rbtn4?.background = ContextCompat.getDrawable(this, R.drawable.checkboxesnormal)
            rbtn5?.background = ContextCompat.getDrawable(this, R.drawable.checkboxesnormal)


        }
        rbtn4?.setOnClickListener {

            rateMyself = 4
            rbtn1?.background = ContextCompat.getDrawable(this, R.drawable.checkboxesnormal)
            rbtn2?.background = ContextCompat.getDrawable(this, R.drawable.checkboxesnormal)
            rbtn3?.background = ContextCompat.getDrawable(this, R.drawable.checkboxesnormal)
            rbtn5?.background = ContextCompat.getDrawable(this, R.drawable.checkboxesnormal)

            rbtn4?.background = ContextCompat.getDrawable(this, R.drawable.checkboxes)

        }
        rbtn5?.setOnClickListener {

            rateMyself = 5
            rbtn1?.background = ContextCompat.getDrawable(this, R.drawable.checkboxesnormal)
            rbtn2?.background = ContextCompat.getDrawable(this, R.drawable.checkboxesnormal)
            rbtn3?.background = ContextCompat.getDrawable(this, R.drawable.checkboxesnormal)
            rbtn4?.background = ContextCompat.getDrawable(this, R.drawable.checkboxesnormal)
            rbtn5?.background = ContextCompat.getDrawable(this, R.drawable.checkboxes)

        }

        //selecting the rating in textview and working as radio buttons logic
    }

    private fun buttonSubmit() {
        when {
            etName?.text?.isEmpty() == true ->
                Toast.makeText(this, "Name is not Filled", Toast.LENGTH_SHORT).show()
            //toast when name field is empty


            etClgname?.text?.isEmpty() == true ->
                Toast.makeText(this, "College name is not Filled", Toast.LENGTH_SHORT).show()
            //toast when clgname field is empty


            genderSelected == "" ->
                Toast.makeText(this, "Gender is not selected", Toast.LENGTH_SHORT).show()
            //toast when gender is not selected

            etTech?.text?.isEmpty() == true ->
                Toast.makeText(this, "Passout year is not selected", Toast.LENGTH_SHORT).show()
            //toast when passout year is empty


            tvSelectDobDate?.text?.equals("Select DOB date") == true ->
                Toast.makeText(this, "DOB is Not Selected", Toast.LENGTH_SHORT).show()
            //toast when DOB is not selected

            tvSelectDateTime?.text?.equals("Select Date and Time") == true ->
                Toast.makeText(this, "Date and Time is Not Selected", Toast.LENGTH_SHORT).show()
            //toast when Date and Time is not selected
            rateMyself == 0 ->
                Toast.makeText(this, "Select u r rating", Toast.LENGTH_SHORT).show()
            //toast when rating is not selected


            else -> {

                nameEntered = etName?.text.toString()
                clgNameEntered = etClgname?.text.toString()
                //storing all the entered details into variables for room storing into database and future purpose

                yearSelected = etTech?.text.toString()
                dobSelected = tvSelectDobDate?.text?.toString()
                dateTimeSelected = tvSelectDateTime?.text?.toString()

                val str = binding?.addUsers?.text.toString()
                if (str == "Add Users") {
                    //checking for new user
                    addRecord()
                    // adding the record into Roomdb
                    jumpToMainActivity()
                    // we again go to main activity where user details will be visible

                } else {
                    updateRecord(
                        UserEntity(
                            userId!!,
                            nameEntered!!,
                            clgNameEntered!!,
                            genderSelected,
                            yearSelected,
                            dobSelected!!,
                            dateTimeSelected!!,
                            ratingBar?.rating?.toInt()!!,
                            rateMyself
                        )
                        // update the users details with the entered details
                    )

                    jumpToMainActivity()

                }
            }

        }


    }

    private fun jumpToMainActivity() {

        val intent = Intent(this@UserDetails, MainActivity::class.java)
        startActivity(intent)

    }

    private fun updateRecord(userEntity: UserEntity) {
        lifecycleScope.launch {
            userDao?.update(userEntity)
            //updating the user record using Dao
        }
        Toast.makeText(this, "Update is done for ${userEntity.name}", Toast.LENGTH_SHORT).show()
    }

    private fun addRecord() {
        if (nameEntered?.isNotEmpty() == true && clgNameEntered?.isNotEmpty() == true &&
            genderSelected.isNotEmpty() && yearSelected.isNotEmpty() &&
            dobSelected?.isNotEmpty() == true && dateTimeSelected?.isNotEmpty() == true &&
            selectedRating != 0 && rateMyself != 0
        )
        //checking the basic condition
        {

            lifecycleScope.launch {

                userDao?.insert(
                    UserEntity(
                        name = nameEntered!!,
                        clgName = clgNameEntered!!,
                        gender = genderSelected,
                        passYear = yearSelected,
                        dob = dobSelected!!,
                        dateTime = dateTimeSelected!!,
                        rating = selectedRating!!,
                        rateMyself = rateMyself
                    )
                    //inserting the details into the database
                )


            }
            Toast.makeText(this, "Successfully inserted into DB ...", Toast.LENGTH_SHORT).show()

        } else {
            Toast.makeText(this, "Something went wrong in Inserting ...", Toast.LENGTH_SHORT).show()
        }

    }


    override fun onDestroy() {
        super.onDestroy()
        //initialise binding to null for ensuring memory leakage
        binding = null
    }


}