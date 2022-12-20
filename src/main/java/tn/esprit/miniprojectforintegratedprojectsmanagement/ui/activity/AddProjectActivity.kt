package tn.esprit.miniprojectforintegratedprojectsmanagement.ui.activity

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.miniprojectforintegratedprojectsmanagement.R
import tn.esprit.miniprojectforintegratedprojectsmanagement.models.Project
import tn.esprit.miniprojectforintegratedprojectsmanagement.utils.ApiInterface
import javax.inject.Inject

@AndroidEntryPoint
class AddProjectActivity : AppCompatActivity() {
    private var btnSaveProject: Button? = null
    private lateinit var txtLayoutProjectName: TextInputLayout
    private var txtProjectName: TextInputEditText? = null
    private lateinit var txtLayoutlinkgit: TextInputLayout
    private var txtlinkgit: TextInputEditText? = null
    private lateinit var txtLayoutcomment: TextInputLayout
    private var txtcomment: TextInputEditText? = null

    @Inject
    lateinit var services: ApiInterface


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_add)

        btnSaveProject = findViewById(R.id.btnSaveProject)
        txtLayoutProjectName = findViewById(R.id.txtLayoutProjectName)
        txtProjectName = findViewById(R.id.txtProjectName)
        txtLayoutlinkgit = findViewById(R.id.txtLayoutlinkgit)
        txtlinkgit = findViewById(R.id.txtlinkgit)
        txtLayoutcomment = findViewById(R.id.txtLayoutcomment)
        txtcomment = findViewById(R.id.txtcomment)

        val toolbar: Toolbar = findViewById(R.id.app_bar)

        toolbar.setNavigationIcon(R.drawable.ic_back)
        setSupportActionBar(toolbar)

        toolbar.setNavigationOnClickListener {
            finish()
        }

        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = " let's Go to work "




        btnSaveProject!!.setOnClickListener {
            addProject()
        }

    }


    private fun addProject() {
        //if (inputcheck()) {
        //Toast.makeText(this, "check ok ", Toast.LENGTH_SHORT).show()

        //get params from bundle
        val bundle = intent.extras
        val groupId = bundle?.getString("groupId")

        val proj = Project(
            "",
            txtProjectName!!.text.toString(),
            txtlinkgit!!.text.toString(),
            txtcomment!!.text.toString(),
            groupId!!
        )


        //val response = ServiceBuilder.buildService(ApiInterface::class.java)

        services.createproject(proj).enqueue(object : Callback<Project> {
            override fun onResponse(
                call: Call<Project>, response: Response<Project>
            ) {
                val test = response.body()
                if (test != null) {
                    Toast.makeText(
                        this@AddProjectActivity, "add assignement Success", Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        this@AddProjectActivity, "add assignement error", Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<Project>, t: Throwable) {
                Toast.makeText(this@AddProjectActivity, t.toString(), Toast.LENGTH_LONG).show()
            }

        })
        // }

    }
}