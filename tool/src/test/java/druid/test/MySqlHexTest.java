package druid.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.KeyedHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.util.JdbcUtils;

import junit.framework.TestCase;

public class MySqlHexTest extends TestCase {
    final int COUNT = 800;
    
    private String          jdbcUrl;
    private String          user;
    private String          password;
    private String          driverClass;

    private DruidDataSource dataSource;

    protected void setUp() throws Exception {
        jdbcUrl = "jdbc:mysql://127.0.0.1:3306/pgame_login";
        user = "root";
        password = "123456";
        driverClass = "com.mysql.jdbc.Driver";

        dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driverClass);
        dataSource.setUrl(jdbcUrl);
        dataSource.setPoolPreparedStatements(true);
        dataSource.setUsername(user);
        dataSource.setPassword(password);

    }


    public void test_0() throws Exception {

    	
        Connection conn = dataSource.getConnection();

        String sql = "SELECT * FROM server";

        PreparedStatement stmt = conn.prepareStatement(sql);

        ResultSet rs = stmt.executeQuery();
        JdbcUtils.printResultSet(rs);
        rs.close();
        stmt.close();

        conn.close();
        
        //add dbUtils
        
        QueryRunner runner = new QueryRunner(dataSource);
        List<Map<String,Object>> list = runner.query(sql, new MapListHandler());
        for(int i = 0, size = list.size(); i < size; i++){
        	Map<String, Object> map = list.get(i);
        	if(i == 0){
        		for(String title : map.keySet()){
        			System.out.print(title);
        			System.out.print("\t");
        		}
        		System.out.println();
        	}
        	
        	for(Object title : map.values()){
    			System.out.print(title);
    			System.out.print("\t");
    		}
    		System.out.println();
        }
        
        System.out.println("");
    }

}