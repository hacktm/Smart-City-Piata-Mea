package com.mymarket.resources;

import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Optional;
import com.mymarket.core.User;
import com.mymarket.db.UserDAO;
import com.sun.jersey.api.NotFoundException;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

	private final UserDAO userDao;

	public UserResource(UserDAO UserDao) {
		this.userDao = UserDao;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@UnitOfWork
	public List<User> getUsers() {
		return userDao.findAll();
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@UnitOfWork
	public User getUserById(@PathParam("id") LongParam id) {
		final Optional<User> user = userDao.findById(id.get());
		if (!user.isPresent()) {
			throw new NotFoundException("User doesn't exist.");
		}
		return user.get();
	}

	@GET
	@Path("delete/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@UnitOfWork
	public void delete(@PathParam("id") LongParam id) {
		User user = getUserById(id);
		userDao.delete(user);
	}

	@PUT
	// @Path("add")
	@Produces(MediaType.APPLICATION_JSON)
	@UnitOfWork
	public void addUser(String json) {
		try {
			User readValue = new ObjectMapper().readValue(json, User.class);
			userDao.create(readValue);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@PUT
	@Path("edit/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@UnitOfWork
	public void editUser(@PathParam("id") LongParam id, String json) {
		try {
			User readValue = new ObjectMapper().readValue(json, User.class);
			User user = getUserById(id);
			String atr;
			atr = readValue.getEmail();
			if (atr != null) {
				user.setEmail(atr);
			}
			atr = readValue.getPassword();
			if (atr != null) {
				user.setPassword(atr);
			}
			atr = readValue.getName();
			if (atr != null) {
				user.setName(atr);
			}
			atr = readValue.getType();
			if (atr != null) {
				user.setType(atr);
			}
			if (atr != null) {
				user.setAdmin(readValue.isAdmin());
			}

		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
