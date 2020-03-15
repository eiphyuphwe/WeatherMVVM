package eh.com.spweathertest

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import eh.com.spweathertest.model.SearchResponse
import eh.com.spweathertest.model.WeatherResponse
import eh.com.spweathertest.viewmodel.MainViewModel
import io.reactivex.Observer
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private val mApplicationContext: Application? = null

    @Mock
    var searchResponseObserver: Observer<SearchResponse>? = null
    @Mock
    var weatherResponseObserver: Observer<WeatherResponse>? = null

    private var viewModel: MainViewModel? = null

    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = MainViewModel(mApplicationContext!!)
    }

    @Test
    fun testFecthSearchCountry_whenReturnData() {
        viewModel!!.search("Japan", "json")!!
            .observeForever((searchResponseObserver as androidx.lifecycle.Observer<in SearchResponse?>?)!!)
    }
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
}
