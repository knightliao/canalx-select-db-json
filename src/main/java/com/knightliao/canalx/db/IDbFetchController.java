package com.knightliao.canalx.db;

import java.util.Map;

import com.knightliao.canalx.db.exception.CanalxSelectDbJsonInitException;

/**
 * @author knightliao
 * @date 2016/11/29 15:12
 */
public interface IDbFetchController {

    // first key: table id
    // second key: the key of table
    Map<String, Map<String, String>> getInitDbKv(String configFilePath) throws CanalxSelectDbJsonInitException;
}
