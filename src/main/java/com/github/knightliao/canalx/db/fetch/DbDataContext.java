package com.github.knightliao.canalx.db.fetch;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author knightliao
 * @date 2016/11/28 11:21
 */
@Data
@AllArgsConstructor
public class DbDataContext {

    private String driverClass;
    private String dbUrl;
    private String userName;
    private String userPassword;
}
