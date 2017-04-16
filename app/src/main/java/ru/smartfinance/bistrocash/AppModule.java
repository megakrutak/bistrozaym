package ru.smartfinance.bistrocash;

import android.app.Application;
import android.content.Context;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    Application app;

    AppModule(BistroCashApp app) {
        this.app = app;
    }
    @Provides
    Helper provideHelper(Context context) {
        return new Helper(context);
    }
    @Provides
    Context provideContext() {
        return app;
    }
}
