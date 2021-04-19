package com.example.homework4

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class ThirdActivity : AppCompatActivity() {

    private lateinit var textViewTitle: TextView
    private lateinit var textViewFeel: TextView
    private lateinit var textViewWhat: TextView
    private lateinit var textViewInterpretation: TextView
    private lateinit var buttonDelete: Button
    private lateinit var buttonUpdate: Button

    private val dreamViewModel:DreamViewModel by viewModels{
        DreamViewModelFactory((application as DreamApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)

        textViewTitle = findViewById(R.id.textView_titleDisplay)
        textViewFeel = findViewById(R.id.textView_feel)
        textViewWhat = findViewById(R.id.textView_whatDisplay)
        textViewInterpretation = findViewById(R.id.textView_interpretationDisplay)
        buttonDelete = findViewById(R.id.button_delete)
        buttonUpdate = findViewById(R.id.button_update)

        val id = intent.getStringExtra("DreamId")
        // Log.d("id", id.toString())

        if (id != null) {
            dreamViewModel.getDreamById(id.toInt()).observe(this, androidx.lifecycle.Observer {
                dream -> dream.let{
                if (dream != null){
                    textViewTitle.text = dream.title
                    textViewFeel.text = textViewFeel.text.toString() + "\n" + dream.emotion
                    textViewWhat.text = textViewWhat.text.toString() + "\n" + dream.content
                    textViewInterpretation.text = textViewInterpretation.text.toString() + "\n" + dream.reflection
                }
            }
            })
        }

        buttonDelete.setOnClickListener{
            if (id != null) {
                dreamViewModel.delete(id.toInt())
                finish()
            }
        }

        buttonUpdate.setOnClickListener{
            val intent = Intent(this@ThirdActivity, SecondActivity::class.java)
            intent.putExtra("id", id)
            startActivity(intent)
        }
    }
}