package com.sumitanantwar.postsbrowser.datastore.db

import android.content.Context
import io.realm.Realm
import io.realm.RealmConfiguration

object DatabaseProvider {

    fun initialize(context: Context) {
        Realm.init(context)
        val config = RealmConfiguration.Builder()
            .schemaVersion(DatabaseMigration.SCHEMA_VERSION)
            .migration(DatabaseMigration())
            .build()

        Realm.setDefaultConfiguration(config)
    }

    fun getInstance() : Realm {
        return Realm.getDefaultInstance()
    }

}