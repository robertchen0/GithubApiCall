package com.example.github.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.github.Repo
import com.example.github.model.ListUsers
import com.example.github.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException

class UserListViewModel: ViewModel() {
    private var userList = MutableLiveData<List<ListUsers>>()
    val observeUserList: LiveData<List<ListUsers>>
    get() = userList

    private var error = MutableLiveData<String>()
    val observeError: LiveData<String>
    get() = error

    val repo: Repo = Repo()

    fun getUsersList(users: String){
        viewModelScope.launch(Dispatchers.IO) {
            val retrievedData = repo.getApiData(users)
            try {
                if (retrievedData.isSuccessful){
                    userList.postValue(retrievedData.body()?.items as List<ListUsers>)
                }
                else{
                    error.postValue(retrievedData.code().toString())
                }
            } catch (e: HttpException) {
                error.postValue(e.message().toString())
            } catch (e: Throwable) {
                error.postValue(e.toString())
            }
        }
    }
}