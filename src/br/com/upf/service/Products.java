package br.com.upf.service;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import br.com.upf.dao.ProductDAO;
import br.com.upf.entity.Product;

@Path("products")
public class Products {
	@Context private HttpServletRequest request;
	
	ProductDAO dao = new ProductDAO();
	Product product = new Product();
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String listar(){
		if (!request.getSession().isNew())
			return new Gson().toJson(dao.listProduct());
		request.getSession().invalidate();
		return "Favor logar na aplicação";
	}
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response insercao(String stringJson) {
		JsonParser parser = new JsonParser();
		JsonObject json = (JsonObject) parser.parse(stringJson);

		if (json.get("nome").isJsonNull() || json.get("descricao").isJsonNull()) {
			System.out.println("Parâmetro obrigatório esta null");
			return null;
		}
	
		Boolean result = dao.saveProduct(json.get("nome").getAsString(),
				json.get("descricao").getAsString());
		
		if (!result)
			return Response
					.status(Status.BAD_REQUEST)
					.type(MediaType.APPLICATION_JSON)
					.entity(new Gson().toJson("Produto ja existe"))
					.build();
		
		return Response
				.status(Status.CREATED)
				.type(MediaType.APPLICATION_JSON)
				.entity(new Gson().toJson("Produto criado com sucesso"))
				.build();
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response alteracao(String stringJson) {
		if (request.getSession().isNew()) {
			request.getSession().invalidate();
			return Response
					.status(Status.BAD_REQUEST)
					.type(MediaType.APPLICATION_JSON)
					.entity(new Gson().toJson("Favor logar na aplicação"))
					.build();
		}
		
		JsonParser parser = new JsonParser();
		JsonObject json = (JsonObject) parser.parse(stringJson);

		if (json.get("login").isJsonNull() || json.get("nome").isJsonNull()) {
			System.out.println("Parâmetro obrigatório esta null");
			return null;
		}
	
		Boolean result = dao.alterProduct(json.get("nome").getAsString(),
				json.get("descricao").getAsString());
		
		if (!result)
			return Response
					.status(Status.BAD_REQUEST)
					.type(MediaType.APPLICATION_JSON)
					.entity(new Gson().toJson("Produto não existe"))
					.build();
		
		return Response
				.status(Status.OK)
				.type(MediaType.APPLICATION_JSON)
				.entity(new Gson().toJson(parser.parse(result.toString())))
				.build();
	}
	
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(String stringJson) {
		if (request.getSession().isNew()) {
			request.getSession().invalidate();
			return Response
					.status(Status.BAD_REQUEST)
					.type(MediaType.APPLICATION_JSON)
					.entity(new Gson().toJson("Favor logar na aplicação"))
					.build();
		}
		
		JsonParser parser = new JsonParser();
		JsonObject json = (JsonObject) parser.parse(stringJson);

		if (json.get("nome").isJsonNull() || json.get("descricao").isJsonNull()) {
			System.out.println("Parâmetro obrigatório esta null");
			return null;
		}
	
		Boolean result = dao.deleteProduct(json.get("nome").getAsString());
		
		if (!result)
			return Response
					.status(Status.BAD_REQUEST)
					.type(MediaType.APPLICATION_JSON)
					.entity(new Gson().toJson("Produto não existe"))
					.build();
		
		return Response
				.status(Status.OK)
				.type(MediaType.APPLICATION_JSON)
				.entity(new Gson().toJson(parser.parse(result.toString())))
				.build();
	}
	

}
