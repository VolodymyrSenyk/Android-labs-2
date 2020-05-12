package com.senyk.volodymyr.quiz.data.database.databasecopyframework;

import androidx.sqlite.db.SupportSQLiteOpenHelper;

public class AssetSQLiteOpenHelperFactory implements SupportSQLiteOpenHelper.Factory {

    @Override
    public SupportSQLiteOpenHelper create(SupportSQLiteOpenHelper.Configuration configuration) {
        return new AssetSQLiteOpenHelper(
                configuration.context,
                configuration.name,
                configuration.callback.version,
                configuration.callback
        );
    }
}
