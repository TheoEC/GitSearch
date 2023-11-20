package com.example.githubsearch.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.githubsearch.R
import com.example.githubsearch.domain.Repository

class RepositoryAdapter(private val Repositories: List<Repository>): RecyclerView.Adapter<RepositoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_repository, parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = Repositories.size

    //Populate views data
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.repositoryName.text = Repositories[position].name
        //TTODO("Implement repository click")
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val repositoryName: TextView

        init {
            view.apply {
                repositoryName = findViewById(R.id.tv_repository_name)
            }
        }

    }
}