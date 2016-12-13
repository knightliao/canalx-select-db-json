package com.github.knightliao.canalx.db.controller.sql;

/**
 * @author knightliao
 * @date 2016/12/13 16:39
 */
public interface ISqlGenMgr {

    String genSql(String tableId, String key, String keyValue);
}
