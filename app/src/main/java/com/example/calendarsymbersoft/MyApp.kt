package com.example.calendarsymbersoft

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class MyApp: Application() {
    override fun onCreate() {
        super.onCreate()
        //init Realm
        Realm.init(this)
        val config = RealmConfiguration.Builder()
            .name("Events.db")
            .deleteRealmIfMigrationNeeded()
            .schemaVersion(0)
            .allowQueriesOnUiThread(true)
            .allowWritesOnUiThread(true)
            .build()
        Realm.setDefaultConfiguration(config)
    }
}