package com.github.knightliao.canalx.db.test.controller.sql;

import org.junit.Assert;
import org.junit.Test;

import com.github.knightliao.canalx.db.controller.sql.ISqlGenMgr;
import com.github.knightliao.canalx.db.controller.sql.impl.SqlGenMgr;

/**
 * @author knightliao
 * @date 2016/12/13 16:49
 */
public class SqlGenMgrTestCase {

    @Test
    public void test() {

        ISqlGenMgr sqlGenMgr = new SqlGenMgr();

        String sql = sqlGenMgr.genSql("test.user", "id", String.valueOf(1L));

        System.out.println(sql);

        Assert.assertEquals(sql, "select * from test.user where id=1");

    }
}
