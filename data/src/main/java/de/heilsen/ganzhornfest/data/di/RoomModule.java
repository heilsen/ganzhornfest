package de.heilsen.ganzhornfest.data.di;

import android.arch.persistence.room.Room;
import android.content.Context;

import dagger.Module;
import dagger.Provides;
import de.heilsen.ganzhornfest.data.room.AppDatabase;

@Module
public class RoomModule {
    private Context context;

    public RoomModule(Context context) {
        this.context = context;
    }

    @Provides
    @RepositoryScope
    public AppDatabase roomDb() {
        return Room.databaseBuilder(context, AppDatabase.class, "app-database").build();
    }
}
