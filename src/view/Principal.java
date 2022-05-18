package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.text.ParseException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.jfree.chart.*;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.json.JSONException;

import controller.ServiceMysql;
import dto.Dados;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;

public class Principal extends JFrame implements Runnable {

	private ServiceMysql serviceMysql = new ServiceMysql();

	private ArrayList<Dados> listaDados = serviceMysql.getSelectPrincipal();

	private DefaultCategoryDataset barra = new DefaultCategoryDataset();

	private JPanel contentPane;

	/**
	 * Launch the application. Classe criada usando os softwares: SWING DESIGN e
	 * WINDOWLBUILDER UI CORE
	 * 
	 * @throws ParseException
	 * @throws JSONException
	 */
	public static void main(String[] args) throws JSONException, ParseException {

//		Integer option = Integer
//				.parseInt(JOptionPane.showInputDialog("ESCOLHA UMA OPÇÂO \n  1- GERAR GRAFÍCO \n 2- INSERIR DADOS "));

		Thread t2 = new Thread(new Runnable() {
			public void run() {
				try {
					Principal frame = new Principal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		Thread t1 = new Thread(new Runnable() {

			public void run() {
				try {
					ServiceMysql service = new ServiceMysql();
					System.out.println("INSERINDO DADOS...");
					service.inserirDados();
				} catch (JSONException e) {
					e.printStackTrace();
				} catch (ParseException e) {
					e.printStackTrace();
				}
				System.out.println("DADOS INSERIDOS");

			}
		});

		t1.start();
		
//		t2.suspend();

	}

	/**
	 * Create the frame.
	 */
	public Principal() {
		setTitle("DASHBOARD DE RISCO DE FOGO");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1100, 500);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		contentPane.add(criarGrafico());

		JLabel lb_opcoes = new JLabel("MAIOR RISCO DE FOGO POR :");
		lb_opcoes.setFont(new Font("Tahoma", Font.PLAIN, 9));
		lb_opcoes.setBounds(10, 5, 182, 24);
		contentPane.add(lb_opcoes);

		JButton bt_principal = new JButton("MUNICIPIO");
		bt_principal.setForeground(new Color(255, 255, 255));
		bt_principal.setBackground(new Color(0, 0, 139));
		bt_principal.setFont(new Font("Tahoma", Font.BOLD, 10));

		// event para seleção de dados principal

		bt_principal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				listaDados = serviceMysql.getSelectPrincipal();
				barra.clear();
				for (Dados dado : listaDados) {
					barra.setValue(Double.parseDouble(dado.getRisco_fogo().toString()), dado.getMunicipio(),
							dado.getEstado());
				}
			}
		});

		bt_principal.setBounds(180, 6, 157, 23);
		contentPane.add(bt_principal);
		JButton bt_estado = new JButton("ESTADO");
		bt_estado.setForeground(new Color(255, 255, 255));
		bt_estado.setBackground(new Color(0, 0, 139));
		bt_estado.setFont(new Font("Tahoma", Font.BOLD, 11));
		bt_estado.setBounds(348, 6, 157, 23);
		contentPane.add(bt_estado);

		// event para os es estados

		bt_estado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				listaDados = serviceMysql.getSelectEstados();
				barra.clear();
				for (Dados dado : listaDados) {
					barra.setValue(Double.parseDouble(dado.getRisco_fogo().toString()), dado.getEstado(),
							dado.getEstado());
				}
			}

		});

		JButton bt_hoje = new JButton("HOJE");
		bt_hoje.setForeground(new Color(255, 255, 255));
		bt_hoje.setBackground(new Color(0, 0, 139));
		bt_hoje.setBounds(515, 6, 157, 23);
		contentPane.add(bt_hoje);

		bt_hoje.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				barra.clear(); // limpa os dados do gráfico
				listaDados = serviceMysql.getSelectHoje(); // atribui ao atributo listaDados os dados daquele filtro

				for (Dados dado : listaDados) {
					barra.setValue(Double.parseDouble(dado.getRisco_fogo().toString()), dado.getMunicipio(),
							dado.getEstado()); // popula cada barra do gráfico
				}

			}
		});

		JButton bt_sem_chuva = new JButton("DIAS SEM CHUVA");
		bt_sem_chuva.setForeground(new Color(255, 255, 255));
		bt_sem_chuva.setBackground(new Color(0, 0, 139));
		bt_sem_chuva.setBounds(682, 6, 157, 23);
		contentPane.add(bt_sem_chuva);

		bt_sem_chuva.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				barra.clear();
				listaDados = serviceMysql.getSelectSemChuva();

				for (Dados dado : listaDados) {
					barra.setValue(Integer.parseInt(dado.getDias_sem_chuva().toString()), dado.getMunicipio(),
							dado.getEstado());
				}
			}
		});

	}

	/**
	 * Ultiliza a lib jfree\jfreechart\1.0.13\jfreechart-1.0.13.jar para criação do
	 * gráfico
	 * 
	 * @return ChartPanel painel
	 */
	public ChartPanel criarGrafico() {

		// configuraçãoes iniciais do gráfico

		serviceMysql.getSelectPrincipal();

		for (Dados dado : listaDados) {
			barra.setValue(Double.parseDouble(dado.getRisco_fogo().toString()), dado.getMunicipio(), dado.getEstado());
		}

		JFreeChart grafico = ChartFactory.createBarChart3D("RISCO DE FOGO: 0.0 - 1.0", null, null, barra,
				PlotOrientation.VERTICAL, true, true, true);

		ChartPanel painel = new ChartPanel(grafico);
		painel.setBounds(10, 40, 1074, 421);
		painel.setLayout(null);
		this.setLocationRelativeTo(null);
		return painel;

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}
}
