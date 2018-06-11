package br.com.upf.util;

import br.com.upf.dao.UserDAO;
import br.com.upf.entity.User;

public class Authenticate {
	
	public boolean authenticate(String login, String senha) {
		UserDAO uDao = new UserDAO();
		User user = uDao.findUserByLogin(login);
		if (login.equals(user.getLogin()) && senha.equals(user.getSenha())) {
			return true;
		}
		return false;
	}

}
