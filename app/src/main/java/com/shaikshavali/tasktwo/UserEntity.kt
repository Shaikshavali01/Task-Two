package com.shaikshavali.tasktwo

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "user-table")
data class UserEntity(
//creating the user table
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String = "",
    @ColumnInfo(name = "College Name")
    //In Db the field name will be College Name
    val clgName: String = "",

    val gender: String = "",
    val passYear: String = "",
    val dob: String = "",
    val dateTime: String = "",
    val rating: Int = 0,
    val rateMyself: Int = 0
//fields or columns in DB

) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readInt()

        //parcelable requires when the "user details" are shifting from one activity to another activity
        //it is used when we share user details and pass them to next page
        //in MainActivity.kt file 101 line........

    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(clgName)
        parcel.writeString(gender)
        parcel.writeString(passYear)
        parcel.writeString(dob)
        parcel.writeString(dateTime)
        parcel.writeInt(rating)
        parcel.writeInt(rateMyself)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserEntity> {
        override fun createFromParcel(parcel: Parcel): UserEntity {
            return UserEntity(parcel)
        }

        override fun newArray(size: Int): Array<UserEntity?> {
            return arrayOfNulls(size)
        }
    }


}




