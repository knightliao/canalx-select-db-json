package com.knightliao.canalx.db.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.knightliao.canalx.db.DbDataContext;
import com.knightliao.canalx.db.DbDataContextAware;
import com.knightliao.canalx.db.DbFetcher;

/**
 * @author knightliao
 * @date 2016/11/28 11:20
 */
public class DbFetcherImpl implements DbFetcher, DbDataContextAware {

    private static final Logger logger = LoggerFactory.getLogger(DbFetcherImpl.class);

    private DbWrapper dbWrapper;

    public List<Map<String, Object>> executeSql(String sql) {

        Connection conn = null;

        List<Map<String, Object>> listOfMaps = null;

        try {
            conn = dbWrapper.getConnection();

            QueryRunner queryRunner = new QueryRunner();
            listOfMaps = queryRunner.query(conn, sql, new MapListHandler());

            return listOfMaps;

        } catch (SQLException e) {

            logger.error(e.getMessage() + " sql: " + sql, e);

        } catch (SecurityException e) {

            logger.error(e.getMessage() + " sql: " + sql, e);

        } finally {
            dbWrapper.cleanUp(conn, null, null);
        }

        return new ArrayList<Map<String, Object>>();
    }

    public void setDbDataContext(DbDataContext dbDataContext) throws ClassNotFoundException {

        dbWrapper = new DbWrapper();
        dbWrapper.setup(dbDataContext.getDriverClass(), dbDataContext.getDbUrl(), dbDataContext.getUserName(),
                dbDataContext.getUserPassword());
    }
}
