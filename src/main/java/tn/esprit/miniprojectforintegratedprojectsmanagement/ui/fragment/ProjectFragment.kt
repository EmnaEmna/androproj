package tn.esprit.miniprojectforintegratedprojectsmanagement.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.miniprojectforintegratedprojectsmanagement.R
import tn.esprit.miniprojectforintegratedprojectsmanagement.adapters.ProjectAdapter
import tn.esprit.miniprojectforintegratedprojectsmanagement.models.GroupId
import tn.esprit.miniprojectforintegratedprojectsmanagement.models.Project
import tn.esprit.miniprojectforintegratedprojectsmanagement.ui.activity.AddProjectActivity
import tn.esprit.miniprojectforintegratedprojectsmanagement.utils.ApiInterface
import javax.inject.Inject

@AndroidEntryPoint
class ProjectFragment : Fragment() {
    private val adapter by lazy {
        ProjectAdapter(onItemClicked = { })
    }

    lateinit var recyclerProjectView: RecyclerView

    @Inject
    lateinit var services: ApiInterface


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_project, container, false)
        recyclerProjectView = rootView.findViewById(R.id.recyclerProjectView)
        // get parameters from bundle
        val bundle = this.arguments
        var groupId = ""
        if(bundle?.get("id") != null){
            groupId = bundle.get("id").toString()
        }
        val addProjectButton = rootView.findViewById<Button>(R.id.addProject)
        addProjectButton.setOnClickListener {
            Toast.makeText(context, "Add Project", Toast.LENGTH_SHORT).show()
            //navigate to activity and pass group id parameter
            val addProjectActivity = Intent(requireContext(), AddProjectActivity::class.java)
            addProjectActivity.putExtra("groupId", groupId)
            startActivity(addProjectActivity)
        }

        //show groupId when clicked on the groupp_roject in the previous fragment
        /////////Toast.makeText(requireContext(), groupId, Toast.LENGTH_SHORT).show()
        val grpId = GroupId(groupId)
        services.getProjects(grpId).enqueue(object : Callback<List<Project>> {
            override fun onResponse(
                call: Call<List<Project>>, response: Response<List<Project>>
            ) {
                val projects = response.body()
                if (projects != null) {
                    val myLayoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    adapter.updateList(projects.toMutableList())
                    recyclerProjectView.layoutManager = myLayoutManager
                    recyclerProjectView.adapter = adapter
                } else {
                    Toast.makeText(context, "No projects found", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Project>>, t: Throwable) {
            }
        })
        return rootView

    }

}