package io.swagger.oportunidade;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.postgresql.util.PSQLException;
import org.springframework.stereotype.Service;

import io.swagger.connection.util.ConexaoPostgreSql;

@Service
public class OportunidadeService {

	public Oportunidade save(Oportunidade oportunidade) {
		Connection conn = null;
		PreparedStatement stmt = null;
		PreparedStatement stmtAnt = null;
		ResultSet rs = null;
		Timestamp timestamp = Timestamp.from(Instant.now());
		try {
			conn = new ConexaoPostgreSql().abrirConexaoComDataBase();

			if (conn == null) {
				return null;
			}

			String sql = "INSERT INTO oportunidade (datacadastro, dataconquistaperda, "
					+ " descricao, situacaooportunidade, idcliente, valor)"
					+ "VALUES (?, ?, ?, ?, ?, ? ) RETURNING id";

			stmt = conn.prepareStatement(sql);
			
			stmt.setTimestamp(1, timestamp);
			if(oportunidade.getSituacaoOportunidade() != null
					&& (oportunidade.getSituacaoOportunidade() == 2
							|| oportunidade.getSituacaoOportunidade() == 3)) {
				stmt.setTimestamp(2, timestamp);
			} else {
				stmt.setTimestamp(2, null);
			}
			
			stmt.setString(3, oportunidade.getDescricao() != null ? oportunidade.getDescricao() : null);
			stmt.setInt(4, oportunidade.getSituacaoOportunidade() != null ? oportunidade.getSituacaoOportunidade() : null);
			stmt.setLong(5, oportunidade.getIdCliente() != null ? oportunidade.getIdCliente() : null);
			stmt.setDouble(6, oportunidade.getValor() != null ? oportunidade.getValor() : null);
	

			rs = stmt.executeQuery();

			if (rs.next()) {
				oportunidade.setId(rs.getLong("id"));
			}
			
			String sqlAnt = "Insert into anotacao (dataCadastro, descricao, idOportunidade)"
					+ " values(?,?,?)";
			
			stmtAnt = conn.prepareStatement(sqlAnt);
			for(Anotacao ant : oportunidade.getAnotacoes()) {
				stmtAnt.setTimestamp(1, timestamp);
				stmtAnt.setString(2, ant.getDescricao());
				stmtAnt.setLong(3, oportunidade.getId());
				stmtAnt.execute();
			}
			


		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
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

		return oportunidade;
	}

	public Oportunidade update(Oportunidade oportunidade) {
		Connection conn = null;
		PreparedStatement stmt = null;
		PreparedStatement stmtAnt = null;
		Timestamp timestamp = Timestamp.from(Instant.now());

		try {
			conn = new ConexaoPostgreSql().abrirConexaoComDataBase();

			if (conn == null) {
				return null;
			}

			String sql = "UPDATE oportunidade SET dataconquistaperda=?, "
					+ "descricao=?, situacaooportunidade=?, idcliente=?, valor=?" 
					+ "WHERE id=?";

			stmt = conn.prepareStatement(sql);
			
			if(oportunidade.getSituacaoOportunidade() != null
					&& (oportunidade.getSituacaoOportunidade() == 2
							|| oportunidade.getSituacaoOportunidade() == 3)) {
				stmt.setTimestamp(1, timestamp);
			} else {
				stmt.setTimestamp(1, null);
			}

			stmt.setString(2, oportunidade.getDescricao() != null ? oportunidade.getDescricao() : null);
			stmt.setInt(3, oportunidade.getSituacaoOportunidade() != null ? oportunidade.getSituacaoOportunidade() : null);
			stmt.setLong(4, oportunidade.getIdCliente() != null ? oportunidade.getIdCliente() : null);
			stmt.setDouble(5, oportunidade.getValor() != null ? oportunidade.getValor() : null);
			stmt.setLong(6, oportunidade.getId() != null ? oportunidade.getId() : null);
			
			stmt.executeUpdate();
			
			for(Anotacao ant : oportunidade.getAnotacoes()) {
				if(ant.getId() > 0) {
					String sqlAnt = "update anotacao set descricao = ? where id = ?";
					stmtAnt = conn.prepareStatement(sqlAnt);
					stmtAnt.setString(1, ant.getDescricao());
					stmtAnt.setLong(2, ant.getId());
					stmtAnt.executeUpdate();
				} else {
					String sqlAnt = "insert into anotacao (datacadastro, descricao, idoportunidade) "
							+ " values(?, ?, ?)";
					stmtAnt = conn.prepareStatement(sqlAnt);
					stmtAnt.setTimestamp(1, timestamp);
					stmtAnt.setString(2, (ant.getDescricao() != null ? ant.getDescricao() : null));
					stmtAnt.setLong(3, oportunidade.getId());
					stmtAnt.execute();
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

		return oportunidade;
	}

	public List<Oportunidade> pesquisa(String pesquisa) {
		Connection conn = null;
		PreparedStatement stmtOportunidade = null;
		PreparedStatement stmtAnotacao = null;
		ResultSet rsOportunidade = null;
		ResultSet rsAnotacao = null;
		List<Oportunidade> oportunidades = new ArrayList<>();

		try {
			conn = new ConexaoPostgreSql().abrirConexaoComDataBase();

			if (conn == null) {
				return oportunidades;
			}

			String pesquisaConcatenada = "";
			if (pesquisa != null && !pesquisa.isEmpty()) {
				pesquisaConcatenada = ""; // Implemente aqui sua lógica de pesquisa
			}

			String sqlOportunidade = "SELECT oportunidade.ID, oportunidade.DATACADASTRO, oportunidade.DATACONQUISTAPERDA, "
					+ " oportunidade.DESCRICAO, oportunidade.SITUACAOOPORTUNIDADE, oportunidade.IDCLIENTE,  "
					+ " oportunidade.valor "
					+ " FROM OPORTUNIDADE "
					+ " inner join cliente on cliente.id = oportunidade.idcliente "
					+ " WHERE cliente.inativo is false " + pesquisaConcatenada + " order by id desc LIMIT 100";

			stmtOportunidade = conn.prepareStatement(sqlOportunidade);
			rsOportunidade = stmtOportunidade.executeQuery();

			String sqlAnotacao = "SELECT * FROM anotacao WHERE idoportunidade = ?";

			stmtAnotacao = conn.prepareStatement(sqlAnotacao);

			while (rsOportunidade.next()) {
			    Oportunidade oportunidade = new Oportunidade();
			    oportunidade.setId(rsOportunidade.getLong("id"));
			    oportunidade.setDataCadastro(rsOportunidade.getTimestamp("datacadastro") != null ? rsOportunidade.getTimestamp("datacadastro").toLocalDateTime() : null);
			    oportunidade.setDataConquistaPerda(rsOportunidade.getTimestamp("dataconquistaperda") != null ? rsOportunidade.getTimestamp("dataconquistaperda").toLocalDateTime() : null);
			    oportunidade.setDescricao(rsOportunidade.getString("descricao"));
			    oportunidade.setSituacaoOportunidade(rsOportunidade.getInt("situacaooportunidade"));
			    oportunidade.setIdCliente(rsOportunidade.getLong("idcliente"));
			    oportunidade.setValor(rsOportunidade.getDouble("valor"));
			    oportunidade.setAnotacoes(new ArrayList<>());

			    // Consulta das anotações para a oportunidade
			    stmtAnotacao.setLong(1, oportunidade.getId());
			    rsAnotacao = stmtAnotacao.executeQuery();

			    while (rsAnotacao.next()) {
			        Anotacao anotacao = new Anotacao();
			        anotacao.setId(rsAnotacao.getLong("id"));
			        anotacao.setDataCadastro(rsAnotacao.getTimestamp("datacadastro") != null ? rsAnotacao.getTimestamp("datacadastro").toLocalDateTime() : null);
			        anotacao.setDescricao(rsAnotacao.getString("descricao"));
			        anotacao.setIdOportunidade(rsAnotacao.getInt("idoportunidade"));

			        oportunidade.getAnotacoes().add(anotacao);
			    }
			

				oportunidades.add(oportunidade);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeResources(rsAnotacao, stmtAnotacao, null);
			closeResources(rsOportunidade, stmtOportunidade, conn);
		}

		return oportunidades;
	}

	public Oportunidade findById(Long id) {
		Connection conn = null;
		PreparedStatement stmt = null;
		PreparedStatement stmtAnotacao = null;
		ResultSet rs = null;
		Oportunidade oportunidade = null;
		ResultSet rsAnotacao = null;

		try {
			conn = new ConexaoPostgreSql().abrirConexaoComDataBase();

			if (conn == null) {
				return null;
			}

			String sql = "SELECT oportunidade.ID, oportunidade.DATACADASTRO, oportunidade,DATACONQUISTAPERDA, \r\n"
					+ "	oportunidade.SITUACAOOPORTUNIDADE, oportunidade.descricao, \r\n"
					+ "	oportunidade.IDCLIENTE, oportunidade.valor \r\n"
					+ " FROM OPORTUNIDADE "
					+ " where oportunidade.id = ?";

			stmt = conn.prepareStatement(sql);
			stmt.setLong(1, id);
			rs = stmt.executeQuery();
			
			String sqlAnotacao = "SELECT * FROM anotacao WHERE idoportunidade = ?";
			stmtAnotacao = conn.prepareStatement(sqlAnotacao);

			if (rs.next()) {
			    oportunidade = new Oportunidade();
			    oportunidade.setId(rs.getLong("id"));
			    oportunidade.setDataCadastro(rs.getTimestamp("datacadastro") != null ? rs.getTimestamp("datacadastro").toLocalDateTime() : null);
			    oportunidade.setDataConquistaPerda(rs.getTimestamp("dataconquistaperda") != null ? rs.getTimestamp("dataconquistaperda").toLocalDateTime() : null);
			    oportunidade.setDescricao(rs.getString("descricao"));
			    oportunidade.setSituacaoOportunidade(rs.getInt("situacaooportunidade"));
			    oportunidade.setIdCliente(rs.getLong("idcliente"));
			    oportunidade.setValor(rs.getDouble("valor"));
			    
			    // Consulta das anotações para a oportunidade
			    stmtAnotacao.setLong(1, oportunidade.getId());
			    rsAnotacao = stmtAnotacao.executeQuery();
			    oportunidade.setAnotacoes(new ArrayList<>());

			    while (rsAnotacao.next()) {
			        Anotacao anotacao = new Anotacao();
			        anotacao.setId(rsAnotacao.getLong("id"));
			        anotacao.setDataCadastro(rsAnotacao.getTimestamp("datacadastro") != null ? rsAnotacao.getTimestamp("datacadastro").toLocalDateTime() : null);
			        anotacao.setDescricao(rsAnotacao.getString("descricao"));
			        anotacao.setIdOportunidade(rsAnotacao.getInt("idoportunidade"));
			        oportunidade.getAnotacoes().add(anotacao);
			    }
			}


		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeResources(rsAnotacao, stmtAnotacao, null);
			closeResources(rs, stmt, conn);
		}
		return oportunidade;

	}
	
	public Boolean deleteOportunidade(Long id) {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = new ConexaoPostgreSql().abrirConexaoComDataBase();
			if (conn == null) {
				return false;
			}
			
			stmt = conn.prepareStatement("delete from oportunidade where id = ?");
			stmt.setLong(1, id);
			
			stmt.execute();
			
			return true;
		}catch(PSQLException p) {
			return false;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}

	private void closeResources(ResultSet rs, PreparedStatement stmt, Connection conn) {
		try {
			if (rs != null) {
				rs.close();
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
}