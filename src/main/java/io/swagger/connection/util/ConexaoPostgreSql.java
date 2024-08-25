package io.swagger.connection.util;

import java.sql.Connection;
import java.sql.DriverManager;

import org.springframework.context.annotation.Configuration;

@Configuration
public class ConexaoPostgreSql {
	
	@SuppressWarnings("deprecation")
	public Connection abrirConexaoComDataBase() {
		try {
				Class.forName("org.postgresql.Driver").newInstance();

				String url = "jdbc:postgresql://127.0.0.1:5432/CRM_ROBERTO"
						+ "?user=postgres&password=Fime2404&&ssl=true;loginTimeout=40";
				return DriverManager.getConnection(url);
			

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
