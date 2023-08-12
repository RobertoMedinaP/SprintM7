package certificacion.td.sprintm7.Model.Local.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "plant_table")
data class PlantEntity(

    @PrimaryKey
    val id: Int,
    val nombre: String,
    val tipo: String,
    val imagen: String,
    val descripcion: String
)