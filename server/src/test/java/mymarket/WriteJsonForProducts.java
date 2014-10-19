package mymarket;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mymarket.core.Average;
import com.mymarket.core.Market;
import com.mymarket.core.Product;

public class WriteJsonForProducts {
	
	public static void main(String... args) {
		Market market = new Market();	
		market.setName("700");
		market.setDescription("700");
		market.setLocation("700");
		
		ArrayList<Average> averages = new ArrayList<Average>();
		market.setAverages(averages);
		
		Average average1 = new Average();
		average1.setDate(new Date());
		Product product = getProduct();
		average1.setProduct(product);
		
		average1.setValue((float) 100);
		averages.add(average1);
		System.out.println(new Timestamp(new Date().getTime()));
		try {
			new ObjectMapper().writeValue(System.out, market);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static Product getProduct() {
		Product product = new Product();
		product.setId((long) 1);
		product.setName("mere");
		product.setOrigin("comuna");
		product.setImageUrl("comuna.jpg");
		return product;
	}
}
