package com.leory.commonlib.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.util.SimpleArrayMap;

/**
 * Describe : shared preference 的工具类
 * Author : leory
 * Date : 2019-06-19
 */

public class SPUtils {
    /**
     * 以表名缓存各张表
     */
    private static SimpleArrayMap<String, SPUtils> SP_UTILS_MAP = new SimpleArrayMap<>();
    private SharedPreferences sp;

    private SPUtils(final String spName) {
        sp = AppUtils.getApplication().getSharedPreferences(spName, Context.MODE_PRIVATE);
    }

    public static SPUtils getInstance(String spName) {
        SPUtils spUtils = SP_UTILS_MAP.get(spName);
        if (spUtils == null) {
            synchronized (SPUtils.class) {
                if (spUtils == null) {
                    spUtils = new SPUtils(spName);
                    SP_UTILS_MAP.put(spName, spUtils);
                }
            }
        }
        return spUtils;
    }

    /**
     * 保存String数据
     *
     * @param key
     * @param value
     * @param isCommit 是否提交
     */
    public SPUtils put(@NonNull String key, String value, boolean isCommit) {
        if (isCommit) {
            sp.edit().putString(key, value).commit();
        } else {
            sp.edit().putString(key, value).apply();
        }
        return this;
    }

    /**
     * 保存String数据
     *
     * @param key
     * @param value
     */
    public SPUtils put(@NonNull String key, String value) {
        return put(key, value, true);//默认提交
    }

    /**
     * 保存int数据
     *
     * @param key
     * @param value
     * @param isCommit 是否提交
     */
    public SPUtils put(@NonNull String key, int value, boolean isCommit) {
        if (isCommit) {
            sp.edit().putInt(key, value).commit();
        } else {
            sp.edit().putInt(key, value).apply();
        }
        return this;
    }

    /**
     * 保存int数据
     *
     * @param key
     * @param value
     */
    public SPUtils put(@NonNull String key, long value) {
        return put(key, value, true);
    }

    /**
     * 保存long数据
     *
     * @param key
     * @param value
     * @param isCommit 是否提交
     */
    public SPUtils put(@NonNull String key, long value, boolean isCommit) {
        if (isCommit) {
            sp.edit().putLong(key, value).commit();
        } else {
            sp.edit().putLong(key, value).apply();
        }
        return this;
    }

    /**
     * 保存long数据
     *
     * @param key
     * @param value
     */
    public SPUtils put(@NonNull String key, int value) {
        return put(key, value, true);
    }

    /**
     * 保存boolean数据
     *
     * @param key
     * @param value
     * @param isCommit 是否提交
     */
    public SPUtils put(@NonNull String key, boolean value, boolean isCommit) {
        if (isCommit) {
            sp.edit().putBoolean(key, value).commit();
        } else {
            sp.edit().putBoolean(key, value).apply();
        }
        return this;
    }

    /**
     * 保存boolean数据
     *
     * @param key
     * @param value
     */
    public SPUtils put(@NonNull String key, boolean value) {
        return put(key, value, true);
    }

    /**
     * 获取字符类型的数据
     *
     * @param key
     * @param defaultValue 默认值
     * @return
     */
    public String getString(@NonNull String key, final String defaultValue) {
        return sp.getString(key, defaultValue);
    }

    /**
     * 获取字符类型的数据
     *
     * @param key
     * @return
     */
    public String getString(@NonNull String key) {
        return sp.getString(key, "");//默认值为空
    }

    /**
     * 获取Long的数据
     *
     * @param key
     * @param defaultValue 默认值
     * @return
     */
    public long getLong(@NonNull String key, final long defaultValue) {
        return sp.getLong(key, defaultValue);
    }

    /**
     * 获取Long的数据
     *
     * @param key
     * @return
     */
    public long getLong(@NonNull String key) {
        return sp.getLong(key, 0);//默认值为0
    }

    /**
     * 获取整型的数据
     *
     * @param key
     * @param defaultValue 默认值
     * @return
     */
    public int getInt(@NonNull String key, final int defaultValue) {
        return sp.getInt(key, defaultValue);
    }

    /**
     * 获取整型的数据
     *
     * @param key
     * @return
     */
    public int getInt(@NonNull String key) {
        return sp.getInt(key, 0);//默认值为0
    }

    /**
     * 获取布尔类型的数据
     *
     * @param key
     * @param defaultValue 默认值
     * @return
     */
    public boolean getBoolean(@NonNull String key, final boolean defaultValue) {
        return sp.getBoolean(key, defaultValue);
    }

    /**
     * 获取布尔类型的数据
     *
     * @param key
     * @return
     */
    public boolean getBoolean(@NonNull String key) {
        return sp.getBoolean(key, false);//默认值为false
    }
}
