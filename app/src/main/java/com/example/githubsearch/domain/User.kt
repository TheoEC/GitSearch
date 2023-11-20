package com.example.githubsearch.domain

import java.util.Date

data class User (
    val name: String,
    val repositoriesQtd: Int,
    val lastSearchDate: Date
)