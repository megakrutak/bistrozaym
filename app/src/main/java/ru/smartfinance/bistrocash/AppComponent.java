package ru.smartfinance.bistrocash;

import dagger.Component;

@Component(modules = {AppModule.class})
public interface AppComponent {
    Helper getHelper();
}
