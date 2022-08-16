package com.car.scth.db;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by mac on 16/12/9.
 */

public class RecordTimeBeanDao {

    private Dao<RecordTimeModel, Integer> RecordOpe;//录音历史时间
    private OrmDbHelper helper;

    public static RecordTimeBeanDao instance;

    public static RecordTimeBeanDao getInstance(Context context) {
        if (instance == null) {
            synchronized (RecordTimeBeanDao.class) {
                if (instance == null) {
                    instance = new RecordTimeBeanDao();
                    instance.helper = OrmDbHelper.getHelper(context);
                    try {
                        instance.RecordOpe = instance.helper.getDao(RecordTimeModel.class);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return instance;
    }


    /**
     * 录音  插入数据库
     */
    public RecordTimeModel createRecordDBBean(RecordTimeModel bean) {
        try {
            return RecordOpe.createIfNotExists(bean);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 录音  修改数据
     */
    public int updataRecordDBBean(RecordTimeModel bean) {
        try {
            return RecordOpe.update(bean);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


    /**
     * 根据主键 查询一个  录音列表
     *
     * @param maikey
     * @return
     */
    public List<RecordTimeModel> getOneByMainKey(String maikey) {
        try {
            PreparedQuery<RecordTimeModel> prepare = RecordOpe.queryBuilder().
                    where().eq(RecordTimeModel.KEY, maikey).prepare();
            return RecordOpe.query(prepare);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询所有
     *
     * @return
     */
    public List<RecordTimeModel> getAll() {
        try {
            return RecordOpe.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 单个删除
     */
    public int deleteContact(RecordTimeModel recordModel) {
        try {
            return RecordOpe.delete(recordModel);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 批量删除
     */
    public int batchDeleteContact(List<RecordTimeModel> recordModel) {
        try {
            return RecordOpe.delete(recordModel);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


    /**
     * 删除所有数据
     *
     * @return
     */
    public int deleteAll() {
        try {
            int i = RecordOpe.executeRaw("delete TABLE db_record_time");
            return i;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }


    public OrmDbHelper getHelper() {
        return helper;
    }
}
