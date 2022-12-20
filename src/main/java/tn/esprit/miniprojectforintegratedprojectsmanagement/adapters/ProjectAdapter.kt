package tn.esprit.miniprojectforintegratedprojectsmanagement.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import tn.esprit.miniprojectforintegratedprojectsmanagement.databinding.ProjectSingleRowBinding
import tn.esprit.miniprojectforintegratedprojectsmanagement.models.Project

class ProjectAdapter(
    val onItemClicked: (Project) -> Unit,
) : RecyclerView.Adapter<ProjectAdapter.MyViewHolder>() {

    private var list: MutableList<Project> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            ProjectSingleRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    fun updateList(list: MutableList<Project>) {
        this.list = list
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        list.removeAt(position)
        notifyItemChanged(position)
    }

    override fun getItemCount(): Int {
        //get device key

        return list.size
    }

    inner class MyViewHolder(val binding: ProjectSingleRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Project) {
            binding.txtProjectName.text = item.name
            binding.txtlinkgit.text = item.gitlink
            binding.textcomment.text = item.text
            binding.root.setOnClickListener {
                println("project id: ${item.id}")
                onItemClicked(item)
            }
        }
    }
}