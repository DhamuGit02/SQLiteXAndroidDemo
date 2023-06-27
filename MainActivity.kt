package com.example.sqlitexandroiddemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val dbHelper = DBHelper(applicationContext)
        val submitbtn = this.findViewById<Button>(R.id.submitBtn);
        val viewBtn = this.findViewById<Button>(R.id.viewDB);
        fun register():Boolean? {
            val name = this.findViewById<EditText>(R.id.nameTxt);
            val mobno = this.findViewById<EditText>(R.id.mobno);
            val email = this.findViewById<EditText>(R.id.email);
            val age = this.findViewById<EditText>(R.id.age);
            return dbHelper.registerUser(mobno.text.toString(), name.text.toString(), email.text.toString(), age.text.toString());
        }
        submitbtn.setOnClickListener {
            if(register() == true) Toast.makeText(applicationContext, "Successfully Registered", Toast.LENGTH_SHORT).show()
            else Toast.makeText(applicationContext, "Unable to register!", Toast.LENGTH_SHORT).show()
        }
        viewBtn.setOnClickListener {
            val viewDBIntent = Intent(this, DatabaseView::class.java)
            startActivity(viewDBIntent)
        }
    }
}