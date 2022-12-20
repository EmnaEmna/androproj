package tn.esprit.miniprojectforintegratedprojectsmanagement.utils

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import tn.esprit.miniprojectforintegratedprojectsmanagement.models.*


interface ApiInterface {


    @POST("api/users/login")
    fun signin(@Body userin: User): Call<User>

    @POST("/api/users/register")
    fun signup(@Body NewUser: User): Call<User>

    @POST("/api/group/creategroup")
    fun addgroup(@Body newGroup: Group): Call<Group>

    @POST("/api/userp/resetPwd")
    fun resetPassword(@Body NewProfilPassword: ProfilPassword): Call<ProfilPassword>

    @POST("api/userp/editus")
    fun editName(@Body NewProfileName: ProfileName): Call<ProfileName>

    @GET("/api/group/getgroups")
    fun getgroups(): Call<List<Group>>

    @POST("/api/project/getProject")
    fun getProjects(@Body groupdId: GroupId): Call<List<Project>>


    @POST("/api/project/createproject")
    fun createproject(@Body NewProject: Project): Call<Project>

    /*companion object {

        var BASE_URL = "http://192.168.1.202:9090/"

        fun create() : ApiInterface {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()

            return retrofit.create(ApiInterface::class.java)
        }

    }*/

}