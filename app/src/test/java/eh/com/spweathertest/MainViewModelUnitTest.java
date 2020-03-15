package eh.com.spweathertest;

import android.app.Application;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import eh.com.currencyexchangeapp.network.RestApiService;
import eh.com.spweathertest.database.WeatherDAO;
import eh.com.spweathertest.model.Country;
import eh.com.spweathertest.model.Result;
import eh.com.spweathertest.model.SearchApi;
import eh.com.spweathertest.model.SearchResponse;
import eh.com.spweathertest.model.WeatherResponse;
import eh.com.spweathertest.reposistories.DatabaseReposistory;
import eh.com.spweathertest.reposistories.WeatherReposistory;
import eh.com.spweathertest.viewmodel.MainViewModel;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

@RunWith(MockitoJUnitRunner.class)
public class MainViewModelUnitTest {

    @Rule public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private Application mApplicationContext;
    @Mock
    WeatherDAO weatherDAO;




    WeatherReposistory weatherReposistory;
    DatabaseReposistory databaseReposistory;


    @Mock
    Observable<SearchResponse> searchResponseObservable;

    @Mock
    Observable<WeatherResponse> weatherResponseObservable;
    @Mock
    androidx.lifecycle.Observer<SearchResponse> searchResponseObserver;
    @Mock
    androidx.lifecycle.Observer<List<Country>> countryObserver;
    @Mock
    Observer<WeatherResponse> weatherResponseObserver;

    private MainViewModel viewModel;

    @Mock
    RestApiService apiService;


    @BeforeClass
    public static void setupClass() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(
                new Function<Callable<Scheduler>, Scheduler>() {
                    @Override
                    public Scheduler apply(Callable<Scheduler> __) throws Exception {
                        return Schedulers.trampoline();
                    }
                });
    }
    @Before
    public void setUp() throws Exception
    {
        MockitoAnnotations.initMocks(this);
        weatherReposistory = new WeatherReposistory(mApplicationContext);
        databaseReposistory = new DatabaseReposistory(weatherDAO);
        viewModel = new MainViewModel(mApplicationContext);


    }

    @Test
    public void testFecthSearchCountry_whenReturnData()
    {
        String key="d3b2c206cdfe43e4bf0140120201203";
         SearchResponse searchResponse = searchResponse = new SearchResponse(new SearchApi(new ArrayList<Result>()));

      //when(apiService.getSearch(key,"","json")).thenReturn(searchResponseObservable);


       viewModel.search("japan","json").observeForever(searchResponseObserver);
       //verify(searchResponseObserver).onChanged();

        assert (viewModel.search("","json").getValue()==null);



    }

    @Test
    public void testFecthSearchWeather_whenReturnData()
    {
        String key="d3b2c206cdfe43e4bf0140120201203";
        SearchResponse searchResponse = searchResponse = new SearchResponse(new SearchApi(new ArrayList<Result>()));

        //when(apiService.getWeather(key,"","json")).thenReturn(weatherResponseObservable);


        viewModel.loadDetailLocalWeather("japan","json").observeForever(weatherResponseObserver);
        //verify(searchResponseObserver).onChanged();




    }

    @Test
    public void saveDataAndGetCountryFromDB()
    {
        String key="d3b2c206cdfe43e4bf0140120201203";
        long time = 1355557;
        List<Country> countryList = new ArrayList<>();

        final Country country = new Country(0,"Yangon","Myanmar","Myanmar",
                "100.00","100.00",time);
        countryList.add(country);

        weatherDAO.insertAll(country);
        weatherDAO.insertAll(null);
        //weatherDAO.getAll().
        weatherDAO.delete(country);


    }


}
