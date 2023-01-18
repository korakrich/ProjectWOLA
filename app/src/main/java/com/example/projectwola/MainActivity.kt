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

class MainActivity : AppCompatActivity() {

    lateinit var txtEmail: EditText
    lateinit var txtPassword: EditText
    lateinit var buttonLogin: Button
    lateinit var buttonRegister: Button
    lateinit var buttonForget : Button

    lateinit var email:String
    lateinit var password:String

    private var mAuth: FirebaseAuth? = null
    private  lateinit var database: FirebaseDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtEmail = findViewById<EditText>(R.id.txtEmail)
        txtPassword = findViewById<EditText>(R.id.txtEmail)
        buttonLogin = findViewById<Button>(R.id.buttonLogin)
        buttonRegister = findViewById<Button>(R.id.buttonRegister)
        buttonForget = findViewById<Button>(R.id.buttonForget)

        mAuth = FirebaseAuth.getInstance()


        buttonRegister!!.setOnClickListener {
            var intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        buttonLogin!!.setOnClickListener{
            loginEmail()
        }

        buttonForget!!.setOnClickListener{
            val gotoForget = Intent(this,ForgetActivity::class.java)
            startActivity(gotoForget)
        }

    }

    override fun onStart() {
        super.onStart()
        val currenUser = mAuth!!.currentUser
        updateUI(currenUser)
    }

    private fun loginEmail() {
        email = txtEmail!!.text.toString()
        password = txtPassword!!.text.toString()
        mAuth!!.signInWithEmailAndPassword(email,password).addOnCompleteListener (this){ task ->
            if (task.isSuccessful) {
                android.util.Log.d("MyApp", "Create New User Success!")
                val user = mAuth!!.currentUser
                updateUI(user)
            } else {
                Log.w("MyApp", "Failure Process!", task.exception)
                Toast.makeText(this@MainActivity,
                    "Authentication Failed",
                    Toast.LENGTH_SHORT)
                    .show()
                updateUI(null)
            }
        }
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            val uid = user.uid
            val email = user.email
            Toast.makeText(this@MainActivity,
                "Wellcome: $email your ID is: $uid",
                Toast.LENGTH_SHORT
            ).show()
            val intentSession = Intent(this,ListActivity::class.java )
            startActivity(intentSession)
        }

    }
}