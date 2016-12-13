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
import com.github.knightliao.canalx.db.exception.CanalxSelectDbJsonInitException;
import com.github.knightliao.canalx.db.fetch.DbFetcher;
import com.github.knightliao.canalx.db.fetch.DbFetcherFactory;
import com.google.gson.Gson;

/**
 * @author knightliao
 * @date 2016/11/29 15:48
 */
public class DbFetchControllerImpl implements IDbFetchController {

    private static final Logger logger = LoggerFactory.getLogger(DbFetchControllerImpl.class);

    private Map<String, TableConfig> tableConfigMap = new HashMap<String, TableConfig>();

    /**
     * @return
     *
     * @throws CanalxSelectDbJsonInitException
     */
    public Map<String, Map<String, String>> getInitDbKv() throws CanalxSelectDbJsonInitException {

        Map<String, Map<String, String>> dbKv = new ConcurrentHashMap<String, Map<String, String>>(100);
        for (String tableId : tableConfigMap.keySet()) {

            TableConfig tableConfig = tableConfigMap.get(tableId);

            try {

                DbFetcher dbFetcher = DbFetcherFactory.getDefaultDbFetcher(tableConfig.getDriverClass(), tableConfig
                        .getDbUrl(), tableConfig.getUserName(), tableConfig.getPassword());

                if (!tableConfig.getInitSql().isEmpty()) {

                    // original data
                    List<Map<String, Object>> dataJson = dbFetcher.executeSql(tableConfig.getInitSql());

                    // to kv
                    Map<String, String> dataKv = this.table2KV(dataJson, tableConfig);

                    //
                    dbKv.put(tableConfig.getIdentify(), dataKv);

                    logger.info("load sql:{} ok.", tableConfig.getInitSql());
                }

            } catch (ClassNotFoundException e) {
                logger.error(e.toString());
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

        URL url = null;
        if (configFilePath == null) {
            url = DbFetchControllerImpl.class.getClassLoader().getResource("canalx-db-kv.xml");
        } else {
            url = DbFetchControllerImpl.class.getClassLoader().getResource(configFilePath);
        }

        if (url == null) {
            throw new CanalxSelectDbJsonInitException("cannot load config: " + configFilePath);
        }

        Map<String, TableConfig> tableConfigMap;
        try {
            tableConfigMap = DbConfiguration.parse(url);
        } catch (Exception e) {
            throw new CanalxSelectDbJsonInitException(e);
        }

        this.tableConfigMap = tableConfigMap;
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
