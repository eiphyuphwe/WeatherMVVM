package eh.com.spweathertest.reposistories;

import eh.com.spweathertest.database.WeatherDAO;
import eh.com.spweathertest.model.Country;
import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.MaybeObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public class TestDao {

    WeatherDAO dao;
    TestDao(WeatherDAO dao)
    {
        this.dao = dao;
    }

    Country ctr;
    public Country getCountry(String area,String region)
    {

        dao.getCountry(area,region)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MaybeObserver<Country>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Country country) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        return ctr;
    }

    String str="";
    public String addCountry(final Country ctr)
    {

        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {

                ctr.setTime(System.currentTimeMillis());
                dao.insertAll(ctr);

            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {

                        str = "Success Added";
                    }

                    @Override
                    public void onError(Throwable e) {
                        str="error";
                    }
                });
        return str;
    }
}
