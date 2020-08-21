package com.shilo.notejava.Repository;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.shilo.notejava.model.Note;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Calendar;
import java.util.TimeZone;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Note .class}, version = 1)
public abstract class NoteDatabase extends RoomDatabase {

    private static NoteDatabase instance;

    public abstract NoteDao noteDao();

    public static synchronized NoteDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), NoteDatabase.class, "note_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(callback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback callback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDBAsyncTask(instance).execute();
        }
    };

    private static class PopulateDBAsyncTask extends AsyncTask<Void, Void, Void> {
        private NoteDao noteDao;

        private PopulateDBAsyncTask(NoteDatabase db){
         noteDao = db.noteDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            /*String dateString = Calendar.getInstance().getTime().toString();
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd").withZone(ZoneOffset.UTC);
            TemporalAccessor date = fmt.parse(dateString);
            Instant time = Instant.from(date);

            DateTimeFormatter fmtOut = DateTimeFormatter.ofPattern("dd-MM-yyyy").withZone(ZoneOffset.UTC);
            fmtOut.toString();*/

            Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
            //noteDao.insert(new Note("first", "first content", timeFormat()));

            //noteDao.insert(new Note("second", "second content", timeFormat()));
            //noteDao.insert(new Note("third", "third content", timeFormat()));
            return null;
        }
    }

    /**
     * the time func for suitable time format
     * @return current time String
     */
    public static String timeFormat(){
        return Calendar.getInstance().getTime().toString().substring(4, 10);
    }
}
