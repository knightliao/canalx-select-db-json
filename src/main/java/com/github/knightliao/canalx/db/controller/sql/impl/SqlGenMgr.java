package com.github.knightliao.canalx.db.controller.sql.impl;

import com.github.knightliao.canalx.db.controller.sql.ISqlGenMgr;

/**
 * @author knightliao
 * @date 2016/12/13 16:40
 */
public class SqlGenMgr implements ISqlGenMgr {

    @Override
    public String genSql(String tableId, String key, String keyValue) {

        return String.format("select * from %s where %s=%s", tableId, key, keyValue);
    }
}
