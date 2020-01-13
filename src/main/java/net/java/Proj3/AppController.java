package net.java.Proj3;

import java.net.http.HttpClient;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Controller
public class AppController {
	
	@GetMapping("search")
	public String homePage(Model model) {
		List<Category> categories = Arrays.asList(Category.values());
		model.addAttribute("categories", categories);
		return "search";
	}
	
	@PostMapping("search")
	public String search(@RequestParam("category") String category, @RequestParam("location") String location, Model model)  {
		// TODO -> add try catch block, separate into new funciton
		RestTemplate restTemplate = new RestTemplate();
		System.out.println("category is " + category);
		System.out.println("location is " + location);
		String url = "https://api.yelp.com/v3/businesses/search?location="+location+"&categories="+category;
		Map<String, String> params = new HashMap<String, String>();
		params.put("categories", category);
		params.put("location", location);
	    HttpHeaders headers = new HttpHeaders();
	    String authToken = "0P7wlg1uetYTBWf9j--MFycB26vvKrVasVY8kftrg4v6yOhvsnVlhuGO5Y7JJ4zlofFW1qy6UMgscvLyd3sy3cXzabe659JpE4F2fMXOLMmSMXy2BWx334iIYmgbXnYx";
	    headers.add("Authorization", "Bearer "+ authToken);
	    HttpEntity entity = new HttpEntity(headers);
	    ResponseEntity<String> response = restTemplate.exchange(
	    	    url, HttpMethod.GET, entity, String.class, params);
	    String json = response.getBody();
	    List<Restaurant> restaurants = jsonToRestaurantList(json);
	    model.addAttribute("restaurants", restaurants);
		return "search_result";
	}
	
	public List<Restaurant> jsonToRestaurantList(String json) {
		List<Restaurant> restaurants = new ArrayList<Restaurant>();
		JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
		JsonArray businesses = jsonObject.getAsJsonArray("businesses");
		for (int i = 0; i < businesses.size(); i++) {
			try {
				JsonObject business = (JsonObject) businesses.get(i);
				String name = business.get("name").getAsString();
				float rating = business.get("rating").getAsFloat();
				String price = business.get("price").getAsString();
				float tmpDistance = business.get("distance").getAsFloat();
				float distance = (float) (tmpDistance * 0.000621371);
				JsonObject tmpAddress = (JsonObject) business.get("location");
				JsonArray tmpAddress2 = (JsonArray) tmpAddress.get("display_address");
				String address = tmpAddress2.get(0).getAsString();
				Restaurant b = new Restaurant(name, address, rating, price, distance);
				restaurants.add(b);
			}
			catch (Exception e) {
				continue;
			}
		}
		return restaurants;
		
	}

}
