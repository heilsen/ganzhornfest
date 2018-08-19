package de.heilsen.ganzhornfest.data.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {RoomClubEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
}
