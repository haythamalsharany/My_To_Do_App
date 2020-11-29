package com.alsharany.criminalintent.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.alsharany.mytodoapp.Task


import com.alsharany.studentapp.database.TaskDao

@Database(entities = [Task::class], version = 1, exportSchema = false)
@TypeConverters(TaskTypeConverter::class)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao

}





