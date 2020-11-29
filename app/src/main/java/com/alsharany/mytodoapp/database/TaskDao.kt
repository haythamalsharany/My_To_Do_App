package com.alsharany.studentapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.alsharany.mytodoapp.Task


import java.util.*

@Dao
interface TaskDao {
    @Query("SELECT* FROM  task_table WHERE taskState=0 ")
    fun getToDoTasks(): LiveData<List<Task>>

    @Query("SELECT* FROM  task_table WHERE taskState=1 ")
    fun getInprogressTasks(): LiveData<List<Task>>

    @Query("SELECT* FROM  task_table WHERE taskState=2 ")
    fun getDoneTasks(): LiveData<List<Task>>


    @Query("SELECT * FROM task_table WHERE id=(:id)")
    fun getTask(id: UUID): LiveData<Task?>

    @Update
    fun updateTask(task: Task)

    @Insert
    fun addTask(task: Task)

    @Delete
    fun deleteTask(task: Task)
}