package com.alsharany.mytodoapp

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "task_table")
data class Task(
    @PrimaryKey
    val id: UUID = UUID.randomUUID(),
    var title: String = " ",
    var details: String = "",
    var createDate: Date = Date(),
    var endDate: Date = Date(),
    var taskState: Int = 0
)
