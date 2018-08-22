package de.heilsen.ganzhornfest.data.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//TODO: use Contract classes
//TODO: finish sqlite implementation
public class GanzhornfestDatabaseHelper extends SQLiteOpenHelper {
    // Database Info
    private static final String DATABASE_NAME = "ganzhornfest.db";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    private static final String TABLE_CLUBS = "clubs";
    private static final String TABLE_OFFERS = "offers";

    // Club Table
    private static final String KEY_CLUB_ID= "id";
    private static final String KEY_CLUB_NAME = "name";
    private static final String KEY_CLUB_DESCRIPTION = "description";

    public GanzhornfestDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CLUBS_TABLE = "CREATE TABLE " + TABLE_CLUBS +
                                    "(" +
                                        KEY_CLUB_ID + "  INTEGER PRIMARY KEY," +
                                        KEY_CLUB_NAME + " TEXT," +
                                        KEY_CLUB_DESCRIPTION + " DESCRIPTION"
                                    + ")";
        db.execSQL(CREATE_CLUBS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
