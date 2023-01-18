package com.example.projectwola

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class ListActivity : AppCompatActivity() {

    lateinit var recyclerViewBranch: RecyclerView

    private var mAuth: FirebaseAuth? = null
    lateinit var database: FirebaseDatabase
    lateinit var databaseReference: DatabaseReference
    lateinit var responseBranch: MutableList<Branch>
    private var BranchAdapter: BranchAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        recyclerViewBranch = findViewById<RecyclerView>(R.id.recyclerViewBranch)

        mAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance("https://project-wola-default-rtdb.asia-southeast1.firebasedatabase.app/")

        recyclerViewBranch.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        databaseReference = database.getReference("branch/")
        responseBranch = mutableListOf()
        BranchAdapter = BranchAdapter(responseBranch as ArrayList<Branch>)
        recyclerViewBranch.adapter = BranchAdapter

        onBindingFirebase()

    }

    private fun onBindingFirebase() {
        databaseReference.addChildEventListener(object: ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                responseBranch.add(snapshot.getValue(Branch::class.java)!!)
                BranchAdapter!!.notifyDataSetChanged()
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onChildRemoved(snapshot: DataSnapshot) {

            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    override fun onStart() {
        super.onStart()
        val currenUser = mAuth!!.currentUser
        updateUI(currenUser)
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            val uid = user.uid
            val email = user.email
            Toast.makeText(
                this@ListActivity,
                "Wellcome: $email your ID is: $uid",
                Toast.LENGTH_SHORT
            ).show()
        }else{
            val intentSession = Intent(this,MainActivity::class.java )
            startActivity(intentSession)
        }
    }

}