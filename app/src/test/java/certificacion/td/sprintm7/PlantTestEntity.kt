package certificacion.td.sprintm7

import certificacion.td.sprintm7.Model.Local.Entities.PlantEntity
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class PlantTestEntity {

    private lateinit var plantEntity: PlantEntity

    @Before
    fun setup() {
        plantEntity = PlantEntity(
            id = 1,
            nombre = "plantita",
            tipo = "interior",
            imagen = "foto",
            descripcion = "descripcion"
        )
    }

    @After
    fun tearDown() {

    }

    @Test
    fun testPlant() {
        assert(plantEntity.id == 1)
        assert(plantEntity.nombre == "plantita")
        assert(plantEntity.tipo == "interior")
        assert(plantEntity.imagen == "foto")
        assert(plantEntity.descripcion == "descripcion")
    }
}