package tn.esprit.miniprojectforintegratedprojectsmanagement.ui.activity


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.miniprojectforintegratedprojectsmanagement.ui.activity.SigninActivity
import tn.esprit.miniprojectforintegratedprojectsmanagement.R
import tn.esprit.miniprojectforintegratedprojectsmanagement.models.User
import tn.esprit.miniprojectforintegratedprojectsmanagement.utils.ApiInterface
import tn.esprit.miniprojectforintegratedprojectsmanagement.sharedPrefs.SharedPrefs
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private var btntosignin: Button? = null
    private var btnlogin: Button? = null
    private var txtLayoutFullName: TextInputLayout? = null
    private lateinit var txtLayoutPassword: TextInputLayout
    private var txtEmail: TextInputEditText? = null
    private var txtPassword: TextInputEditText? = null
    lateinit var PREF_USERNAME: SharedPreferences
    @Inject
    lateinit var services:ApiInterface

    @Inject
    lateinit var sharedPrefs: SharedPrefs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        val toolbar: Toolbar = findViewById(R.id.app_bar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Welcome"

        PREF_USERNAME=getSharedPreferences("PREF_USERNAME", Context.MODE_PRIVATE)

        txtLayoutFullName = findViewById(R.id.txtLayoutFullName)
        txtLayoutPassword = findViewById(R.id.txtLayoutPassword)
        txtEmail = findViewById(R.id.txtEmail)
        txtPassword = findViewById(R.id.txtPassword)

        btntosignin = findViewById(R.id.btntosignin)
        btnlogin = findViewById(R.id.btnlogin)

        btntosignin!!.setOnClickListener {
            clickSignIn()
        }

        btnlogin!!.setOnClickListener {
            clickLogIn()
        }


    }

    private fun clickLogIn() {
        if (inputcheck()) {
           /* val mainIntent = Intent(this, MainActivity::class.java).apply {
            }
            startActivity(mainIntent)*/

            val userin = User(txtEmail!!.text.toString(),txtPassword!!.text.toString())


            // val response = ServiceBuilder.buildService(ApiInterface::class.java)
            services.signin(userin).enqueue(
                object : Callback<User> {
                    override fun onResponse(
                        call: Call<User>,
                        response: Response<User>
                    ) {
                        val userdata = response.body()


                        if (userdata != null){
                            sharedPrefs.saveToken(userdata.token!!)

println("here " + userdata.token)
                           ////// Toast.makeText(applicationContext, userdata.token, Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            startActivity(intent)


                            // Toast.makeText(this@LoginActivity,"Login Success", Toast.LENGTH_SHORT).show()
                        }else{
                            Toast.makeText(this@LoginActivity, "User not found check your ID or passord", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<User>, t: Throwable) {
                        Toast.makeText(this@LoginActivity,t.toString(), Toast.LENGTH_LONG).show()
                    }

                }
            )
        }
    }

    private fun inputcheck(): Boolean {

        txtLayoutFullName!!.error = null
        txtLayoutPassword.error = null
        if (txtEmail!!.text?.isEmpty()!!) {
            // Set error text
            txtLayoutFullName!!.error = getString(R.string.erroremail)
            return false
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(txtEmail?.text!!).matches()) {
            txtLayoutFullName!!.error = getString(R.string.checkYourEmail)
            return false
        }
        if (txtPassword!!.text?.isEmpty()!!){
            txtLayoutPassword.error = getString(R.string.errorpassword)
            return false
        }

        if (txtPassword?.length()!! < 5) {
            txtLayoutPassword!!.error = getString(R.string.mustBeAtLeast5)
            return false
        }
        // Clear error text
        txtLayoutFullName!!.error = null
        // Clear error text
        txtLayoutPassword.error = null
        return true
    }

    private fun clickSignIn() {


        val mainIntent = Intent(this, SigninActivity::class.java).apply {
        }
        startActivity(mainIntent)

    }
}

/*
    private fun doLogin(){
        if (validate()){
            val apiInterface = ApiInterface.create()
            progBar.visibility = View.VISIBLE

           window.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )

            apiInterface.seConnecter(txtLogin.text.toString(), txtPassword!!.text.toString()).enqueue(object : Callback<User> {

                override fun onResponse(call: Call<User>, response: Response<User>) {

                    val user = response.body()

                    if (user != null){
                        Toast.makeText(this@LoginActivity, "Login Success", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this@LoginActivity, "User not found", Toast.LENGTH_SHORT).show()
                    }

                    progBar.visibility = View.INVISIBLE
                   window.clearFlags( WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    Toast.makeText(this@LoginActivity, "Connexion error!", Toast.LENGTH_SHORT).show()

                    progBar.visibility = View.INVISIBLE
                   window.clearFlags( WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                }

            })

       }
    }*/

