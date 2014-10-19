package com.mymarket.resources;

import io.dropwizard.hibernate.UnitOfWork;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.mymarket.core.User;
import com.mymarket.db.UserDAO;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

	private final UserDAO UserDao;

	public UserResource(UserDAO UserDao) {
		this.UserDao = UserDao;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@UnitOfWork
	public List<User> getUsers() {
		return UserDao.findAll();
	}
	
//	@GET
//	@Path("/User/id")
//	@Produces(MediaType.APPLICATION_JSON)
//	@UnitOfWork
//	public Market getMarketById(@PathParam("Id") LongParam id) {
//		final Optional<Market> market = UserDao.findById(id.get());
//		if (!market.isPresent()) {
//			throw new NotFoundException("No such market.");
//		}
//		return market.get();
//	}
//	
//	@PUT
//	@UnitOfWork
//	public void addMarket(String json){
//		try {
//			Market readValue = new ObjectMapper().readValue(json, Market.class);
//			UserDao.create(readValue);
//		} catch (JsonParseException e) {
//			e.printStackTrace();
//		} catch (JsonMappingException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//		
//	}

}
