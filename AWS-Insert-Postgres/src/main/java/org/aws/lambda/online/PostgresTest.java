package org.aws.lambda.online;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import org.aws.lambda.online.data.RequestDetails;
import org.aws.lambda.online.data.ResponseDetails;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class PostgresTest implements RequestHandler<RequestDetails, ResponseDetails> {

	public ResponseDetails handleRequest(RequestDetails requestDetails, Context context) {
		// TODO Auto-generated method stub
		ResponseDetails responseDetails = new ResponseDetails();
			try {
				insertDetails(requestDetails, responseDetails);
			} catch (SQLException sqlException) {
				responseDetails.setMessageID("999");
				responseDetails.setMessageReason("Registro não encontrado motivo: " + sqlException);

			}
			return responseDetails;
		
	}
	
	private void insertDetails(RequestDetails requestDetails, ResponseDetails responseDetails) throws SQLException {
		Connection connection = getConection();
		Statement statement = connection.createStatement();
		String query = getquery(requestDetails);
		int responseCode = statement.executeUpdate(query);
		if (1 == responseCode) {
			responseDetails.setMessageID(String.valueOf(responseCode));
			responseDetails.setMessageReason("Sucesso na alteração dos detalhes");
		}
	}

	private String getquery(RequestDetails requestDetails) {

		String query = "INSERT INTO public.LIDERES_DE_RUA (id, nome, logradouro, numero, cep, telefone, email, data_nascimento, "
				+ "rg, cpf, agente_comunitario_de_saude, observacoes, status) VALUES(";
		if (requestDetails != null) {
			query = query.concat("'" + requestDetails.getId() + "','" + requestDetails.getNome() + "'" + ",'"
					+ requestDetails.getLogradouro() + "','" + requestDetails.getNumero() + "','"
					+ requestDetails.getCep() + "'" + ",'" + requestDetails.getTelefone() + "','"
					+ requestDetails.getEmail() + "'" + ",'" + requestDetails.getData_nascimento() + "','"
					+ requestDetails.getRg() + "','" + requestDetails.getCpf() + "','"
					+ requestDetails.getAgente_comunitario_de_saude() + "','" + requestDetails.getObservacoes() + "','"
					+ requestDetails.getStatus() + "')");
		}
		System.out.println("A consulta " + query);
		return query;
	}

	private Connection getConection() throws SQLException {
		String url = "jdbc:postgresql://conectainstancia.cbcskmsytoea.us-east-1.rds.amazonaws.com:5432/ConectaDB";
		String username = "AdminConecta";
		String password = "*abc*123";
		Connection conn = DriverManager.getConnection(url, username, password);
		return conn;

	}


//	public static void main(String[] args) {
//        RequestDetails requestDetails=new RequestDetails();
//        requestDetails.setId((long) 9);
//        requestDetails.setNome("Bruna Paco");
//        requestDetails.setAgente_comunitario_de_saude("Manoel Gomes");
//        requestDetails.setCep("785555");
//        requestDetails.setCpf("5842255555");
//        requestDetails.setRg("75222");
//        requestDetails.setData_nascimento("14/11/1895");
//        requestDetails.setEmail("teste@testes.com.br");
//        requestDetails.setLogradouro("rua certa");
//        requestDetails.setNumero(0);
//        requestDetails.setObservacoes("nada a declarar");
//        requestDetails.setStatus("A");
//        requestDetails.setTelefone("3355-8477");
//        Connection connection = null;
//		try {
//			connection = getConection();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		Statement statement = null;
//		try {
//			statement = connection.createStatement();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		String query = getquery(requestDetails);
//		int responseCode = 0;
//		try {
//			responseCode = statement.executeUpdate(query);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		if (1 == responseCode) {
//			System.out.println("deu certo"+responseCode);
//		
//	}
//}
}