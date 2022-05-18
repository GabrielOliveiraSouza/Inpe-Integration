package conexoes;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Classe para fazer conexão com a API 
 */

public class ConexaoUrl {
	
	private static final String URL = "https://queimadas.dgi.inpe.br/queimadas/dados-abertos/api/focos/?pais_id=33";
	
	/**
	 * Retorna os dados vindos da API
	 * @return HttpURLConnection
	 */
	public HttpURLConnection conectaUrl() {

		HttpURLConnection connectionUrl = null;

		try {

			URL url = new URL(ConexaoUrl.URL);
			connectionUrl = (HttpURLConnection) url.openConnection();
			connectionUrl.setRequestMethod("GET");

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return connectionUrl;

	}

}
