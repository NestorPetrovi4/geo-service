package ru.netology.i18n;

import ru.netology.entity.Country;

public class LocalizationServiceImplMock implements LocalizationService{

    @Override
    public String locale(Country country) {
        switch (country) {
            case RUSSIA:
                return "Добро пожаловать";
            default:
                return "Welcome";
        }
    }
}
