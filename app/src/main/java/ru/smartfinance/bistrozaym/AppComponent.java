package ru.smartfinance.bistrozaym;

import dagger.Component;
import dagger.Provides;

@Component(modules = {AppModule.class})
public interface AppComponent {
    Helper getHelper();
}
