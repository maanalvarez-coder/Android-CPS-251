package com.example.assignment8

import kotlinx.coroutines.flow.Flow

class ContactRepository(private val contactDao: ContactDao) {
    // Exposes a Flow of all notes, allowing for observing changes in the database.
    val allContacts: Flow<List<Contact>> = contactDao.getAllContacts()

    // Suspended function to insert a new note into the database.
    // Marked as suspend because Room operations are asynchronous.
    suspend fun insert(contact: Contact) = contactDao.insert(contact)

    // Suspended function to delete an existing note from the database.
    // Marked as suspend because Room operations are asynchronous.
    suspend fun delete(contact: Contact) = contactDao.delete(contact)

    fun getContactsSortedByNameAsc() = contactDao.getContactsSortedByNameAsc()

    fun getContactsSortedByNameDesc() = contactDao.getContactsSortedByNameDesc()
    fun findContacts(query: String) = contactDao.findContacts(query)
}