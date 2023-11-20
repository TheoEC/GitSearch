package com.example.githubsearch.domain

import com.google.gson.annotations.SerializedName

data class Repository (
    val name    : String,
    @SerializedName("html_url")
    val htmlURL : String
)