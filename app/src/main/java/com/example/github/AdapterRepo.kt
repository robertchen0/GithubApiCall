package com.example.github

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.github.model.Repository

class AdapterRepo(var context: Context): RecyclerView.Adapter<AdapterRepo.CustomViewHolder>() {

    var data: List<Repository> = emptyList()

    fun setDataRepoSet(list: List<Repository>){
        data = list
        notifyDataSetChanged()
    }

    class CustomViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val repo_name: TextView = itemView.findViewById(R.id.tv_repo_name)
        val fork: TextView = itemView.findViewById(R.id.tv_repo_forks)
        val star: TextView = itemView.findViewById(R.id.tv_repo_stars)
        val cardView: CardView = itemView.findViewById(R.id.repo_cardView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        return CustomViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.repositories_layout, parent, false)
        )
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.repo_name.text = data[position].name
        holder.fork.text = data[position].forks.toString()
        holder.star.text = data[position].stargazers_count.toString()
        holder.cardView.setOnClickListener {
            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data = Uri.parse(data[position].html_url)
            context.startActivity(openURL)
        }
    }
}