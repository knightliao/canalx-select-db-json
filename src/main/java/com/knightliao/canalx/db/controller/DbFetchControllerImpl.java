package com.knightliao.canalx.db.controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.knightliao.canalx.db.IDbFetchController;
import com.knightliao.canalx.db.config.DbConfiguration;
import com.knightliao.canalx.db.config.TableConfig;
import com.knightliao.canalx.db.exception.CanalxSelectDbJsonInitException;
import com.knightliao.canalx.db.fetch.DbFetcher;
import com.knightliao.canalx.db.fetch.DbFetcherFactory;

/**
 * @author knightliao
 * @date 2016/11/29 15:48
 */
public class DbFetchControllerImpl implements IDbFetchController {

    private static final Logger logger = LoggerFactory.getLogger(DbFetchControllerImpl.class);

    /**
     * @return
     *
     * @throws CanalxSelectDbJsonInitException
     */
    public Map<String, Map<String, String>> getInitDbKv(String configFilePath) throws CanalxSelectDbJsonInitException {

        URL url = null;
        if (configFilePath == null) {
            url = DbFetchControllerImpl.class.getClassLoader().getResource("canalx-db-kv.xml");
        } else {
            url = DbFetchControllerImpl.class.getClassLoader().getResource(configFilePath);
        }

        Map<String, TableConfig> tableConfigMap = null;
        try {
            tableConfigMap = DbConfiguration.parse(url);
        } catch (Exception e) {
            throw new CanalxSelectDbJsonInitException(e);
        }

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
                if (tableConfig.getKeyId().equals(column)) {
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
