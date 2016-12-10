package com.github.knightliao.canalx.db.fetch;

import javax.sql.DataSource;

/**
 * @author knightliao
 * @date 2016/11/28 11:25
 */
public interface DbDataContextAware {

    void setDbDataContext(DbDataContext dbDataContext) throws ClassNotFoundException;

    void setDataSource(DataSource dataSource);
}
