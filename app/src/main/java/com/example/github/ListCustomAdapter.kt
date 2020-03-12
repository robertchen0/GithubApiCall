package com.example.github

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.github.model.ListUsers
import com.example.github.view.MainActivity
import com.example.github.view.UserDetail
import com.squareup.picasso.Picasso

class ListCustomAdapter(var context: Context): RecyclerView.Adapter<ListCustomAdapter.CustomViewHolder>() {

    var data: List<ListUsers> = emptyList()

    fun setDataSet(list: List<ListUsers>){
        data = list
        notifyDataSetChanged()
    }

    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val icon: ImageView = itemView.findViewById(R.id.im_view)
        val name: TextView = itemView.findViewById(R.id.tv_username)
        val repo_num: TextView = itemView.findViewById(R.id.tv_repo_nums)
        val card: CardView  = itemView.findViewById(R.id.rv_cardView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        return CustomViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_search_layout, parent, false)
        )
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.name.text = data[position].login
        Picasso.get().load(data[position].avatar_url).resize(100, 100)
            .centerCrop().into(holder.icon)

        holder.card.setOnClickListener {
            val intent = Intent(context, UserDetail::class.java)
            intent.putExtra("username", data[position].login)
            it.context.startActivity(intent)
        }
    }
}
