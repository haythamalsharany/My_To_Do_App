package com.alsharany.mytodoapp

import android.app.Application

class TaskApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        TaskRepository.initialize(this)
    }
}