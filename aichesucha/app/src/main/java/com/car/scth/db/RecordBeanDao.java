package com.car.scth.db;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by mac on 16/12/9.
 */

public class RecordBeanDao {

    private Dao<RecordModel, Integer> RecordOpe;//录音
    private OrmDbHelper helper;

    public static RecordBeanDao instance;

    public static RecordBeanDao getInstance(Context context) {
        if (instance == null) {
            synchronized (RecordBeanDao.class) {
                if (instance == null) {
                    instance = new RecordBeanDao();
                    instance.helper = OrmDbHelper.getHelper(context);
                    try {
                        instance.RecordOpe = instance.helper.getDao(RecordModel.class);
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
    public RecordModel createRecordDBBean(RecordModel bean) {
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
    public int updataRecordDBBean(RecordModel bean) {
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
    public List<RecordModel> getOneByMainKey(String maikey) {
        try {
            PreparedQuery<RecordModel> prepare = RecordOpe.queryBuilder().
                    where().eq(RecordModel.KEY, maikey).prepare();
            return RecordOpe.query(prepare);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据名字 查询一个  录音列表
     *
     * @param name
     * @return
     */
    public List<RecordModel> getOneByMainName(String name) {
        try {
            PreparedQuery<RecordModel> prepare = RecordOpe.queryBuilder().
                    where().eq(RecordModel.NAME, name).prepare();
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
    public List<RecordModel> getAll() {
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
    public int deleteContact(RecordModel recordModel) {
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
    public int batchDeleteContact(List<RecordModel> recordModel) {
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
            int i = RecordOpe.executeRaw("delete TABLE db_record_bean");
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
