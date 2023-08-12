package certificacion.td.sprintm7.Model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import certificacion.td.sprintm7.Model.Local.Entities.PlantByIdEntity
import certificacion.td.sprintm7.Model.Local.PlantDao
import certificacion.td.sprintm7.Model.Remote.RetrofitClient

class PlantRepository(private val plantDao: PlantDao) {

    private val retrofitClient = RetrofitClient.retrofitInstance()

    val plantListLiveData = plantDao.getAllPlants()

    val plantByIdListLiveData = MutableLiveData<PlantByIdEntity>()

    suspend fun fetchPlant() {

        val service = kotlin.runCatching { retrofitClient.fetchPlantList() }

        service.onSuccess {
            when (it.code()) {
                in 200..299 -> it.body()?.let {
                    plantDao.insertAllPlants(fromInternetPLantEntity(it))
                }

                else -> Log.d("****Repo****", "${it.code()}-${it.errorBody()}")
            }
            service.onFailure {
                Log.e("<<<<<<Error>>>>>>>", "${it.message}")
            }
        }
    }

    suspend fun fetchIdPlant(id: String): PlantByIdEntity? {
        val service = kotlin.runCatching { retrofitClient.fetchPlantByID(id) }
        return service.getOrNull()?.body()?.let { plantID ->
            val plantByID = fromInternetPlantByIdEntity(plantID)
            plantDao.insertPlantByID(plantByID)
            plantByID
        }
    }

}