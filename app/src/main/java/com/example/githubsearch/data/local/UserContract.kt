package com.example.githubsearch.data.local

import android.provider.BaseColumns

object UserContract {
    object UserEntry : BaseColumns{
        const val TABLE_NAME       = "users"
        const val USER_NAME        = "user_name"
        const val REPOSITORIES_QTD = "repositories_qtd"
        const val LAST_SEARCH      = "last_search"
    }

    const val TABLE_USER =
        "CREATE TABLE ${UserEntry.TABLE_NAME} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY, " +
                "${UserEntry.USER_NAME} TEXT, " +
                "${UserEntry.REPOSITORIES_QTD} INTEGER, " +
                "${UserEntry.LAST_SEARCH} DATE)"

    const val SQL_DELETE_ETRIES = "DROP TABLE IF EXISTS ${UserEntry.TABLE_NAME}"
}