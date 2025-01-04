package com.example.aquasense

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.getValue

class MainActivity : AppCompatActivity() {
    var tds = ""
    var turbid = ""
    var temp = ""
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val questionButton = findViewById<ImageView>(R.id.questionButton)
        val tdsTv = findViewById<TextView>(R.id.tds_tv)
        val tempTv = findViewById<TextView>(R.id.suhu_tv)
        val turbidTv = findViewById<TextView>(R.id.turbid_tv)
        val myRef = Firebase.database.getReference("transaction")

        // Read from the database
        myRef.addValueEventListener(object: ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val tdsVal = snapshot.child("tds").getValue<String>()
                if (tdsVal != null) {
                    tds = tdsVal
                }
                val tempVal = snapshot.child("suhu").getValue<String>()
                if (tempVal != null) {
                    temp = tempVal
                }
                val turbidVal = snapshot.child("turbid").getValue<String>()
                if (turbidVal != null) {
                    turbid = turbidVal
                }

                tdsTv.text = tds
                turbidTv.text = turbid
                tempTv.text = temp

            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "Failed to read value.", error.toException())
            }

        })

        questionButton.setOnClickListener {
            // Aksi ketika tombol diklik, misalnya membuka dialog atau halaman bantuan

            val intent = Intent(this, HelpActivity::class.java)
            startActivity(intent)
        }
    }
}