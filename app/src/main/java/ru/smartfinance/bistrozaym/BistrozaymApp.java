package ru.smartfinance.bistrozaym;

import android.app.Application;

import com.yandex.metrica.YandexMetrica;
import com.yandex.metrica.push.YandexMetricaPush;

import dagger.Component;
import dagger.internal.DaggerCollections;

/**
 * Created by mihail on 18.03.2017.
 */

public class BistrozaymApp extends Application {

    final private String APP_METRICA_API_KEY = "57b14ecf-07ec-4b46-b04b-dec104e9b0ca";
    private static AppComponent component;
    private static AppModule module;

    @Override
    public void onCreate() {
        super.onCreate();
        // Инициализация AppMetrica SDK
        YandexMetrica.activate(getApplicationContext(), APP_METRICA_API_KEY);
        // Инициализация AppMetrica Push SDK
        YandexMetricaPush.init(getApplicationContext());
        // Отслеживание активности пользователей
        YandexMetrica.enableActivityAutoTracking(this);
        YandexMetrica.setCollectInstalledApps(true);

        AppModule module = new AppModule(this);
        component = DaggerAppComponent.builder().appModule(module).build();
    }

    public static AppComponent getComponent() {
        return component;
    }

    public static AppModule getModule() {
        return module;
    }
}
