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
					+ " descricao, situacaooportunidade, idcliente, valor) "
					+ " VALUES (?, ?, ?, ?, ?, ? ) RETURNING id";

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
			stmt.setInt(5, oportunidade.getIdCliente() != null ? oportunidade.getIdCliente() : null);
			stmt.setDouble(6, oportunidade.getValor() != null ? oportunidade.getValor() : null);
	

			rs = stmt.executeQuery();

			if (rs.next()) {
				oportunidade.setId(rs.getInt("id"));
			}
			if(oportunidade.getAnotacoes() != null && !oportunidade.getAnotacoes().isEmpty()) {
				String sqlAnt = "Insert into anotacao (dataCadastro, descricao, idOportunidade)"
						+ " values(?,?,?)";
				
				stmtAnt = conn.prepareStatement(sqlAnt);
				for(Anotacao ant : oportunidade.getAnotacoes()) {
					stmtAnt.setTimestamp(1, timestamp);
					stmtAnt.setString(2, ant.getDescricao());
					stmtAnt.setInt(3, oportunidade.getId());
					stmtAnt.execute();
				}
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
					+ " descricao=?, situacaooportunidade=?, idcliente=?, valor=? " 
					+ " WHERE id=? ";

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
			stmt.setInt(4, oportunidade.getIdCliente() != null ? oportunidade.getIdCliente() : null);
			stmt.setDouble(5, oportunidade.getValor() != null ? oportunidade.getValor() : null);
			stmt.setInt(6, oportunidade.getId() != null ? oportunidade.getId() : null);
			
			stmt.executeUpdate();
			if(oportunidade.getAnotacoes() != null && !oportunidade.getAnotacoes().isEmpty()) {
				for(Anotacao ant : oportunidade.getAnotacoes()) {
					if(ant.getId() != null) {
						String sqlAnt = "update anotacao set descricao = ? where id = ?";
						stmtAnt = conn.prepareStatement(sqlAnt);
						stmtAnt.setString(1, ant.getDescricao());
						stmtAnt.setInt(2, ant.getId());
						stmtAnt.executeUpdate();
					} else {
						String sqlAnt = "insert into anotacao (datacadastro, descricao, idoportunidade) "
								+ " values(?, ?, ?)";
						stmtAnt = conn.prepareStatement(sqlAnt);
						stmtAnt.setTimestamp(1, timestamp);
						stmtAnt.setString(2, (ant.getDescricao() != null ? ant.getDescricao() : null));
						stmtAnt.setInt(3, oportunidade.getId());
						stmtAnt.execute();
					}
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
//		PreparedStatement stmtAnotacao = null;
		ResultSet rsOportunidade = null;
	
		List<Oportunidade> oportunidades = new ArrayList<>();

		try {
			conn = new ConexaoPostgreSql().abrirConexaoComDataBase();

			if (conn == null) {
				return oportunidades;
			}

			String pesquisaConcatenada = "";
			if (pesquisa != null && !pesquisa.isEmpty()) {
				pesquisa = pesquisa.replaceAll("[.\\-/]", ""); // Remover pontos, traços e barras

				String[] splitPesquisa = pesquisa.split("\\s+");
				StringBuilder pesqNomeFantasia = new StringBuilder();
				StringBuilder pesqRazaoSocial = new StringBuilder();
				if (splitPesquisa.length > 0 && !pesquisa.isEmpty()) {
					pesqNomeFantasia.append(" AND ('").append(splitPesquisa[0])
							.append("' != '' AND UNACCENT(cliente.NOMEFANTASIA) ILIKE UNACCENT(CONCAT('%','")
							.append(splitPesquisa[0]).append("','%')))");
					pesqRazaoSocial.append(" OR ('").append(splitPesquisa[0])
							.append("' != '' AND UNACCENT(cliente.RAZAOSOCIAL) ILIKE UNACCENT(CONCAT('%','")
							.append(splitPesquisa[0]).append("','%')))");
					pesqRazaoSocial.append(" OR ('").append(splitPesquisa[0])
					.append("' != '' AND UNACCENT(oportunidade.descricao) ILIKE UNACCENT(CONCAT('%','")
					.append(splitPesquisa[0]).append("','%')))");

					for (int cnt = 1; cnt < splitPesquisa.length; cnt++) {
						pesqNomeFantasia.append(" AND ('").append(splitPesquisa[cnt])
								.append("' != '' AND UNACCENT(cliente.NOMEFANTASIA) ILIKE UNACCENT(CONCAT('%','")
								.append(splitPesquisa[cnt]).append("','%')))");
						pesqRazaoSocial.append(" AND ('").append(splitPesquisa[cnt])
								.append("' != '' AND UNACCENT(cliente.RAZAOSOCIAL) ILIKE UNACCENT(CONCAT('%','")
								.append(splitPesquisa[cnt]).append("','%')))");
						pesqRazaoSocial.append(" AND ('").append(splitPesquisa[cnt])
						.append("' != '' AND UNACCENT(oportunidade.descricao) ILIKE UNACCENT(CONCAT('%','")
						.append(splitPesquisa[cnt]).append("','%')))");
					}
				}
			}

			String sqlOportunidade = "SELECT oportunidade.ID, oportunidade.DATACADASTRO, oportunidade.DATACONQUISTAPERDA, "
					+ " oportunidade.DESCRICAO, oportunidade.SITUACAOOPORTUNIDADE, oportunidade.IDCLIENTE,  "
					+ " oportunidade.valor, cliente.nomefantasia "
					+ " FROM OPORTUNIDADE "
					+ " inner join cliente on cliente.id = oportunidade.idcliente "
					+ " WHERE cliente.inativo is false " + pesquisaConcatenada + " order by id desc LIMIT 100";

			stmtOportunidade = conn.prepareStatement(sqlOportunidade);
			rsOportunidade = stmtOportunidade.executeQuery();

			while (rsOportunidade.next()) {
				
			    Oportunidade oportunidade = new Oportunidade();
			    oportunidade.setId(rsOportunidade.getInt("id"));
			    oportunidade.setDataCadastro(rsOportunidade.getTimestamp("datacadastro") != null ? rsOportunidade.getTimestamp("datacadastro").toLocalDateTime() : null);
			    oportunidade.setDataConquistaPerda(rsOportunidade.getTimestamp("dataconquistaperda") != null ? rsOportunidade.getTimestamp("dataconquistaperda").toLocalDateTime() : null);
			    oportunidade.setDescricao(rsOportunidade.getString("descricao"));
			    oportunidade.setSituacaoOportunidade(rsOportunidade.getInt("situacaooportunidade"));
			    oportunidade.setIdCliente(rsOportunidade.getInt("idcliente"));
			    oportunidade.setValor(rsOportunidade.getDouble("valor"));
			    oportunidade.setNomeCliente(rsOportunidade.getString("nomefantasia"));
			    oportunidade.setAnotacoes(new ArrayList<>());

				oportunidades.add(oportunidade);
			}
			
			for(Oportunidade opt : oportunidades) {
				PreparedStatement stmtAnotacao = null;
				 String sqlAnotacao = "SELECT * FROM anotacao WHERE idoportunidade = ? ";
			    try {
			    	
			    	stmtAnotacao = conn.prepareStatement(sqlAnotacao);
			        stmtAnotacao.setInt(1, opt.getId());
			        ResultSet rsAnotacao = stmtAnotacao.executeQuery();
				    while (rsAnotacao.next()) {
				        Anotacao anotacao = new Anotacao();
				        anotacao.setId(rsAnotacao.getInt("id"));
				        anotacao.setDataCadastro(rsAnotacao.getTimestamp("datacadastro") != null ? rsAnotacao.getTimestamp("datacadastro").toLocalDateTime() : null);
				        anotacao.setDescricao(rsAnotacao.getString("descricao"));
				        anotacao.setIdOportunidade(rsAnotacao.getInt("idoportunidade"));
	
				        opt.getAnotacoes().add(anotacao);
				    }
			        
			    } catch(Exception e) {
			    	e.printStackTrace();
			    
			    }
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeResources(rsOportunidade, stmtOportunidade, conn);
		}

		return oportunidades;
	}

	public Oportunidade findById(Integer id) {
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
					+ "	oportunidade.IDCLIENTE, oportunidade.valor, cliente.nomefantasia \r\n"
					+ " FROM OPORTUNIDADE "
					+ " inner join cliente on cliente.id = oportunidade.idcliente "
					+ " where oportunidade.id = ?";

			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			
			String sqlAnotacao = "SELECT * FROM anotacao WHERE idoportunidade = ?";
			stmtAnotacao = conn.prepareStatement(sqlAnotacao);

			if (rs.next()) {
			    oportunidade = new Oportunidade();
			    oportunidade.setId(rs.getInt("id"));
			    oportunidade.setDataCadastro(rs.getTimestamp("datacadastro") != null ? rs.getTimestamp("datacadastro").toLocalDateTime() : null);
			    oportunidade.setDataConquistaPerda(rs.getTimestamp("dataconquistaperda") != null ? rs.getTimestamp("dataconquistaperda").toLocalDateTime() : null);
			    oportunidade.setDescricao(rs.getString("descricao"));
			    oportunidade.setSituacaoOportunidade(rs.getInt("situacaooportunidade"));
			    oportunidade.setIdCliente(rs.getInt("idcliente"));
			    oportunidade.setValor(rs.getDouble("valor"));
			    oportunidade.setNomeCliente(rs.getString("nomefantasia"));
			    
			    // Consulta das anotações para a oportunidade
			    stmtAnotacao.setInt(1, oportunidade.getId());
			    rsAnotacao = stmtAnotacao.executeQuery();
			    oportunidade.setAnotacoes(new ArrayList<>());

			    while (rsAnotacao.next()) {
			        Anotacao anotacao = new Anotacao();
			        anotacao.setId(rsAnotacao.getInt("id"));
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
	
	public Boolean deleteOportunidade(Integer id) {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = new ConexaoPostgreSql().abrirConexaoComDataBase();
			if (conn == null) {
				return false;
			}
			
			stmt = conn.prepareStatement("delete from oportunidade where id = ? ");
			stmt.setInt(1, id);
			
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