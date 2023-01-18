package com.example.projectwola

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {

    lateinit var txtEmailCreate:EditText
    lateinit var txtPasswordCreate:EditText
    lateinit var buttonRegisterlCreate:Button
    lateinit var buttonbackreg:Button

    lateinit var email:String
    lateinit var password:String

    private var mAuth: FirebaseAuth? = null
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        txtEmailCreate = findViewById<EditText>(R.id.txtEmailCreate)
        txtPasswordCreate = findViewById<EditText>(R.id.txtEmailCreate)
        buttonRegisterlCreate = findViewById<Button>(R.id.buttonRegisterCreate)

        mAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance("https://project-wola-default-rtdb.asia-southeast1.firebasedatabase.app/")

        buttonRegisterlCreate.setOnClickListener {
            createAccount()
        }
    }


    override fun onStart() {
        super.onStart()
        val currenUser = mAuth!!.currentUser
        updateUI(currenUser)
    }

    private fun createAccount(){

        email = txtEmailCreate.text.toString()
        password = txtPasswordCreate.text.toString()
        mAuth!!.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this){
            task -> if (task.isSuccessful){
                Log.d("MyApp","Create New User Success!")
                val user = mAuth!!.currentUser
                val databaseReference = database.reference.child("user").push()
                databaseReference.child("uid").setValue(user!!.uid)
                databaseReference.child("email").setValue(user!!.email)
                updateUI(null)

            }else{
                Log.w("MyApp","Failure Process!",task.exception)
                Toast.makeText(this@RegisterActivity,
                    "Authentication Failed",
                    Toast.LENGTH_SHORT
                ).show()
                updateUI(null)

                }
            }
        }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            val uid = user.uid
            val email = user.email
            Toast.makeText(
                this@RegisterActivity,
                "Wellcome: $email your ID is: $uid",
                Toast.LENGTH_SHORT
            ).show()
            val intentSession = Intent(this,ListActivity::class.java )
            startActivity(intentSession)
        }
    }


}





