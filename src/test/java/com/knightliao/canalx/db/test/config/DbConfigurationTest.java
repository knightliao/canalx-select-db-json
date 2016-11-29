package com.knightliao.canalx.db.test.config;

import java.net.URL;

import org.junit.Assert;
import org.junit.Test;

import com.knightliao.canalx.db.config.DbConfiguration;

/**
 * @author knightliao
 * @date 2016/11/29 10:42
 */
public class DbConfigurationTest {

    @Test
    public void test() {

        URL url = DbConfigurationTest.class.getClassLoader().getResource("canalx-db-kv.xml");

        try {

            DbConfiguration.parse(url);

        } catch (Exception e) {
            e.printStackTrace();

            Assert.assertTrue(false);
        }
    }
}
