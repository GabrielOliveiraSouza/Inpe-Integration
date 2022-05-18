package dto;


/*
 * Classe com os campos do banco de dados e seus respectivos gets/sets 
 */
public class Dados {
	
	
	private String estado, municipio ;

	//definadas como object pois a API não especifica um tipo para esses campos
	private Object data_hora, dias_sem_chuva, risco_fogo;
		
	

	public Object getDias_sem_chuva() {
		return dias_sem_chuva;
	}

	public void setDias_sem_chuva(Object dias_sem_chuva) {
		this.dias_sem_chuva = dias_sem_chuva;
	}

	public Object getRisco_fogo() {
		return risco_fogo;
	}

	public void setRisco_fogo(Object risco_fogo) {
		this.risco_fogo = risco_fogo;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public Object getData_hora() {
		return data_hora;
	}

	public void setData_hora(Object data_hora) {
		this.data_hora = data_hora;
	}

	
}


