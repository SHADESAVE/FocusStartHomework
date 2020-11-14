package com.example.focusstarthomework.contacts.data.datasource.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.focusstarthomework.contacts.domain.entity.Contact

@Database(entities = [Contact::class], version = 1, exportSchema = false)
abstract class ContactsDataBase : RoomDatabase() {

    abstract fun contactsDao(): ContactsDao

    companion object {
        @Volatile
        private var INSTANCE: ContactsDataBase? = null

        fun getInstance(context: Context): ContactsDataBase =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(context).also { INSTANCE = it }
            }
        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                ContactsDataBase::class.java,
                "ContactsDatabase.db"
            ).build()
    }
}