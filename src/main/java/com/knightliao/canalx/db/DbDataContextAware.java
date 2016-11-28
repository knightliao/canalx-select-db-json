package com.knightliao.canalx.db;

/**
 * @author knightliao
 * @date 2016/11/28 11:25
 */
public interface DbDataContextAware {

    void setDbDataContext(DbDataContext dbDataContext) throws ClassNotFoundException;
}
