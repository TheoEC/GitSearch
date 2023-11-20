package com.example.githubsearch.data.local

import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.example.githubsearch.data.local.UserContract.UserEntry.LAST_SEARCH
import com.example.githubsearch.data.local.UserContract.UserEntry.REPOSITORIES_QTD
import com.example.githubsearch.data.local.UserContract.UserEntry.TABLE_NAME
import com.example.githubsearch.data.local.UserContract.UserEntry.USER_NAME
import com.example.githubsearch.domain.User
import java.util.Date

class UserRepository(val context: Context) {

    private fun save(user: User) : Boolean{
        var rowID = -1L
        try {
            val dbHelper = UserDBHelper(context)
            val dataBase = dbHelper.writableDatabase

            val columns = ContentValues().apply {
                put(USER_NAME, user.name)
                put(REPOSITORIES_QTD, user.repositoriesQtd)
                put(LAST_SEARCH, user.lastSearchDate.time)
            }

            dataBase?.let {
                rowID = it.insert(TABLE_NAME, null, columns)
            }
        } catch (ex: Exception) {
            ex.message?.let {
                Log.e("USER SAVING ERROR", it)
            }
        }

        return (rowID != -1L)
    }

    fun findUserByName(name: String): User? {
        val dbHelper = UserDBHelper(context)
        val dataBase = dbHelper.readableDatabase

        val columns = arrayOf(
            USER_NAME,
            REPOSITORIES_QTD,
            LAST_SEARCH
        )

        val filter = "$USER_NAME = ?"
        val filterValues = arrayOf(name)

        val cursor = dataBase.query(
            TABLE_NAME,
            columns,
            filter,
            filterValues,
            null,
            null,
            null
        )

        val usersList = mutableListOf<User>()

        var user: User? = null

        with(cursor) {
            while (moveToNext()){
                val name = getString(getColumnIndexOrThrow(USER_NAME))
                val qtd  = getInt(getColumnIndexOrThrow(REPOSITORIES_QTD))
                val date = Date(getLong(getColumnIndexOrThrow(LAST_SEARCH)))

                user = User(name, qtd, date)
            }
        }
        cursor?.close()
        return user
    }

    fun saveIfNotExist(user: User): Boolean {
        if (findUserByName(user.name) == null) {
            return save(user)
        } else {
            Log.d("finsUserByName", "User already exists")
            return false
        }
    }

    fun getAllUsers() : List<User> {
        val dbHelper = UserDBHelper(context)
        val dataBase = dbHelper.readableDatabase

        val columns = arrayOf(
            USER_NAME,
            REPOSITORIES_QTD,
            LAST_SEARCH
        )

        val cursor = dataBase.query(
            TABLE_NAME,
            columns,
            null,
            null,
            null,
            null,
            null
        )

        val usersList = mutableListOf<User>()

        with(cursor) {
            while (moveToNext()){
                val name = getString(getColumnIndexOrThrow(USER_NAME))
                val qtd  = getInt(getColumnIndexOrThrow(REPOSITORIES_QTD))
                val date = Date(getLong(getColumnIndexOrThrow(LAST_SEARCH)))

                usersList.add(
                    User(
                        name,
                        qtd,
                        date
                    )
                )
            }
        }
        cursor?.close()
        return usersList
    }
}