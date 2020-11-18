package com.arzupinarcioglu.listofgithubrepos

import android.app.Application
import com.arzupinarcioglu.listofgithubrepos.model.local.AppDatabase


class App : Application() {

    companion object {
        lateinit var db: AppDatabase
    }

}