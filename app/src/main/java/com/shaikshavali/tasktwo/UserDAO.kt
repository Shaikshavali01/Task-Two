package com.shaikshavali.tasktwo

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDAO {

    @Insert
    suspend fun insert(userEntity: UserEntity)

    @Update
    suspend fun update(userEntity: UserEntity)

    @Delete
    suspend fun delete(userEntity: UserEntity)              //CRUD Operations......

    @Query("SELECT * FROM 'User-table'")
    fun fetchAllUsers(): Flow<List<UserEntity>>

    @Query("SELECT * FROM 'User-table' where id=:id")
    fun fetchUserById(id: Int): Flow<UserEntity>

}