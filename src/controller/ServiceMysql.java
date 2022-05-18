package controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import dao.Dao;
import dto.Dados;

public class ServiceMysql {
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private Dao dao = new Dao();
	
	/**
	 * Recebe o json vindo da ServiceUrl e trata os dados para serem salvos no banco.
	 * @throws JSONException
	 * @throws ParseException
	 */
	
	public void inserirDados() throws JSONException, ParseException {

		Dados dados = new Dados();
		

		ServiceUrl url = new ServiceUrl();
		String jsonUrl = ServiceUrl.getDataUrl();
		
//		System.out.println(jsonUrl);

		ArrayList<JSONObject> listJson = url.passDadosToJson(jsonUrl);

		for (JSONObject json : listJson) {
			
			// trata o campo de dat para tirarmos o fuso horário, já que essa informação não nos interessa
			String[] dataFormat = json.getString("data_hora_gmt").split("\\+");

			dados.setEstado(json.getString("estado"));
			dados.setMunicipio(json.getString("municipio"));
			dados.setData_hora(sdf.parse(dataFormat[0].replace("T", " ")));
			
			if (!json.isNull("risco_fogo")) {
				dados.setRisco_fogo(json.get("risco_fogo"));	
			}else {
				dados.setRisco_fogo(0);
			}
			
			
			if (!json.isNull("numero_dias_sem_chuva")) {	
				dados.setDias_sem_chuva(json.get("numero_dias_sem_chuva"));
			}else {
				dados.setDias_sem_chuva(0);
			}
			
			
			dao.inserirDados(dados);
		}

	}
	
	
	/**
	 * Os 4 métodos seguintes preparam os dados para mandar para o gráfico 
	 * @return ArrayList<Dados>
	 */
	public ArrayList<Dados> getSelectPrincipal(){
		return dao.selectPrincipal(); 
	}
	
	public ArrayList<Dados> getSelectEstados(){
		return dao.selectEstado(); 
	}
	
	public ArrayList<Dados> getSelectHoje(){
		return dao.selectHoje();
	}
	
	public ArrayList<Dados> getSelectSemChuva(){
		return dao.selectSemChuva();
	}

}
