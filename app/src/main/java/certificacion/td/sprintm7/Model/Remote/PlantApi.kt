package certificacion.td.sprintm7.Model.Remote

import certificacion.td.sprintm7.Model.Remote.FromInternet.Plant
import certificacion.td.sprintm7.Model.Remote.FromInternet.PlantById
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PlantApi {

    @GET("plantas")
    suspend fun fetchPlantList(): Response<List<Plant>>

    @GET("plantas/{id}")
    suspend fun fetchPlantByID(@Path("id")id: String): Response<PlantById>

}