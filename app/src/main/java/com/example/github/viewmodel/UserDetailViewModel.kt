package com.example.github.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.github.Repo
import com.example.github.model.Repository
import com.example.github.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException

class UserDetailViewModel : ViewModel() {
    private var userDetail = MutableLiveData<User>()
    val observeUserDetail: LiveData<User>
        get() = userDetail

    private var userRepo = MutableLiveData<List<Repository>>()
    val observeRepoList: LiveData<List<Repository>>
        get() = userRepo

    private var error = MutableLiveData<String>()
    val observeError: LiveData<String>
        get() = error

    val repo: Repo = Repo()

    fun getUserData(user: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val retrievedData = repo.getUserApiData(user)
            try {
                if (retrievedData.isSuccessful) {
                    userDetail.postValue(retrievedData.body())
                } else {
                    error.postValue(retrievedData.code().toString())
                }
            } catch (e: HttpException) {
                error.postValue(e.message().toString())
            } catch (e: Throwable) {
                error.postValue(e.toString())
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            val retrievedRepoData = repo.getUserApiRepoData(user)
            try {
                if (retrievedRepoData.isSuccessful) {
                    userRepo.postValue(retrievedRepoData.body())
                } else {
                    error.postValue(retrievedRepoData.code().toString())
                }
            } catch (e: HttpException) {
                error.postValue(e.message().toString())
            } catch (e: Throwable) {
                error.postValue(e.toString())
            }
        }
    }
}