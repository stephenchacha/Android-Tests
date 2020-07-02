package com.ngangavic.test.chat

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.ngangavic.test.R

class ChatActivity : AppCompatActivity() {

    private lateinit var textViewTitle: TextView
    private lateinit var editTextMessage: EditText
    private lateinit var imageButtonSend: ImageButton
    private lateinit var recyclerviewMessages: RecyclerView
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        textViewTitle = findViewById(R.id.textViewTitle)
        editTextMessage = findViewById(R.id.editTextMessage)
        imageButtonSend = findViewById(R.id.imageButtonSend)
        recyclerviewMessages = findViewById(R.id.recyclerviewMessages)
        textViewTitle.text="Choose Recipient"
        textViewTitle.setOnClickListener { chooseRecipient() }

        auth = FirebaseAuth.getInstance()
        database = Firebase.database.reference

    }

    private fun authRegisterAlert() {
        val alert = AlertDialog.Builder(this)
        alert.setCancelable(false)
        val customLayout = getLayoutInflater().inflate(R.layout.dialog_chat_register, null)
        alert.setView(customLayout)

        val name = customLayout.findViewById<EditText>(R.id.registerName)
        val email = customLayout.findViewById<EditText>(R.id.registerEmail)
        val password = customLayout.findViewById<EditText>(R.id.registerPassword)


        alert.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })
        alert.setPositiveButton("Register", DialogInterface.OnClickListener { dialog, which ->
            auth.createUserWithEmailAndPassword(email.text.toString(), password.text.toString())
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            database.child("chat-users").child(auth.currentUser!!.uid).child("username").setValue(name.text.toString())
                                    .addOnSuccessListener {
                                        editTextMessage.isEnabled = true
                                        imageButtonSend.isEnabled = true
                                        dialog.cancel()
                                        chooseRecipient()
                                        Toast.makeText(baseContext, "Authentication success", Toast.LENGTH_LONG).show()
                                    }
                                    .addOnFailureListener {
                                        Toast.makeText(baseContext, "Error.", Toast.LENGTH_SHORT).show()
                                    }
                        } else {
                            Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                        }

                    }
        })
        val dialog = alert.create()
        customLayout.findViewById<TextView>(R.id.textViewLogin).setOnClickListener {
            dialog.cancel()
            authLoginAlert()
        }
        dialog.show()
    }

    private fun authLoginAlert() {
        val alert = AlertDialog.Builder(this)
        alert.setCancelable(false)
        val customLayout = getLayoutInflater().inflate(R.layout.dialog_chat_login, null)
        alert.setView(customLayout)

        val email = customLayout.findViewById<EditText>(R.id.loginEmail)
        val password = customLayout.findViewById<EditText>(R.id.loginPassword)

        alert.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })
        alert.setPositiveButton("Login", DialogInterface.OnClickListener { dialog, which ->
            auth.signInWithEmailAndPassword(email.text.toString(), password.text.toString())
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            chooseRecipient()
                            editTextMessage.isEnabled = true
                            imageButtonSend.isEnabled = true
                            dialog.cancel()
                            Toast.makeText(baseContext, "Authentication success", Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                        }

                    }
        })
        val dialog = alert.create()
        customLayout.findViewById<TextView>(R.id.textViewRegister).setOnClickListener {
            dialog.cancel()
            authRegisterAlert()
        }
        dialog.show()
    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        Log.e("CHAT USER", currentUser.toString())
        if (currentUser == null) {
            editTextMessage.isEnabled = false
            imageButtonSend.isEnabled = false
            authLoginAlert()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.chat_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_logout -> {
                auth.signOut()
                recyclerviewMessages.visibility = View.GONE
                editTextMessage.isEnabled = false
                imageButtonSend.isEnabled = false
                Toast.makeText(baseContext, "You signed out", Toast.LENGTH_LONG).show()
            }
            R.id.action_change_recipient->{
                chooseRecipient()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun chooseRecipient() {
        val alert = AlertDialog.Builder(this)
        alert.setCancelable(false)
        alert.setTitle("Choose Recipient")
        val recyclerView = RecyclerView(this)
        alert.setView(recyclerView)
        val dialog = alert.create()
        recyclerView.setOnClickListener {
            dialog.cancel()
        }
        dialog.show()
    }

}