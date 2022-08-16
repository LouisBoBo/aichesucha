package com.car.scth.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mac on 16/12/9.
 */

public class OrmDbHelper extends OrmLiteSqliteOpenHelper {

    private static final String TABLE_NAME = "sqlite.db";
    private static final int dbVersion = 2;//
    private Map<String, Dao> daos = new HashMap<String, Dao>();
    private static OrmDbHelper instance;

    private OrmDbHelper(Context context) {
        super(context, TABLE_NAME, null, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, RecordModel.class);
            TableUtils.createTable(connectionSource, RecordTimeModel.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, RecordModel.class, true);
            TableUtils.dropTable(connectionSource,RecordTimeModel.class,true);
            onCreate(sqLiteDatabase, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    /**
     * 单例获取该Helper
     *
     * @param context
     * @return
     */
    public static synchronized OrmDbHelper getHelper(Context context) {
        context = context.getApplicationContext();
        if (instance == null) {
            synchronized (OrmDbHelper.class) {
                if (instance == null)
                    instance = new OrmDbHelper(context);
            }
        }


        return instance;
    }

    @Override
    public Dao getDao(Class clazz) throws SQLException {
        Dao dao = null;
        String className = clazz.getSimpleName();
        if (daos.containsKey(className)) {
            dao = daos.get(className);
        }
        if (dao == null) {
            dao = super.getDao(clazz);
            daos.put(className, dao);
        }
        return dao;
    }
}
