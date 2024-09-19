package it.saimao.maonote;

import android.content.Context;

import androidx.room.AutoMigration;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import it.saimao.maonote.dao.NoteDao;
import it.saimao.maonote.entities.DateConverter;
import it.saimao.maonote.entities.Note;

@Database(entities = {Note.class}, version = 2, exportSchema = false)
@TypeConverters({DateConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract NoteDao noteDao();

    private static AppDatabase appDatabase;

    public synchronized static AppDatabase getInstance(Context context) {
        if (appDatabase == null) {
            appDatabase = Room.databaseBuilder(context, AppDatabase.class, "my_note_application").allowMainThreadQueries().build();
        }
        return appDatabase;
    }

}
