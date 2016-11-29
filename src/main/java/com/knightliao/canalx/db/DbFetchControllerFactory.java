package com.knightliao.canalx.db;

import com.knightliao.canalx.db.controller.DbFetchControllerImpl;

/**
 * @author knightliao
 * @date 2016/11/29 15:12
 */
public class DbFetchControllerFactory {

    public static IDbFetchController getDefaultDbController() {

        IDbFetchController dbFetchController = new DbFetchControllerImpl();
        return dbFetchController;
    }
}
