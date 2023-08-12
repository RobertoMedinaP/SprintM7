package certificacion.td.sprintm7.Model

import certificacion.td.sprintm7.Model.Local.Entities.PlantByIdEntity
import certificacion.td.sprintm7.Model.Local.Entities.PlantEntity
import certificacion.td.sprintm7.Model.Remote.FromInternet.Plant
import certificacion.td.sprintm7.Model.Remote.FromInternet.PlantById

fun fromInternetPLantEntity(plantList: List<Plant>): List<PlantEntity> {

    return plantList.map {
        PlantEntity(
            id = it.id,
            nombre = it.nombre,
            tipo = it.tipo,
            imagen = it.imagen,
            descripcion = it.descripcion

        )
    }
}

fun fromInternetPlantByIdEntity(plantById: PlantById): PlantByIdEntity {

    return PlantByIdEntity(
        id = plantById.id,
        nombre = plantById.nombre,
        tipo = plantById.tipo,
        imagen = plantById.imagen,
        descripcion = plantById.descripcion,
    )
}