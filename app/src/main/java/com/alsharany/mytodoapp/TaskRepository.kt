package com.alsharany.mytodoapp

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.alsharany.criminalintent.database.TaskDatabase
import java.util.*
import java.util.concurrent.Executors

private const val DATABASE_NAME = "crime-database"

class TaskRepository private constructor(context: Context) {


    private val database: TaskDatabase =
        Room.databaseBuilder(
            context.applicationContext,
            TaskDatabase::class.java,
            DATABASE_NAME
        ).fallbackToDestructiveMigration()
            .build()
    private val taskDao = database.taskDao()
    private val executor = Executors.newSingleThreadExecutor()
    private val filesDir = context.applicationContext.filesDir
    fun getToDOTasks(): LiveData<List<Task>> = taskDao.getToDoTasks()
    fun getInprogressTasks(): LiveData<List<Task>> = taskDao.getInprogressTasks()
    fun getDoneTasks(): LiveData<List<Task>> = taskDao.getDoneTasks()
    fun getTask(id: UUID): LiveData<Task?> = taskDao.getTask(id)

    fun updateTask(task: Task) {
        executor.execute {
            taskDao.updateTask(task)
        }
    }

    fun addTask(task: Task) {
        executor.execute {
            taskDao.addTask(task)
        }
    }

    fun deleteTask(task: Task) {
        executor.execute {
            taskDao.deleteTask(task)
        }
    }
    // fun getPhotoFile(task: Task): File = File(filesDir, task.photoFileName)

    companion object {
        private var INSTANCE: TaskRepository? = null
        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = TaskRepository(context)
            }
        }

        fun get(): TaskRepository {
            return INSTANCE ?: throw IllegalStateException("CrimeRepository must be initialized")
        }
    }

}







