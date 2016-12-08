package com.github.knightliao.canalx.db;

import java.util.Map;

import com.github.knightliao.canalx.db.exception.CanalxSelectDbJsonInitException;

/**
 * @author knightliao
 * @date 2016/11/29 15:12
 */
public interface IDbFetchController {

    // first key: table id
    // second key: the key of table
    Map<String, Map<String, String>> getInitDbKv() throws CanalxSelectDbJsonInitException;

    // init
    void init(String configFilePath) throws CanalxSelectDbJsonInitException;

    //
    String getTableKey(String tableId);
}
