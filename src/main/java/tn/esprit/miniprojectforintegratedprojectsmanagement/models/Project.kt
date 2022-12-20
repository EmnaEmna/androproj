package tn.esprit.miniprojectforintegratedprojectsmanagement.models

import com.google.gson.annotations.SerializedName

data class Project (
    @SerializedName("_id")var id: String,
    @SerializedName("name")var name: String,
    @SerializedName("gitlink")var gitlink: String?= null,
    @SerializedName("text")var text: String?= null,
    @SerializedName("groupId")var groupId: String
)
