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

import br.com.upf.dao.CommentDAO;

@Path("comments")
public class Comments {

	@Context private HttpServletRequest request;
	
	CommentDAO dao = new CommentDAO();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String listar(){
	
			return new Gson().toJson(dao.listComents());
	}
	
	@POST
	@Path("products")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String listarByProduct(String stringJson){
		JsonParser parser = new JsonParser();
		JsonObject json = (JsonObject) parser.parse(stringJson);
		if (json.get("productid").isJsonNull()) {
			System.out.println("Parâmetro obrigatório esta null :P");
			return null;
		}
		String query = "produtoid = " + json.get("productid").getAsInt();
		return new Gson().toJson(dao.listComentsByQuery(query));
	}
	
	@POST
	@Path("users")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String listarByUser(String stringJson){
		JsonParser parser = new JsonParser();
		JsonObject json = (JsonObject) parser.parse(stringJson);
		if (json.get("usuarioid").isJsonNull()) {
			System.out.println("Parâmetro obrigatório esta null :P");
			return null;
		}
		String query = "usuarioid = " + json.get("usuarioid").getAsInt();
		return new Gson().toJson(dao.listComentsByQuery(query));
	}
	
	@POST
	@Path("unique")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String listarByUnique(String stringJson){
		JsonParser parser = new JsonParser();
		JsonObject json = (JsonObject) parser.parse(stringJson);
		if (json.get("usuarioid").isJsonNull() || json.get("productid").isJsonNull()) {
			System.out.println("Parâmetro obrigatório esta null :P");
			return null;
		}
		String query = "usuarioid = " + json.get("usuarioid").getAsInt() + " and produtoid = " + json.get("productid").getAsInt();
		return new Gson().toJson(dao.listComentsByQuery(query));
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response insercao(String stringJson) {
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

		if (json.get("comentario").isJsonNull() 
				|| json.get("nota").isJsonNull() 
				|| json.get("productid").isJsonNull()
				|| json.get("usuarioid").isJsonNull()) {
			
			System.out.println("Parâmetro obrigatório esta null :P");
			return null;
		}
		
		Boolean result = false;
		
		try {
			result = dao.saveComent1(
					json.get("comentario").getAsString(),
					json.get("nota").getAsString(),
					json.get("productid").getAsInt(),
					json.get("usuarioid").getAsInt());
		} catch (NumberFormatException e) {
			return Response
					.status(Status.BAD_REQUEST)
					.type(MediaType.APPLICATION_JSON)
					.entity(new Gson().toJson("Parametros productid e usuarioid devem ser inteiros!"))
					.build();
		}
		
		
		if (!result)
			return Response
					.status(Status.BAD_REQUEST)
					.type(MediaType.APPLICATION_JSON)
					.entity(new Gson().toJson("Comentario Inválido"))
					.build();
		
		return Response
				.status(Status.CREATED)
				.type(MediaType.APPLICATION_JSON)
				.entity(new Gson().toJson("Comentário feito com sucesso! :D"))
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

		if (json.get("comentario").isJsonNull() 
				|| json.get("nota").isJsonNull() 
				|| json.get("productid").isJsonNull()
				|| json.get("usuarioid").isJsonNull()) {
			
			System.out.println("Parâmetro obrigatório esta null :P");
			return null;
		}
	
		Boolean result = false;
		
		try {
			result = dao.alterComent(
					json.get("comentario").getAsString(),
					json.get("nota").getAsString(),
					json.get("productid").getAsInt(),
					json.get("usuarioid").getAsInt());
		} catch (NumberFormatException e) {
			return Response
					.status(Status.BAD_REQUEST)
					.type(MediaType.APPLICATION_JSON)
					.entity(new Gson().toJson("Parametros productid e usuarioid devem ser inteiros!"))
					.build();
		}
		
		if (!result)
			return Response
					.status(Status.BAD_REQUEST)
					.type(MediaType.APPLICATION_JSON)
					.entity(new Gson().toJson("Comentário não existe"))
					.build();
		
		return Response
				.status(Status.OK)
				.type(MediaType.APPLICATION_JSON)
				.entity(new Gson().toJson("Comentário alterado com sucesso"))
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

		if (json.get("productid").isJsonNull()
			|| json.get("usuarioid").isJsonNull()) {
			System.out.println("Parâmetro obrigatório esta null :P");
			return null;
		}
		
		Boolean result = false;
		
		try {
			result = dao.deleteComents(
					json.get("productid").getAsInt(),
					json.get("usuarioid").getAsInt());
		} catch (NumberFormatException e) {
			return Response
					.status(Status.BAD_REQUEST)
					.type(MediaType.APPLICATION_JSON)
					.entity(new Gson().toJson("Parametros productid e usuarioid devem ser inteiros!"))
					.build();
		}
		
		if (!result)
			return Response
					.status(Status.BAD_REQUEST)
					.type(MediaType.APPLICATION_JSON)
					.entity(new Gson().toJson("Comentário não existe :X"))
					.build();
		
		
		return Response
				.status(Status.OK)
				.type(MediaType.APPLICATION_JSON)
				.entity(new Gson().toJson("Comentário deletado com sucesso"))
				.build();
	}
}
