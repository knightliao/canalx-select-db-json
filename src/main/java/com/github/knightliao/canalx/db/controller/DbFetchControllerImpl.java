package com.github.knightliao.canalx.db.controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.knightliao.canalx.db.IDbFetchController;
import com.github.knightliao.canalx.db.config.DbConfiguration;
import com.github.knightliao.canalx.db.config.TableConfig;
import com.github.knightliao.canalx.db.controller.sql.ISqlGenMgr;
import com.github.knightliao.canalx.db.controller.sql.impl.SqlGenMgr;
import com.github.knightliao.canalx.db.exception.CanalxSelectDbJsonInitException;
import com.github.knightliao.canalx.db.fetch.DbFetcher;
import com.github.knightliao.canalx.db.fetch.DbFetcherFactory;
import com.google.gson.Gson;

/**
 * @author knightliao
 * @date 2016/11/29 15:48
 */
public class DbFetchControllerImpl implements IDbFetchController {

    protected static final Logger logger = LoggerFactory.getLogger(DbFetchControllerImpl.class);

    protected Map<String, TableConfig> tableConfigMap = new HashMap<String, TableConfig>();

    // db fetcher
    protected Map<String, DbFetcher> dbFetcherMap = new HashMap<>();

    // file name
    protected static String FILE_NAME = "canalx-db-kv.xml";

    /**
     * @return
     *
     * @throws CanalxSelectDbJsonInitException
     */
    public Map<String, Map<String, String>> getInitDbKv() {

        Map<String, Map<String, String>> dbKv = new ConcurrentHashMap<String, Map<String, String>>(100);
        for (String tableId : tableConfigMap.keySet()) {

            TableConfig tableConfig = tableConfigMap.get(tableId);

            try {

                DbFetcher dbFetcher = dbFetcherMap.get(tableId);

                if (!tableConfig.getInitSql().isEmpty()) {

                    // original data
                    List<Map<String, Object>> dataJson = dbFetcher.executeSql(tableConfig.getInitSql());

                    // to kv
                    Map<String, String> dataKv = this.table2KV(dataJson, tableConfig);

                    //
                    dbKv.put(tableConfig.getIdentify(), dataKv);

                    logger.info("load sql:{} ok.", tableConfig.getInitSql());
                }
            } catch (SQLException e) {
                logger.error(e.toString());
            }
        }

        return dbKv;
    }

    /**
     * @param configFilePath
     *
     * @throws CanalxSelectDbJsonInitException
     */
    public void init(String configFilePath) throws CanalxSelectDbJsonInitException {

        URL url;
        if (configFilePath == null) {
            url = DbFetchControllerImpl.class.getClassLoader().getResource(FILE_NAME);
        } else {
            url = DbFetchControllerImpl.class.getClassLoader().getResource(configFilePath);
        }

        if (url == null) {
            throw new CanalxSelectDbJsonInitException("cannot load config: " + configFilePath);
        }

        /**
         *  table config
         */
        Map<String, TableConfig> tableConfigMap;
        try {
            tableConfigMap = DbConfiguration.parse(url);
        } catch (Exception e) {
            throw new CanalxSelectDbJsonInitException(e);
        }

        this.tableConfigMap = tableConfigMap;

        /**
         * int db fetcher
         */

        for (String tableId : tableConfigMap.keySet()) {

            TableConfig tableConfig = tableConfigMap.get(tableId);

            try {

                DbFetcher dbFetcher = DbFetcherFactory.getDefaultDbFetcher(tableConfig.getDriverClass(), tableConfig
                        .getDbUrl(), tableConfig.getUserName(), tableConfig.getPassword());
                dbFetcherMap.put(tableId, dbFetcher);

            } catch (ClassNotFoundException e) {
                logger.error(e.toString());
            }
        }
    }

    /**
     * @param tableId
     *
     * @return
     */
    public String getTableKey(String tableId) {
        if (tableConfigMap.keySet().contains(tableId)) {
            return tableConfigMap.get(tableId).getKeyId();
        } else {
            return null;
        }
    }

    /**
     * get row by execute sql, 只返回一条
     *
     * @param tableId
     *
     * @return
     */
    @Override
    public String getRowByExecuteSql(String tableId, String keyValue) {

        DbFetcher dbFetcher = dbFetcherMap.get(tableId);

        String tableKey = getTableKey(tableId);
        TableConfig tableConfig = tableConfigMap.get(tableId);

        if (dbFetcher != null && tableKey != null && tableConfig != null) {

            ISqlGenMgr sqlGenMgr = new SqlGenMgr();
            String sql = sqlGenMgr.genSql(tableId, tableKey, keyValue);

            // original data
            List<Map<String, Object>> dataJson = null;
            try {

                dataJson = dbFetcher.executeSql(sql);

                // to kv
                Map<String, String> map = this.table2KV(dataJson, tableConfig);

                if (map.keySet().size() == 0) {
                    return null;
                }
                return map.get(keyValue);

            } catch (SQLException e) {

                logger.error(e.toString(), e);
            }

        }

        return null;
    }

    /**
     * table data to kv data
     *
     * @param dataJson
     * @param tableConfig
     *
     * @return
     */
    private Map<String, String> table2KV(List<Map<String, Object>> dataJson, TableConfig tableConfig) {

        Map<String, String> tableKv = new HashMap<String, String>(100);
        for (Map<String, Object> rowMap : dataJson) {

            boolean foundKey = false;
            for (String column : rowMap.keySet()) {

                // key
                if (tableConfig.getKeyId().equalsIgnoreCase(column)) {
                    tableKv.put(rowMap.get(column).toString(), new Gson().toJson(rowMap));
                    foundKey = true;
                    break;
                }
            }

            if (!foundKey) {
                logger.warn("cannot find key:{} for table:{}'s column:{}", tableConfig.getKeyId(), tableConfig
                        .getIdentify(), rowMap.toString());
            }
        }

        return tableKv;
    }
}
