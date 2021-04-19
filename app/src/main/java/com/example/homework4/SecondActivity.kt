package com.example.homework4

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity

class SecondActivity : AppCompatActivity() {

    private lateinit var spinner: Spinner
    private lateinit var editTextTitle: EditText
    private lateinit var editTextWhat: EditText
    private lateinit var editTextInterpretation: EditText
    private lateinit var buttonSave: Button

    private val dreamViewModel:DreamViewModel by viewModels{
        DreamViewModelFactory((application as DreamApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        editTextTitle = findViewById(R.id.editText_title)
        editTextWhat = findViewById(R.id.editText_what)
        editTextInterpretation = findViewById(R.id.editText_interpretation)

        spinner = findViewById(R.id.spinner)
        ArrayAdapter.createFromResource(
            this,
            R.array.feelings_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }

        val id = intent.getStringExtra("id")
        // Log.d("id", id.toString())

        if (id != null) {
            dreamViewModel.getDreamById(id.toInt()).observe(this, androidx.lifecycle.Observer {
                    dream -> dream.let{
                editTextTitle.setText(dream.title)
                editTextWhat.setText(dream.content)
                editTextInterpretation.setText(dream.reflection)

                for (i in 0 until spinner.count) {
                    if (spinner.getItemAtPosition(i).toString().equals(dream.emotion, ignoreCase = true)) {
                        spinner.setSelection(i)
                        break;
                    }
                }

            }
            })
        }


        buttonSave = findViewById(R.id.button_save)
        buttonSave.setOnClickListener{
            when {
                TextUtils.isEmpty(editTextTitle.text) -> {
                    toastError("Missing title")
                }
                TextUtils.isEmpty(editTextWhat.text) -> {
                    toastError("Missing content description")
                }
                TextUtils.isEmpty(editTextInterpretation.text) -> {
                    toastError("Missing interpretation")
                }
                else -> {
                    val dream = Dream(editTextTitle.text.toString(), editTextWhat.text.toString(),
                                        editTextInterpretation.text.toString(), spinner.selectedItem.toString())
                    if(id == null){
                        dreamViewModel.insert(dream)
                        finish()
                    }
                    else{
                        dreamViewModel.updateDream(id.toInt(), editTextTitle.text.toString(), editTextWhat.text.toString(),
                            editTextInterpretation.text.toString(), spinner.selectedItem.toString())
                        val intent2 = Intent(this@SecondActivity, ThirdActivity::class.java)
                        intent2.putExtra("DreamId", id)
                        startActivity(intent2)
                    }

                }
            }
        }
    }

    private fun toastError(text:String){
        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    }
}