package com.github.knightliao.canalx.db.fetch;

import javax.sql.DataSource;

import com.github.knightliao.canalx.db.fetch.impl.DbFetcherImpl;

/**
 * @author knightliao
 * @date 2016/11/28 11:19
 */
public class DbFetcherFactory {

    public static DbFetcher getDefaultDbFetcher(String driverClass, String dbUrl, String userName, String userPassword)
            throws ClassNotFoundException {

        DbFetcher dbFetcher = new DbFetcherImpl();

        DbDataContext dbDataContext = new DbDataContext(driverClass, dbUrl, userName, userPassword);
        ((DbFetcherImpl) dbFetcher).setDbDataContext(dbDataContext);

        return dbFetcher;
    }

    public static DbFetcher getDefaultDbFetcher(DataSource dataSource)
            throws ClassNotFoundException {

        DbFetcher dbFetcher = new DbFetcherImpl();

        ((DbFetcherImpl) dbFetcher).setDataSource(dataSource);

        return dbFetcher;
    }
}
