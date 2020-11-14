package com.example.focusstarthomework.contacts.data.datasource.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.focusstarthomework.contacts.domain.entity.Contact

@Dao
interface ContactsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContacts(contactList: List<Contact>)

    @Query("SELECT * FROM contacts_table")
    suspend fun getContacts(): List<Contact>
}