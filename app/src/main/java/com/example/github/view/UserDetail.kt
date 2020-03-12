package com.example.github.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.github.AdapterRepo
import com.example.github.R
import com.example.github.model.Repository
import com.example.github.model.User
import com.example.github.viewmodel.UserDetailViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_user_detail.*

class UserDetail : AppCompatActivity() {

    lateinit var userViewModel: UserDetailViewModel
    lateinit var userAdapterRepo: AdapterRepo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)
        val item = intent.getStringExtra("username")
        userViewModel = ViewModelProvider(this).get(UserDetailViewModel::class.java)
        userAdapterRepo = AdapterRepo(this)
        userViewModel.getUserData(item ?: "")
        userViewModel.observeUserDetail.observe(this, Observer {
            setData(it)
        })
        userViewModel.observeError.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })
        userViewModel.observeRepoList.observe(this, Observer {
            userAdapterRepo.setDataRepoSet(it)
        })
        ed_search_repo.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                userAdapterRepo.setDataRepoSet(filterRepo(userViewModel.observeRepoList.value!!, s.toString()))
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
        recyclerView_repos.adapter = userAdapterRepo
        recyclerView_repos.layoutManager = LinearLayoutManager(this)
    }

    private fun filterRepo(list: List<Repository>, userInput: String) : List<Repository>{
        val newArray: ArrayList<Repository> = arrayListOf()
        if(userInput.isEmpty()){
            return list
        }
        else {
            for(i in list){
                if(i.name.contains(userInput, true)){
                    newArray.add(i)
                }
            }
            return newArray
        }
    }

    private fun setData(user: User){
        tv_userDetailName.text = user.login
        tv_email.text = user.email
        tv_location.text = user.location
        tv_join_date.text = user.created_at
        tv_followers.text = user.followers.toString()
        tv_following.text = user.following.toString()
        tv_blog.text = user.blog
        Picasso.get().load(user.avatar_url).resize(300, 300)
            .centerCrop().into(im_userimage)
    }
}
