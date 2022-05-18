package dao;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.cj.xdevapi.PreparableStatement;

import conexoes.ConexaoMysql;
import dto.Dados;

/*
 * Classe Dao, com obejtivo de interagir com o Banco de dados e a API do inpe
 */

public class Dao {

	

	private ConexaoMysql conn = new ConexaoMysql();

	private String query = null;

	private PreparedStatement pst = null;

	private ResultSet rs = null;

	
	

	/**
	 * Insere dados na tabela do banco de dados
	 * @param dado
	 */
	public void inserirDados(Dados dado) {

		this.query = "replace into aps3Sem.dados_queimadas(estado, municipio, data_hora, risco_fogo, dias_sem_chuva)"
				+ "values(?,?,?,?,?)";

		try {
			this.pst = conn.getConexao().prepareStatement(this.query);

			pst.setString(1, dado.getEstado());
			pst.setString(2, dado.getMunicipio());
			pst.setObject(3, dado.getData_hora());
			pst.setObject(4, dado.getRisco_fogo());
			pst.setObject(5, dado.getDias_sem_chuva());

			pst.execute();
			pst.close();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Os próximos 4 métodos fazem um select no banco de dados MYSQL, para aliementar os gráficos do programa
	 * @return ArrayList<Dados> listaDados
	 */

	public ArrayList<Dados> selectPrincipal() {
		
		this.query = "SELECT estado,municipio, risco_fogo  FROM aps3sem.dados_queimadas WHERE risco_fogo <> 0 group by estado,municipio, risco_fogo  order by risco_fogo desc; ";
		ArrayList<Dados> listaDados = new ArrayList<Dados>();

		try {
			pst = conn.getConexao().prepareStatement(this.query);

			rs = pst.executeQuery();

			while (rs.next()) {
				Dados dado = new Dados();
				dado.setEstado(rs.getString("estado"));
				dado.setMunicipio(rs.getString("municipio"));
				dado.setRisco_fogo(rs.getDouble("risco_fogo"));
			

				listaDados.add(dado);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listaDados;

	}

	public ArrayList<Dados> selectEstado() {
		this.query = "SELECT estado, risco_fogo  FROM aps3sem.dados_queimadas WHERE risco_fogo <> 0 group by estado, risco_fogo  order by risco_fogo desc; ";
		ArrayList<Dados> listaDados = new ArrayList<Dados>();

		try {
			pst = conn.getConexao().prepareStatement(this.query);

			rs = pst.executeQuery();

			while (rs.next()) {
				Dados dado = new Dados();
				dado.setEstado(rs.getString(1));
				dado.setRisco_fogo(rs.getDouble(2));

				listaDados.add(dado);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listaDados;

	}
	
	public ArrayList<Dados> selectHoje() {
		this.query = "SELECT  estado, municipio  , risco_fogo  FROM aps3sem.dados_queimadas WHERE day(data_hora) = day(now()) and risco_fogo <> 0 group by estado, municipio order by risco_fogo desc;";
		ArrayList<Dados> listaDados = new ArrayList<Dados>();

		try {
			pst = conn.getConexao().prepareStatement(this.query);

			rs = pst.executeQuery();

			while (rs.next()) {
				Dados dado = new Dados();
				dado.setEstado(rs.getString("estado"));
				dado.setMunicipio(rs.getString("municipio"));
				dado.setRisco_fogo(rs.getDouble("risco_fogo"));
			

				listaDados.add(dado);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listaDados;

	}
	
	
	public ArrayList<Dados> selectSemChuva() {
		this.query = "SELECT  estado, municipio  , dias_sem_chuva  FROM aps3sem.dados_queimadas WHERE dias_sem_chuva <> 0 group by estado, municipio order by dias_sem_chuva desc;";
		ArrayList<Dados> listaDados = new ArrayList<Dados>();

		try {
			pst = conn.getConexao().prepareStatement(this.query);

			rs = pst.executeQuery();

			while (rs.next()) {
				Dados dado = new Dados();
				dado.setEstado(rs.getString("estado"));
				dado.setMunicipio(rs.getString("municipio"));
				dado.setDias_sem_chuva(rs.getInt("dias_sem_chuva"));
			

				listaDados.add(dado);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listaDados;

	}
	
	
}
