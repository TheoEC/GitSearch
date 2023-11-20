package br.com.igorbag.githubsearch.data

import com.example.githubsearch.domain.Repository
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubService {

    @GET("users/{user}/repos")
    fun getAllRepositoriesFromUser(@Path("user") user: String): retrofit2.Call<List<Repository>>

}