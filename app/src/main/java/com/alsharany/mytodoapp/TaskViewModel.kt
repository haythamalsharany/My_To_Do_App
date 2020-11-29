package com.alsharany.mytodoapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class TaskViewModel : ViewModel() {
    private var taskRepo: TaskRepository? = null
    var toDoTaskLiveDataList: LiveData<List<Task>>? = null
    var inprogressTaskLiveDataList: LiveData<List<Task>>? = null
    var doneTaskLiveDataList: LiveData<List<Task>>? = null


    init {
        taskRepo = TaskRepository.get()


        toDoTaskLiveDataList = taskRepo!!.getToDOTasks()
        inprogressTaskLiveDataList = taskRepo!!.getInprogressTasks()
        doneTaskLiveDataList = taskRepo!!.getDoneTasks()
    }


    fun addTask(task: Task) {
        taskRepo?.addTask(task)

    }

    fun deleteTask(task: Task) {
        taskRepo?.deleteTask(task)


    }


}