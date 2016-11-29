package com.knightliao.canalx.db.test;

import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;

import com.knightliao.canalx.db.DbFetchControllerFactory;
import com.knightliao.canalx.db.IDbFetchController;

/**
 * @author knightliao
 * @date 2016/11/29 15:12
 */
public class DbFetchControllerTest {

    @Ignore
    @Test
    public void test() {

        IDbFetchController dbFetchController = DbFetchControllerFactory.getDefaultDbController();

        Map<String, Map<String, String>> dbKvs = dbFetchController.getInitDbKv();

        for (String tableId : dbKvs.keySet()) {

            System.out.println("table identify: " + tableId);
            System.out.println("table kv:" + dbKvs.get(tableId));
        }
    }
}
