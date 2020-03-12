package com.example.github

import com.example.github.model.Repository
import com.example.github.model.ResponseList
import com.example.github.model.User
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Repo {

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api = retrofit.create(NetworkApi::class.java)

    suspend fun getApiData(users: String) : Response<ResponseList>{
        return api.searchUsers(users)
    }
    suspend fun getUserApiData(user: String) : Response<User>{
        return api.getUser(user)
    }
    suspend fun getUserApiRepoData(user: String) : Response<List<Repository>>{
        return api.getUserRepos(user)
    }
}