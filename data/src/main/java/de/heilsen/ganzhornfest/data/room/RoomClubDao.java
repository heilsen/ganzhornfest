package de.heilsen.ganzhornfest.data.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface RoomClubDao {
    @Query("SELECT * FROM roomClubEntity")
    List<RoomClubEntity> getAll();

    @Query("SELECT * FROM roomClubEntity WHERE id == :i")
    RoomClubEntity get(int i);

    @Query("SELECT * FROM roomClubEntity WHERE name LIKE :name LIMIT 1")
    RoomClubEntity get(String name);

    @Insert
    void insertAll(RoomClubEntity... roomClubEntities);

}
