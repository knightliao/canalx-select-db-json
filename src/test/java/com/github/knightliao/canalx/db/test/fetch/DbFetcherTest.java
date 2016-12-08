package com.github.knightliao.canalx.db.test.fetch;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.github.knightliao.canalx.db.fetch.DbFetcher;
import com.github.knightliao.canalx.db.fetch.DbFetcherFactory;

/**
 * @author knightliao
 * @date 2016/11/28 11:18
 */
public class DbFetcherTest {

    @Ignore
    @Test
    public void test() {

        String driverClass = "com.mysql.jdbc.Driver";
        String dbUrl = "jdbc:mysql://localhost:3306?"
                + "useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&allowMultiQueries"
                + "=true";
        String userName = "root";
        String password = "123456";

        try {

            DbFetcher dbFetcher = DbFetcherFactory.getDefaultDbFetcher(driverClass, dbUrl, userName, password);

            List<Map<String, Object>> results = dbFetcher.executeSql("select * from 100weidu.user");
            for (Map<String, Object> map : results) {
                System.out.println(map.toString());
            }

        } catch (Exception e) {

            e.printStackTrace();

            Assert.assertTrue(false);
        }
    }
}
