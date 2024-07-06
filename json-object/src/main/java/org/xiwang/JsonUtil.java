package org.xiwang;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.SneakyThrows;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtil {
	public static final ObjectMapper MAPPER = new ObjectMapper();
	
	
	@SneakyThrows
	public static String toManualJson(Person person) {
		ObjectNode object = JsonNodeFactory.instance.objectNode();
		object.put("name", person.getName());
		object.put("age", person.getAge());
		ArrayNode arrayNode = object.putArray("hobbies");
		person.getHobbies().forEach(arrayNode::add);
		return object.toString();
	}
	
	@SneakyThrows
	public static Person fromManualJson(String json) {
		JsonNode node = MAPPER.readTree(json);
		Person person = new Person();
		person.setName(node.get("name").asText());
		person.setAge(node.get("age").asInt());
		person.setHobbies(new ArrayList<>());
		ArrayNode arrayNode = (ArrayNode) node.get("hobbies");
		for (JsonNode jsonNode : arrayNode) {
			person.getHobbies().add(jsonNode.asText());
		}
		return person;
	}
	
	@SneakyThrows
	public static Person fromManualJsonByOrgJson(String json) {
		JSONObject object = new JSONObject(json);
		Person person = new Person();
		person.setName(object.getString("name"));
		person.setAge(object.getInt("age"));
		person.setHobbies(new ArrayList<>());
		JSONArray array = object.getJSONArray("hobbies");
		for (int i = 0; i < array.length(); i++) {
			person.getHobbies().add(array.getString(i));
		}
		return person;
	}
	
	@SneakyThrows
	public static String toJacksonJson(Person person) {
		return MAPPER.writeValueAsString(person);
	}
	
	@SneakyThrows
	public static Person fromJacksonJson(String json) {
		return MAPPER.readValue(json, Person.class);
	}
	
	@SneakyThrows
	public static String toManualJsonByOrgJson(Person person) {
		JSONObject object = new JSONObject();
		object.put("name", person.getName());
		object.put("age", person.getAge());
		object.put("hobbies", new JSONArray(person.getHobbies()));
		return object.toString();
	}
}
