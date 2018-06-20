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

import br.com.upf.dao.UserDAO;
import br.com.upf.entity.User;

@Path("users")
public class Users {
	@Context private HttpServletRequest request;
	
	UserDAO dao = new UserDAO();
	User user = new User();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String listar(){
		if (!request.getSession().isNew())
			return new Gson().toJson(dao.listUser());
		request.getSession().invalidate();
		return new Gson().toJson("Favor logar na aplicação");
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response insercao(String stringJson) {
		JsonParser parser = new JsonParser();
		JsonObject json = (JsonObject) parser.parse(stringJson);

		if (json.get("login").isJsonNull() || json.get("nome").isJsonNull()
				|| json.get("senha").isJsonNull()) {
			System.out.println("Parâmetro obrigatório esta null");
			return null;
		}
	
		Boolean result = dao.saveUser(json.get("login").getAsString(),
				json.get("nome").getAsString(), json.get("senha").getAsString());
		
		if (!result)
			return Response
					.status(Status.BAD_REQUEST)
					.type(MediaType.APPLICATION_JSON)
					.entity(new Gson().toJson("Login ja existe"))
					.build();
		
		return Response
				.status(Status.CREATED)
				.type(MediaType.APPLICATION_JSON)
				.entity(new Gson().toJson("Usuário criado com sucesso"))
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

		if (json.get("login").isJsonNull() || json.get("nome").isJsonNull()
				|| json.get("senha").isJsonNull()) {
			System.out.println("Parâmetro obrigatório esta null");
			return null;
		}
	
		Boolean result = dao.alterUser(json.get("login").getAsString(),
				json.get("nome").getAsString(), json.get("senha").getAsString());
		
		if (!result)
			return Response
					.status(Status.BAD_REQUEST)
					.type(MediaType.APPLICATION_JSON)
					.entity(new Gson().toJson("Login não existe"))
					.build();
		
		return Response
				.status(Status.OK)
				.type(MediaType.APPLICATION_JSON)
				.entity(new Gson().toJson("Usuário alterado com sucesso"))
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

		if (json.get("login").isJsonNull()) {
			System.out.println("Parâmetro obrigatório esta null");
			return null;
		}
	
		Boolean result = dao.deleteUser(json.get("login").getAsString());
		
		if (!result)
			return Response
					.status(Status.BAD_REQUEST)
					.type(MediaType.APPLICATION_JSON)
					.entity(new Gson().toJson("Login não existe"))
					.build();
		
		return Response
				.status(Status.OK)
				.type(MediaType.APPLICATION_JSON)
				.entity(new Gson().toJson("Usuário deletado com sucesso"))
				.build();
	}

}
