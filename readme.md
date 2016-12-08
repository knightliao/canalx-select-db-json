canalx-select-db-json [![Build Status](https://travis-ci.org/knightliao/canalx-select-db-json.svg?branch=master)](https://travis-ci.org/knightliao/canalx-select-db-json) [![Coverage Status](https://coveralls.io/repos/github/knightliao/canalx-select-db-json/badge.svg?branch=master)](https://coveralls.io/github/knightliao/canalx-select-db-json?branch=master)
=======

Fetch MYSQL's data to K-V style data.

## quick start 

### config 

    <?xml version="1.0" encoding="UTF-8"?>
    
    <configuration>
        <base>
            <driverClass>com.mysql.jdbc.Driver</driverClass>
        </base>
    
        <dbs dbUrl="jdbc:mysql://localhost:3306?useUnicode=true&amp;characterEncoding=UTF-8&amp;zeroDateTimeBehavior=convertToNull&amp;allowMultiQueries=true"
             name="test"
             userName="root" password="123456">
            <db>
                <table keyId="id" name="user" initSql="select * from test.user"/>
            </db>
    
            <db name="test2"
                dbUrl="jdbc:mysql://localhost:3306?useUnicode=true&amp;characterEncoding=UTF-8&amp;zeroDateTimeBehavior=convertToNull&amp;allowMultiQueries=true"
                userName="root" password="123456">
                <table keyId="id" name="store" initSql="select * from test2.store"/>
            </db>
        </dbs>
    
    </configuration>

### code: 

    @Test
    public void test() {

        IDbFetchController dbFetchController = DbFetchControllerFactory.getDefaultDbController();
        
        dbFetchController.init("canalx-db-kv.xml");
        Map<String, Map<String, String>> dbKvs = dbFetchController.getInitDbKv();

        for (String tableId : dbKvs.keySet()) {

            System.out.println("table identify: " + tableId);
            System.out.println("table kv:" + dbKvs.get(tableId));
        }
    }
    
### result 
    
    table identify: test2.store
    table kv:{1={"id":1,"name":"product1","products":100}}
    table identify: test.user
    table kv:{1={"id":1,"name":"user1","phone":"123456789"}}
    
### Maven

    <dependency>
        <groupId>com.knightliao.canalx</groupId>
        <artifactId>canalx-select-db-json</artifactId>
        <version>0.0.1-SNAPSHOT</version>
    </dependency>
    
## 推荐

- 有态度无广告的搜索引擎: https://www.sov5.com
- 微读 - 高品质阅读: http://www.100weidu.com
- Python中国社区: http://www.python88.com
- Disconf - 分布式配置管理平台: http://github.com/knightliao/disconf
- CanalX - 基于 `Canal` 的数据感知服务框架: http://github.com/knightliao/canalX

