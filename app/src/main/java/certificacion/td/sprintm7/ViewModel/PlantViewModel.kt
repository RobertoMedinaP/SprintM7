package certificacion.td.sprintm7.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import certificacion.td.sprintm7.Model.Local.DataBase.PlantDb
import certificacion.td.sprintm7.Model.Local.Entities.PlantByIdEntity
import certificacion.td.sprintm7.Model.Local.Entities.PlantEntity
import certificacion.td.sprintm7.Model.PlantRepository
import kotlinx.coroutines.launch

class PlantViewModel(application: Application): AndroidViewModel(application) {

    private val repository: PlantRepository

    private val plantByIdLiveData = MutableLiveData<PlantByIdEntity>()

    private var idSelected: String = "-1"

    init {

        val plantDao = PlantDb.getDataBase(application).getPlantDao()
        repository = PlantRepository(plantDao)

        viewModelScope.launch {
            repository.fetchPlant()
        }
    }

    fun getPlantList(): LiveData<List<PlantEntity>> = repository.plantListLiveData

    fun getPlantId(): LiveData<PlantByIdEntity> = plantByIdLiveData

    fun getPlantByIdFromInternet(id: String) = viewModelScope.launch {

        val plantId = repository.fetchIdPlant(id)
        plantId?.let {
            plantByIdLiveData.postValue(it)
        }
    }

}