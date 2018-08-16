package de.heilsen.ganzhornfest.app.di;

import dagger.MapKey;

@MapKey
@interface MyNumberClassKey {
    Class<? extends Number> value();
}

