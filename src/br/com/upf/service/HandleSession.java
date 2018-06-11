package br.com.upf.service;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import br.com.upf.util.Authenticate;

@Path("sessao")	
public class HandleSession {
	@Context private HttpServletRequest request;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(String stringJson) {
		JsonParser parser = new JsonParser();
		JsonObject json = (JsonObject) parser.parse(stringJson);
		
		if (json.get("login").isJsonNull() || json.get("senha").isJsonNull()) {
			System.out.println("Parâmetro obrigatório esta null");
			return null;
		}
		
		if (!request.getSession().isNew()) {
			return Response
					.status(Status.OK)
					.type(MediaType.APPLICATION_JSON)
					.entity(new Gson().toJson("Voce já está logado!"))
					.build();
		}
		
		Authenticate auth = new Authenticate();
		
		if (auth.authenticate(json.get("login").getAsString(), json.get("senha").getAsString())) {	
			request.getSession(true);
			return Response
					.status(Status.OK)
					.type(MediaType.APPLICATION_JSON)
					.entity(new Gson().toJson("Logado com Sucesso"))
					.build();
		} else {
			request.getSession().invalidate();
			return Response
					.status(Status.BAD_REQUEST)
					.type(MediaType.APPLICATION_JSON)
					.entity(new Gson().toJson("Login or senha estão errados"))
					.build();
		}
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response logout() {
		if (request.getSession().isNew()) {
			request.getSession().invalidate();
			return Response
					.status(Status.OK)
					.type(MediaType.APPLICATION_JSON)
					.entity(new Gson().toJson("Voce não está logado!"))
					.build();
		}
		
		request.getSession().invalidate();
		
		return Response
				.status(Status.OK)
				.type(MediaType.APPLICATION_JSON)
				.entity(new Gson().toJson("Deslogado com sucesso"))
				.build();
		
	}
	
}
