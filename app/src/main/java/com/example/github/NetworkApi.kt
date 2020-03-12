package com.example.github

import com.example.github.model.ListUsers
import com.example.github.model.Repository
import com.example.github.model.ResponseList
import com.example.github.model.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NetworkApi {

    //https://api.github.com/
    //https://api.github.com/search/users?q=tom
    //https://api.github.com/users/robertchen0/
    //https://api.github.com/users/robertchen0/repos

    @GET("search/users")
    suspend fun searchUsers(@Query("q") users: String) : Response<ResponseList>

    @GET("users/{username}")
    suspend fun getUser(@Path("username") user: String) : Response<User>

    @GET("https://api.github.com/users/{username}/repos")
    suspend fun getUserRepos(@Path("username") user: String) : Response<List<Repository>>
}