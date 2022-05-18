package conexoes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoMysql {

	private Connection conn = null;

	private String driverName = null;

	private String user = null;

	private String password = null;

	private String servidor = null;
	
	

	public Connection getConexao() {

		try {

			driverName = "com.mysql.cj.jdbc.Driver";
			user = "root";
			servidor = "jdbc:mysql://127.0.0.1:3306/?user=root?useTimezone=true&serverTimezone=UTC";

			Class.forName(driverName);
			conn = DriverManager.getConnection(servidor, user, password);

			
		} catch (ClassNotFoundException e) {

			System.out.println("driver não encontrado não encontrrado");

		} catch (SQLException e) {

		}

		return conn;
	}
}
