package certificacion.td.sprintm7

import android.util.Log
import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import certificacion.td.sprintm7.Model.Local.DataBase.PlantDb
import certificacion.td.sprintm7.Model.Local.Entities.PlantByIdEntity
import certificacion.td.sprintm7.Model.Local.Entities.PlantEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.CoreMatchers.not
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runner.manipulation.Ordering.Context
import java.io.IOException
import java.lang.Exception
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException
import kotlin.jvm.Throws

//Prueba pasada :-)
@RunWith(AndroidJUnit4::class)
class PlantDaoInstrumentalTest {
    //la regla dice que haremos las pruebas en el hilo principal
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    //private lateinit var phoneDao: PhoneDao
    private lateinit var db: PlantDb

    @Before
    fun setupDB() {
        //setteamos la base de datos antes del test
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            PlantDb::class.java
        ).build()
    }

    @After
    @Throws(IOException::class)
    fun shutDown() {
        //despues del test apagamos la base de datos
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun coroutineDBInsertPlant() {
        //primer test instanciamos el dao
        val plantDao = db.getPlantDao()

        //creamos 2 plantas
        val plant1 = PlantEntity(1, "Geranio", "Flor", "imagen", "Flor")
        val plant2 = PlantEntity(2, "Ruda", "Hierba", "foto", "Interior")
        val plantas = listOf(plant1, plant2)

        //en la coroutine

        runBlocking(Dispatchers.Default) {
            //insertamos phone 1 y2(phones)
            plantDao.insertAllPlants(plantas)
        }

        plantDao.getAllPlants().observeForever {
            assertThat(plantas.size, equalTo(2))
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    @Throws(Exception::class)
    fun insertPlantById() {
        //acá probaremos la otra funcion del dao
        val plantDao = db.getPlantDao()

        val idPlant = PlantByIdEntity(
            2,
            "planta2",
            "tipo2",
            "imagen2",
            "desc2"
        )


        //variable para manejar coroutine en ambiente de prueba
        val testDispatcher = TestCoroutineDispatcher()
        Dispatchers.setMain(testDispatcher)

        testDispatcher.runBlockingTest {
            plantDao.insertPlantByID(idPlant)
            val plantLiveData = plantDao.getPlantById("2")
            val plantValue = plantLiveData.getOrAwaitValue()

            plantDao.getPlantById("2")
            assertThat(plantValue?.tipo, equalTo("tipo2"))
            assertThat(plantValue?.nombre, equalTo("planta2"))
        }

        Dispatchers.resetMain()

    }


    //funcion auxiliar obtiene o espera valor de un livedata
    fun <T> LiveData<T>.getOrAwaitValue(
        time: Long = 2,
        timeUnit: TimeUnit = TimeUnit.SECONDS,
        afterObserve: () -> Unit = {}
    ): T {
        var data: T? = null
        val latch = CountDownLatch(1)
        //el observer captura el valor del livedata
        val observer = object : Observer<T> {
            override fun onChanged(o: T) {
                data = o
                latch.countDown()
                this@getOrAwaitValue.removeObserver(this)
            }
        }
        this.observeForever(observer)

        afterObserve.invoke()

        // Si no se obtiene el valor en el tiempo esperado lanza una excepcion
        if (!latch.await(time, timeUnit)) {
            this.removeObserver(observer)
            throw TimeoutException("El valor del LiveData no se obtuvo en el tiempo esperado.")
        }

        @Suppress("UNCHECKED_CAST")
        return data as T


    }
}