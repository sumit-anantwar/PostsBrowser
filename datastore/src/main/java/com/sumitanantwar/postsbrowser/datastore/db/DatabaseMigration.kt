package com.sumitanantwar.postsbrowser.datastore.db

import io.realm.DynamicRealm
import io.realm.RealmMigration

class DatabaseMigration : RealmMigration {

    companion object {
        // Must be bumped when the schema changes
        const val SCHEMA_VERSION : Long = 0
    }

    /** Migrations to run based on Schema Version */
    override fun migrate(realm: DynamicRealm, oldVersion: Long, newVersion: Long) {

        val schema = realm.schema

        if (oldVersion < 1) {
            // Migrations to be setup when the schema is supposed to be updated
        }

    }

}