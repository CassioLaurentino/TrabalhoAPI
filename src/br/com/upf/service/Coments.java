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

import br.com.upf.dao.ComentsDAO;
import br.com.upf.entity.Coment;

		
@Path("coments")
public class Coments {

		@Context private HttpServletRequest request;
		
		ComentsDAO dao = new ComentsDAO();
		Coments coments = new Coments();
		@GET
		@Produces(MediaType.APPLICATION_JSON)
		public String listar(){
			if (!request.getSession().isNew())
				return new Gson().toJson(dao.listComents());
			request.getSession().invalidate();
			return "Favor logar na aplica��o ;D";
		}
		@POST
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public Response insercao(String stringJson) {
			JsonParser parser = new JsonParser();
			JsonObject json = (JsonObject) parser.parse(stringJson);

			if (json.get("comentario").isJsonNull() || json.get("nota").isJsonNull()) {
				System.out.println("Par�metro obrigat�rio esta null :P");
				return null;
			}
		
			Boolean result = dao.saveComents(json.get("comentario").getAsString(),
					json.get("nota").getAsString());
			
			if (!result)
				return Response
						.status(Status.BAD_REQUEST)
						.type(MediaType.APPLICATION_JSON)
						.entity(new Gson().toJson("Comentario Inv�lido"))
						.build();
			
			return Response
					.status(Status.CREATED)
					.type(MediaType.APPLICATION_JSON)
					.entity(new Gson().toJson("Coment�rio feito com sucesso! :D"))
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
						.entity(new Gson().toJson("Favor logar na aplica��o!"))
						.build();
			}
			
			JsonParser parser = new JsonParser();
			JsonObject json = (JsonObject) parser.parse(stringJson);

			if (json.get("comentario").isJsonNull() || json.get("nota").isJsonNull()) {
				System.out.println("Par�metro obrigat�rio esta null :P");
				return null;
			}
		
			Boolean result = dao.alterComents(json.get("comentario").getAsString(),
					json.get("nota").getAsString());
			
			if (!result)
				return Response
						.status(Status.BAD_REQUEST)
						.type(MediaType.APPLICATION_JSON)
						.entity(new Gson().toJson("Coment�rio n�o existe"))
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
						.entity(new Gson().toJson("Favor logar na aplica��o ;D"))
						.build();
			}
			
			JsonParser parser = new JsonParser();
			JsonObject json = (JsonObject) parser.parse(stringJson);

			if (json.get("comentario").isJsonNull() || json.get("nota").isJsonNull()) {
				System.out.println("Par�metro obrigat�rio esta null :P");
				return null;
			}
		
			Boolean result = dao.deleteComents(json.get("comentario").getAsString());
			
			if (!result)
				return Response
						.status(Status.BAD_REQUEST)
						.type(MediaType.APPLICATION_JSON)
						.entity(new Gson().toJson("Coment�rio n�o existe :X"))
						.build();
			
			return Response
					.status(Status.OK)
					.type(MediaType.APPLICATION_JSON)
					.entity(new Gson().toJson(parser.parse(result.toString())))
					.build();
		}
		
	
	}

