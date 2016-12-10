package com.github.knightliao.canalx.db.test;

import java.util.Map;

import javax.sql.DataSource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.github.knightliao.canalx.db.DbFetchControllerFactory;
import com.github.knightliao.canalx.db.IDbFetchController;
import com.github.knightliao.canalx.db.fetch.DbFetcher;
import com.github.knightliao.canalx.db.fetch.DbFetcherFactory;
import com.github.knightliao.canalx.db.fetch.impl.DbFetcherImpl;
import com.github.knightliao.test.h2.H2BaseTestCase;

import mockit.Mock;
import mockit.MockUp;
import mockit.integration.junit4.JMockit;

/**
 * @author knightliao
 * @date 2016/11/29 15:12
 */
@RunWith(JMockit.class)
public class DbFetchControllerTest extends H2BaseTestCase {

    IDbFetchController dbFetchController = DbFetchControllerFactory.getDefaultDbController();

    @Test
    public void test() {

        final DataSource dataSource = this.getDataSource();

        //
        // mock up factory method
        //
        new MockUp<DbFetcherFactory>() {

            // mock up database
            @Mock
            public DbFetcher getDefaultDbFetcher(String driverClass, String dbUrl, String userName,
                                                 String userPassword)
                    throws ClassNotFoundException {

                DbFetcher dbFetcher = new DbFetcherImpl();

                // set up h2 data base
                ((DbFetcherImpl) dbFetcher).setDataSource(dataSource);

                return dbFetcher;
            }
        };

        try {

            dbFetchController.init("canalx-db-kv.xml");
            Map<String, Map<String, String>> dbKvs = dbFetchController.getInitDbKv();

            for (String tableId : dbKvs.keySet()) {

                System.out.println("table identify: " + tableId);
                System.out.println("table kv:" + dbKvs.get(tableId));
            }

        } catch (Exception e) {

            Assert.assertTrue(false);
        }
    }
}
