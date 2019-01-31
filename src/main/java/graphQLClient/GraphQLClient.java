package graphQLClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GraphQLClient {

	private Map<String, String> headers = new HashMap<>();
	private URL url;

	public GraphQLClient() throws MalformedURLException {
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	public void addHeaders(String key, String value) {
		headers.put(key, value);
	}

	public void setUrl(URL url) {
		this.url = url;
	}


	public <T> T objectGraphQLQuery(String query, Class<T> targetType) throws Exception {
		JSONObject jsonQuery = createQueryJson(query);
		// Send the Query to the GraphQL Endpoint and read the Result
		HttpURLConnection conn = openHttpConnection();
		String result = readGraphQLResponse(jsonQuery, conn);
		JSONObject queryObject = parseJSON(query, result);
		Object object = new ObjectMapper().readerFor(targetType).readValue(queryObject.toString());
		return targetType.cast(object);
	}

	public <T> List<T> listGraphQLQuery(String query, Class<T> targetType) throws Exception {
		JSONObject jsonQuery = createQueryJson(query);
		HttpURLConnection conn = openHttpConnection();
		String result = readGraphQLResponse(jsonQuery, conn);
		JSONArray queryArray = parseJSONArray(query, result);
		ObjectMapper objectMapper = new ObjectMapper();
		Class<T[]> arrayClass = (Class<T[]>) Class.forName("[L" + targetType.getName() + ";");
		T[] objects = objectMapper.readValue(queryArray.toString(), arrayClass);
		return Arrays.asList(objects);
	}

	private JSONObject createQueryJson(String query) {
		JSONObject jsonQuery = new JSONObject();
		jsonQuery.put("query", query);
		return jsonQuery;
	}

	private HttpURLConnection openHttpConnection() throws IOException {
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setConnectTimeout(5000);
		conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		for (Map.Entry<String, String> entry : headers.entrySet()) {
			conn.addRequestProperty(entry.getKey(), entry.getValue());
		}
		conn.setDoOutput(true);
		conn.setDoInput(true);
		conn.setRequestMethod("POST");
		return conn;
	}

	private String readGraphQLResponse(JSONObject jsonQuery, HttpURLConnection conn) throws IOException {
		OutputStream os = conn.getOutputStream();
		os.write(jsonQuery.toString().getBytes("UTF-8"));
		os.close();
		// Read the response from the GraphQL Endpoint
		InputStream in = new BufferedInputStream(conn.getInputStream());
		String result = IOUtils.toString(in, "UTF-8");
		in.close();
		conn.disconnect();
		return result;
	}

	private JSONObject parseJSON(String query, String result) throws Exception {
		Object object = createJSONObject(query, result);
		return (JSONObject) object;
	}

	private Object createJSONObject(String query, String result) throws Exception {
		JSONParser parser = new JSONParser();
		JSONObject data = (JSONObject) parser.parse(result);
		JSONArray errors = (JSONArray) data.get("errors");
		if (errors != null) {
			for (Object error : errors) {
				JSONObject err = (JSONObject) parser.parse(error.toString());
				throw new Exception(err.get("message").toString());
			}
		}
		JSONObject dataObject = (JSONObject) data.get("data");
		int firstBraceOpened = query.indexOf("{");
		int endOfQuery = 0;
		String querySubstring = query.substring(firstBraceOpened + 1);
		if (query.contains("(") && querySubstring.indexOf("(") < querySubstring.indexOf("{")) {
			endOfQuery = query.indexOf("(");
		} else {
			endOfQuery = querySubstring.indexOf("{") + 1 + firstBraceOpened;
		}
		String queryName = query.substring(firstBraceOpened + 1, endOfQuery).trim();
		return dataObject.get(queryName);
	}

	private JSONArray parseJSONArray(String query, String result) throws Exception {
		Object object = createJSONObject(query, result);
		return (JSONArray) object;
	}

}
