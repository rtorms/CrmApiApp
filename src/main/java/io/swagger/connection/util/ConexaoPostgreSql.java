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

				String url = "jdbc:postgresql://aws-0-sa-east-1.pooler.supabase.com:6543/"
						+ "postgres?user=postgres.rasldcdvwzvesfascbqd&password=DEVOPS_UTFPR";
				return DriverManager.getConnection(url);
			

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
