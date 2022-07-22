package com.example.gesbulletin

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.example.gesbulletin.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {

    //view binding
    private lateinit var binding: ActivityRegisterBinding

    //firebase auth
    private lateinit var firebaseAuth: FirebaseAuth

    //progress dialog
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //init firebase auth
        firebaseAuth = FirebaseAuth.getInstance()

        //init progress dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please Wait")
        progressDialog.setCanceledOnTouchOutside(false)

        //back button click
        binding.backBtn.setOnClickListener {
            onBackPressed()
        }

        //handle click
        binding.registerBtn.setOnClickListener {
            validateData()
        }
    }

    private var name = ""
    private var email = ""
    private var date = ""
    private var phoneno = ""
    private var password = ""
    private var prnno = ""

    private fun validateData() {

        //input data

        name = binding.nameEt.text.toString().trim()
        email = binding.emailEt.text.toString().trim()
        date = binding.birthdateEt.text.toString().trim()
        phoneno = binding.phonenoEt.text.toString().trim()
        password = binding.passwordEt.text.toString().trim()
        prnno = binding.prnnoEt.text.toString().trim()
        val cpassword = binding.cpasswordEt.text.toString().trim()

        //validating data

        if(name.isEmpty()){
            Toast.makeText(this, "Enter Your Name....", Toast.LENGTH_LONG ).show()
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(this, "Invalid Email....", Toast.LENGTH_LONG ).show()
        }
        else if(date.isEmpty()){
            Toast.makeText(this, "Invalid Date....", Toast.LENGTH_LONG ).show()
        }
        else if(password.isEmpty()){
            Toast.makeText(this, "Enter Password....", Toast.LENGTH_LONG ).show()
        }
        else if(cpassword.isEmpty()){
            Toast.makeText(this, "Confirm Password....", Toast.LENGTH_LONG ).show()
        }
        else if (password != cpassword){
            Toast.makeText(this, "Password don't match...", Toast.LENGTH_SHORT).show()
        }
        else {
            createUserAccount()
        }
    }

    private fun createUserAccount() {

        //show progress
        progressDialog.setMessage("Creating Account...")
        progressDialog.show()

        //create user in firebase auth
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                //account created
                updateUserInfo()
            }
            .addOnFailureListener { e->
                progressDialog.dismiss()
                Toast.makeText(this, "Failed Creating Account due to ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun updateUserInfo() {

        //saving data info in real time database
        progressDialog.setMessage("Saving User Info...")
        val timestamp=System.currentTimeMillis()

        val uid = firebaseAuth.uid

        val hashMap: HashMap<String, Any?> = HashMap()
        hashMap["uid"] = uid
        hashMap["name"] = name
        hashMap["email"] = email
        hashMap["Phone No"] = phoneno
        hashMap["DOB"] = date
        hashMap["profile image"] = ""
        hashMap["prn no"] = ""
        hashMap["userType"]= "user"
        hashMap["timestamp"] = timestamp


        //set data to db
        val ref = FirebaseDatabase.getInstance().getReference("Users")
        ref.child(uid!!)
            .setValue(hashMap)
            .addOnSuccessListener {
                progressDialog.dismiss()
                Toast.makeText(this, "Account Created...", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@RegisterActivity , DashboardstudentActivity::class.java))
                finish()
            }
            .addOnFailureListener { e->
                progressDialog.dismiss()
                Toast.makeText(this, "Failed Saving User info Due to ${e.message}", Toast.LENGTH_SHORT).show()
            }

    }
}