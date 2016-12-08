package com.github.knightliao.canalx.db.fetch;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author knightliao
 * @date 2016/11/28 11:18
 */
public interface DbFetcher {

    List<Map<String, Object>> executeSql(String sql) throws SQLException;
}
