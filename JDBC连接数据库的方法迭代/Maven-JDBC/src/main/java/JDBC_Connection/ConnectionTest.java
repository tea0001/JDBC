package JDBC_Connection;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import org.junit.Test;

import com.mysql.cj.jdbc.Driver;

public class ConnectionTest {

	/**
	 *	 方式一
	 * @throws Exception
	 */
	@Test
	public void connectionTest1() throws Exception {
		Driver driver = (Driver) new com.mysql.cj.jdbc.Driver();
		
		String url = "jdbc:mysql://localhost:3306/test";
		//将用户和密码封装到Properties中
		Properties info = new Properties();
		info.setProperty("user", "root");
		info.setProperty("password", "123456");
		
		Connection connect = driver.connect(url, info);
		System.out.println(connect);
	}
	/**
	 * 	方式二：对方式一的迭代，在如下的程序中不会出现第三方的API，使得程序具有可移植性
	 * @throws Exception
	 */
	@Test
	public void connectionTest2() throws Exception {
		//1、获得Driver的实现类的对象，通过反射
		Class clazz = Class.forName("com.mysql.cj.jdbc.Driver");
		Driver driver = (Driver) clazz.newInstance();
		
		//2、通过要连接的数据库
		String url = "jdbc:mysql://localhost:3306/test";
		
		//3、提供连接需要的用户和密码
		Properties info = new Properties();
		info.setProperty("user", "root");
		info.setProperty("password", "123456");
		
		//4、获得连接
		Connection connect = driver.connect(url, info);
		System.out.println(connect);
		
	}
	/**
	 * 	方式三：使用  DriverManager 替换 Driver
	 * @throws Exception
	 */
	@Test
	public void connectionTest3() throws Exception {
		
		Class clazz = Class.forName("com.mysql.cj.jdbc.Driver");
		Driver driver = (Driver) clazz.newInstance();
		
		String url = "jdbc:mysql://localhost:3306/test";
		String user = "root";
		String password = "123456";
		//注册驱动
		DriverManager.registerDriver(driver);
		//获取连接
		Connection connection = DriverManager.getConnection(url, user, password);
		System.out.println(connection);
	}
	/**
	 * 	方式四：由于Driver在编译时就提供静态代码块实现了注册驱动
	 */
	@Test
	public void connectionTest4() throws Exception {
		
		Class.forName("com.mysql.cj.jdbc.Driver");
		
		String url = "jdbc:mysql://localhost:3306/test";
		String user = "root";
		String password = "123456";
		/**
		static {
		        try {
		            java.sql.DriverManager.registerDriver(new Driver());
		        } catch (SQLException E) {
		            throw new RuntimeException("Can't register driver!");
		        }
		    }
		 */
		Connection connection = DriverManager.getConnection(url, user, password);
		System.out.println(connection);
	}
	/**
	 * 	方式五：最终版。
	 * 	好处：达到了数据与程序的解耦。利于后期的修改
	 * @throws Exception
	 */
	@Test
	public void connectionTest5() throws Exception {
		InputStream is = ConnectionTest.class.getClassLoader().getResourceAsStream("db.properties");
		
		Properties properties = new Properties();
		properties.load(is);
		
		String url = properties.getProperty("url");
		String user = properties.getProperty("user");
		String password = properties.getProperty("password");
		String classDriver = properties.getProperty("classDriver");
		
		Class.forName(classDriver);
		Connection connection = DriverManager.getConnection(url, user, password);
		System.out.println(connection);
		
	}
	
}
