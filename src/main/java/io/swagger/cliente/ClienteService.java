package io.swagger.cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import io.swagger.connection.util.ConexaoPostgreSql;

@Service
public class ClienteService {

	public Cliente save(Cliente cliente) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet generatedKeys = null;

		try {
			conn = new ConexaoPostgreSql().abrirConexaoComDataBase();

			if (conn == null) {
				return null;
			}

			String sql = "INSERT INTO cliente (inativo, datacadastro, razaosocial, nomefantasia, "
					+ " cpfcnpj, telefone, cep, endereco, numero, bairro, complemento, cidade ) "
					+ "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";

			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			stmt.setBoolean(1, cliente.isInativo());
			stmt.setTimestamp(2, Timestamp.from(Instant.now()));
			stmt.setString(3, cliente.getNomeFantasia());
			stmt.setString(4, cliente.getNomeFantasia());
			stmt.setString(5, cliente.getCpfCnpj());
			stmt.setString(6, cliente.getTelefone());
			stmt.setString(7, cliente.getCep());
			stmt.setString(8, cliente.getEndereco());
			stmt.setString(9, cliente.getNumero());
			stmt.setString(10, cliente.getBairro());
			stmt.setString(11, cliente.getComplemento());
			stmt.setString(12, cliente.getCidade());

			int affectedRows = stmt.executeUpdate();

			if (affectedRows > 0) {
				generatedKeys = stmt.getGeneratedKeys();
				if (generatedKeys.next()) {
					cliente.setId(generatedKeys.getLong(1));
				} else {
					throw new SQLException("Falha ao obter o ID gerado.");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return cliente;
	}

	public Cliente update(Cliente cliente) {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			conn = new ConexaoPostgreSql().abrirConexaoComDataBase();

			if (conn == null) {
				return null;
			}

			String sql = "UPDATE cliente SET " + "inativo = ?, " + "nomefantasia = ?, " + "cpfcnpj = ?, "
					+ "telefone = ?, " + "cep = ? , " + "endereco = ?, " + "numero = ?, " + "bairro = ?, "
					+ "complemento = ?, " + "cidade = ? " + "WHERE id = ?";

			stmt = conn.prepareStatement(sql);

			stmt.setBoolean(1, cliente.isInativo());
			stmt.setString(2, cliente.getNomeFantasia());
			stmt.setString(3, cliente.getCpfCnpj());
			stmt.setString(4, cliente.getTelefone());
			stmt.setString(5, cliente.getCep());
			stmt.setString(6, cliente.getEndereco());
			stmt.setString(7, cliente.getNumero());
			stmt.setString(8, cliente.getBairro());
			stmt.setString(9, cliente.getComplemento());
			stmt.setString(10, cliente.getCidade());
			stmt.setLong(11, cliente.getId());

			int rowsAffected = stmt.executeUpdate();

			if (rowsAffected == 0) {
				throw new SQLException("Falha ao atualizar o cliente. Nenhum registro foi modificado.");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return cliente;
	}

	public Cliente findById(Long id) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		Cliente cliente = null;

		try {
			conn = new ConexaoPostgreSql().abrirConexaoComDataBase();

			if (conn == null) {
				return null;
			}

			String sql = "SELECT c.id, c.inativo, c.datacadastro, c.nomefantasia, c.cpfcnpj, c.telefone, "
					+ " c.cep, c.endereco, c.numero, c.bairro, c.complemento, c.cidade as nomecidade "
					+ "FROM cliente c " +

					"WHERE c.id = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setLong(1, id);

			resultSet = stmt.executeQuery();

			if (resultSet.next()) {
				cliente = new Cliente();
				cliente.setId(resultSet.getLong("id"));
				cliente.setInativo(resultSet.getBoolean("inativo"));
				cliente.setDataCadastro(resultSet.getTimestamp("datacadastro"));
				cliente.setNomeFantasia(resultSet.getString("nomefantasia"));
				cliente.setCpfCnpj(resultSet.getString("cpfcnpj"));
				cliente.setTelefone(resultSet.getString("telefone"));
				cliente.setCep(resultSet.getString("cep"));
				cliente.setEndereco(resultSet.getString("endereco"));
				cliente.setNumero(resultSet.getString("numero"));
				cliente.setBairro(resultSet.getString("bairro"));
				cliente.setComplemento(resultSet.getString("complemento"));
				cliente.setCidade(resultSet.getString("nomecidade"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return cliente;
	}

	public void delete(Long id) {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			conn = new ConexaoPostgreSql().abrirConexaoComDataBase();

			if (conn == null) {
				return;
			}

			String sql = "DELETE FROM cliente WHERE id = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setLong(1, id);

			stmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public List<Cliente> pesquisa(String pesquisa) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		List<Cliente> clientes = new ArrayList<>();

		try {
			conn = new ConexaoPostgreSql().abrirConexaoComDataBase();

			if (conn == null) {
				return clientes;
			}

			String pesquisaConcatenada = "";
			if (pesquisa != null && !pesquisa.isEmpty()) {
				pesquisa = pesquisa.replaceAll("[.\\-/]", ""); // Remover pontos, traços e barras

				String[] splitPesquisa = pesquisa.split("\\s+");
				StringBuilder pesqNomeFantasia = new StringBuilder();
				StringBuilder pesqRazaoSocial = new StringBuilder();

				if (splitPesquisa.length > 0 && !pesquisa.isEmpty()) {
					pesqNomeFantasia.append(" AND ('").append(splitPesquisa[0])
							.append("' != '' AND UNACCENT(C.NOMEFANTASIA) ILIKE UNACCENT(CONCAT('%','")
							.append(splitPesquisa[0]).append("','%')))");
					pesqRazaoSocial.append(" OR ('").append(splitPesquisa[0])
							.append("' != '' AND UNACCENT(C.RAZAOSOCIAL) ILIKE UNACCENT(CONCAT('%','")
							.append(splitPesquisa[0]).append("','%')))");

					for (int cnt = 1; cnt < splitPesquisa.length; cnt++) {
						pesqNomeFantasia.append(" AND ('").append(splitPesquisa[cnt])
								.append("' != '' AND UNACCENT(C.NOMEFANTASIA) ILIKE UNACCENT(CONCAT('%','")
								.append(splitPesquisa[cnt]).append("','%')))");
						pesqRazaoSocial.append(" AND ('").append(splitPesquisa[cnt])
								.append("' != '' AND UNACCENT(C.RAZAOSOCIAL) ILIKE UNACCENT(CONCAT('%','")
								.append(splitPesquisa[cnt]).append("','%')))");
					}
				}

				pesquisaConcatenada = pesqNomeFantasia.toString() + " " + pesqRazaoSocial.toString();
			}

			String sql = "SELECT c.id, c.inativo, c.datacadastro, c.nomefantasia, c.cpfcnpj, c.telefone, "
					+ " c.cep, c.endereco, c.numero, c.bairro, c.complemento, c.cidade as nomecidade "
					+ "FROM cliente c " + "WHERE id > 0 " + pesquisaConcatenada + "ORDER BY c.id DESC LIMIT 100";
			stmt = conn.prepareStatement(sql);
			resultSet = stmt.executeQuery();

			while (resultSet.next()) {
				Cliente cliente = new Cliente();
				cliente.setId(resultSet.getLong("id"));
				cliente.setInativo(resultSet.getBoolean("inativo"));
				cliente.setDataCadastro(resultSet.getDate("datacadastro"));
				cliente.setNomeFantasia(resultSet.getString("nomefantasia"));
				cliente.setCpfCnpj(resultSet.getString("cpfcnpj"));
				cliente.setTelefone(resultSet.getString("telefone"));
				cliente.setCep(resultSet.getString("cep"));
				cliente.setEndereco(resultSet.getString("endereco"));
				cliente.setNumero(resultSet.getString("numero"));
				cliente.setBairro(resultSet.getString("bairro"));
				cliente.setComplemento(resultSet.getString("complemento"));
				cliente.setCidade(resultSet.getString("nomecidade"));

				clientes.add(cliente);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return clientes;
	}

	public Boolean deleteCliente(Long id) {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = new ConexaoPostgreSql().abrirConexaoComDataBase();
			if (conn == null) {
				return false;
			}

			stmt = conn.prepareStatement("delete from cliente where id = ?");
			stmt.setLong(1, id);

			stmt.execute();

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

}
