package com.example.githubsearch.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import br.com.igorbag.githubsearch.data.GitHubService
import com.example.githubsearch.R
import com.example.githubsearch.data.local.UserRepository
import com.example.githubsearch.domain.Repository
import com.example.githubsearch.domain.User
import com.example.githubsearch.ui.adapter.RepositoryAdapter
import com.example.githubsearch.ui.adapter.UserAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Date

class MainActivity : AppCompatActivity() {
    lateinit var btnConfirm: Button
    lateinit var etUserName: EditText
    lateinit var lstReporitories: RecyclerView
    lateinit var lstUsers: RecyclerView

    lateinit var GitService: GitHubService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupRetrofit()
        setupViews()
        setupRecentUsers(this)
    }

    fun setupRecentUsers(context: Context) {
        val repository = UserRepository(context)
        val adapter = UserAdapter(repository.getAllUsers())

        lstUsers.adapter = adapter
        adapter.userItemListener = { user ->
            etUserName.setText(user.name)
            getAllRepositories()
        }
    }

    fun setupViews() {
        etUserName      = findViewById(R.id.et_UserName)
        lstReporitories = findViewById(R.id.rv_repositories)
        lstUsers        = findViewById(R.id.rv_last_searches)
        btnConfirm      = findViewById(R.id.btn_confirm)
        btnConfirm.setOnClickListener {
            getAllRepositories()
        }
    }

    fun setupRetrofit() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        GitService = retrofit.create(GitHubService::class.java)
    }

    fun getAllRepositories(){
        val user = etUserName.text.toString()
        GitService.getAllRepositoriesFromUser(user).enqueue(object : Callback<List<Repository>>{
            override fun onResponse(
                call: Call<List<Repository>>,
                response: Response<List<Repository>>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        val repository = UserRepository(applicationContext)
                        repository.saveIfNotExist(
                            User(
                                user,
                                it.size,
                                Date()
                            )
                        )
                        setupRepositories(it)
                        setupRecentUsers(applicationContext)
                    }
                }
            }

            override fun onFailure(call: Call<List<Repository>>, t: Throwable) {
                Log.d("Response", "Failure")
            }
        })
    }

    fun setupRepositories(list: List<Repository>){
        val repositoriesAdapter = RepositoryAdapter(list)
        lstReporitories.adapter = repositoriesAdapter
    }
}