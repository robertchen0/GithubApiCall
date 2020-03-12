package com.example.github.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.github.ListCustomAdapter
import com.example.github.R
import com.example.github.viewmodel.UserListViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.delay
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var listViewModel: UserListViewModel
    lateinit var listAdapter: ListCustomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        et_search_users.addTextChangedListener(object: TextWatcher{
            var timer = Timer()
            override fun afterTextChanged(s: Editable?) {
                timer = Timer()
                timer.schedule(object : TimerTask(){
                    override fun run() {
                        if(!s.toString().isEmpty()) {
                            listViewModel.getUsersList(s.toString())
                        }
                    }
                }, 1000)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                timer.cancel()
//                if(count >= 3){
//                    listViewModel.getUsersList(s.toString())
//                }
            }
        })

        listViewModel = ViewModelProvider(this).get(UserListViewModel::class.java)
        listAdapter = ListCustomAdapter(this)
        listViewModel.observeUserList.observe(this, Observer {
            listAdapter.setDataSet(it)
        })
        listViewModel.observeError.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })
        recyclerView.adapter = listAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }
}
