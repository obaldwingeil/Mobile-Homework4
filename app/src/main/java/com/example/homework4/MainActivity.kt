package com.example.homework4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Spinner
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var button_log: Button

    private val dreamViewModel:DreamViewModel by viewModels{
        DreamViewModelFactory((application as DreamApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)

        val adapter = DreamListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        dreamViewModel.getAllDreams.observe(this, Observer {
            dreams -> dreams?.let{
            adapter.submitList(it)
        }
        })

        button_log = findViewById(R.id.button_newDream)
        button_log.setOnClickListener{
            launchNextActivity()
        }
    }

    private fun launchNextActivity(){
        val intent = Intent(this, SecondActivity::class.java)
        startActivity(intent)
    }
}