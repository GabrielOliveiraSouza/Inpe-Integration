package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import conexoes.ConexaoUrl;
import dao.Dao;

/**
 * Classe para tratamento dos dados vindos da API
 */
public class ServiceUrl {

	/**
	 * Método que recebe os dados e transforma em um "arquivo" texto
	 * @return
	 * @throws JSONException
	 */
	public static String getDataUrl() throws JSONException {
		String dados = null;
		try {
			
			
			ConexaoUrl conn = new ConexaoUrl();
			HttpURLConnection con = conn.conectaUrl();
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String linha = null;
			int a = 0;
			while((linha = br.readLine()) != null && a < 30) {
				
				dados += linha;
				a++;
			}
			System.out.println(dados);
			
			br.close();

			
		} catch (IOException e) {
			
			
			e.printStackTrace();
			System.out.println("Erro na leitura dos dados");
			
			
		}

		return dados;
	}
	
	/**
	 *  Método para transformar um arquivo de texto do método getDataUrl em JSON usando a lib org.json-chargebee-1.0.jar
	 * @param String json
	 * @return ArrayList<JSONObject> listJson
	 */
	public ArrayList<JSONObject> passDadosToJson(String json) {
		
		
		JSONObject jsonIterator = null;
		ArrayList<JSONObject> listJson = new ArrayList<JSONObject>();
		System.out.println(json);
		
		if (json != null) {
			try {

				JSONArray jsonArray = new JSONArray(json);

				for (int i = 0; i < jsonArray.length(); i++) {
					jsonIterator = (JSONObject) jsonArray.get(i);
					System.out.println("entrou1");
					jsonIterator = (JSONObject) jsonIterator.get("properties");
					System.out.println("entrou2");
					listJson.add(jsonIterator);
				}

			} catch (JSONException e) {
				e.printStackTrace();
			}

		}
		return listJson;

	}

}
