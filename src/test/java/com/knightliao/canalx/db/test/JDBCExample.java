package com.knightliao.canalx.db.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;

import com.google.gson.Gson;

/**
 * @author knightliao
 * @date 2016/11/28 10:56
 */

public class JDBCExample {

    public static String resultSetToJson(String query) {
        Connection connection = null;
        List<Map<String, Object>> listOfMaps = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306?"
                    + "useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&allowMultiQueries"
                    + "=true", "root", "123456");
        } catch (Exception ex) {
            System.err.println("***exception trying to connect***");
            ex.printStackTrace();
        }

        try {
            QueryRunner queryRunner = new QueryRunner();
            listOfMaps = queryRunner.query(connection, query, new MapListHandler());
        } catch (SQLException se) {
            throw new RuntimeException("Couldn't query the database.", se);
        } finally {
            DbUtils.closeQuietly(connection);
        }
        return new Gson().toJson(listOfMaps);
    }

    public static void main(String[] args) {

        String data = JDBCExample.resultSetToJson("select * from 100weidu.user");

        System.out.println(data);
    }
}