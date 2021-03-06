package com.ngangavic.test.sharedprefs

import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ngangavic.test.R

class SharedPrefsActivity : AppCompatActivity() {
    lateinit var editTextName: EditText
    lateinit var editTextYear: EditText
    lateinit var editTextAge: EditText
    lateinit var editTextTown: EditText
    lateinit var editTextPhone: EditText
    lateinit var buttonSave: Button
    lateinit var buttonRead: Button
    lateinit var textViewDisplay: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shared_prefs)
        editTextName = findViewById(R.id.editTextName)
        editTextYear = findViewById(R.id.editTextYear)
        editTextAge = findViewById(R.id.editTextAge)
        editTextTown = findViewById(R.id.editTextTown)
        editTextPhone = findViewById(R.id.editTextPhone)
        buttonSave = findViewById(R.id.buttonSave)
        buttonRead = findViewById(R.id.buttonRead)
        textViewDisplay = findViewById(R.id.textViewDisplay)

//        val sharedPref = applicationContext.getSharedPreferences(
//                getString(R.string.preference_file_key), Context.MODE_PRIVATE)

        val sharedPreferences = LocalStorage(applicationContext)
//        sharedPreferences.save()


        buttonSave.setOnClickListener {
            if (TextUtils.isEmpty(editTextName.text.toString()) || TextUtils.isEmpty(editTextYear.text.toString()) || TextUtils.isEmpty(editTextAge.text.toString()) || TextUtils.isEmpty(editTextTown.text.toString()) || TextUtils.isEmpty(editTextPhone.text.toString())) {
                Toast.makeText(applicationContext, "Fill all the fields", Toast.LENGTH_SHORT).show()
            } else {
                sharedPreferences.save(getString(R.string.name), editTextName.text.toString())
                sharedPreferences.save(getString(R.string.year), editTextYear.text.toString())
                sharedPreferences.save(getString(R.string.age), editTextAge.text.toString())
                sharedPreferences.save(getString(R.string.town), editTextTown.text.toString())
                sharedPreferences.save(getString(R.string.phone), editTextPhone.text.toString())

                sharedPreferences.save2(getString(R.string.name), editTextName.text.toString())
                sharedPreferences.save2(getString(R.string.year), editTextYear.text.toString())
                sharedPreferences.save2(getString(R.string.age), editTextAge.text.toString())
                sharedPreferences.save2(getString(R.string.town), editTextTown.text.toString())
                sharedPreferences.save2(getString(R.string.phone), editTextPhone.text.toString())
                Toast.makeText(applicationContext, "Saved", Toast.LENGTH_SHORT).show()
//                with(sharedPref.edit()) {
//                    putString(getString(R.string.name), editTextName.text.toString())
//                    putString(getString(R.string.year), editTextYear.text.toString())
//                    putString(getString(R.string.age), editTextAge.text.toString())
//                    putString(getString(R.string.town), editTextTown.text.toString())
//                    putString(getString(R.string.phone), editTextPhone.text.toString())
//                    commit()
//                    Toast.makeText(applicationContext,"Saved",Toast.LENGTH_SHORT).show()
//                }
            }
        }

        buttonRead.setOnClickListener {
            val name = sharedPreferences.getValueString(getString(R.string.name))
            val year = sharedPreferences.getValueString(getString(R.string.year))
            val age = sharedPreferences.getValueString(getString(R.string.age))
            val town = sharedPreferences.getValueString(getString(R.string.town))
            val phone = sharedPreferences.getValueString(getString(R.string.phone))
//            val name = sharedPref.getString(getString(R.string.name),"null")
            textViewDisplay.text = name + " " + year + " " + age + " " + town + " " + phone
        }
    }
}
