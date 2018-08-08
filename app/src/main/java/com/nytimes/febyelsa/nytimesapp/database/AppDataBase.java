package com.nytimes.febyelsa.nytimesapp.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {ArticleEntity.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {

    public abstract ArticlesDao articleDao();

}
