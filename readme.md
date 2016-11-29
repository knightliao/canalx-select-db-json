
## canalx-select-db

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
    
## 推荐

- 有态度无广告的搜索引擎: https://www.sov5.com
- 高质量的微信公众号阅读: http://www.100weidu.com
- Python中国社区: http://www.python88.com
