package ru.smartfinance.bistrozaym;

import android.app.Application;

import com.yandex.metrica.YandexMetrica;
import com.yandex.metrica.push.YandexMetricaPush;

/**
 * Created by mihail on 18.03.2017.
 */

public class BistrozaymApp extends Application {

    final private String APP_METRICA_API_KEY = "f68de7ae-dc8f-4acf-826a-9d34646e6c4b";

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
    }
}
