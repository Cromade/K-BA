package projet.k_ba.network;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Set;

/**
 * Created by candice on 16/07/2017.
 */
public class WebService {
    public static NetworkResponse get(String url) throws Exception {
        return get(url, null);
    }

    public static NetworkResponse get(String url, Map<String, String> headers) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        // add request header
        if (headers != null) {
            Set<String> keys = headers.keySet();
            for (String key : keys) {
                con.setRequestProperty(key, headers.get(key));
            }
        }
        System.out.println("Sending 'GET' request to URL : " + url);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine = null;
        StringBuffer response = new StringBuffer();

        NetworkResponse networkResponse = new NetworkResponse();
        networkResponse.setHeaders(con.getHeaderFields());

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        networkResponse.setBody(response.toString());
        return networkResponse;
    }

    public static NetworkResponse post(String url, Map<String, Object> parameters) throws Exception {
        return post(url, parameters, null);
    }

    public static NetworkResponse post(String url, Map<String, Object> parameters, Map<String, String> headers) throws Exception {
        return body("POST", url, parameters, headers);
    }

    public static NetworkResponse put(String url, Map<String, Object> parameters) throws Exception {
        return post(url, parameters, null);
    }

    public static NetworkResponse put(String url, Map<String, Object> parameters, Map<String, String> headers) throws Exception {
        return body("PUT", url, parameters, headers);
    }

    private static NetworkResponse body(String method, String url, Map<String, Object> parameters, Map<String, String> headers) throws Exception {
        // Create connection
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod(method);

        String parametersStr = parameters !=null ? new JSONObject(parameters).toString() : "";

        if (headers != null) {
            Set<String> keys = headers.keySet();
            for (String key : keys) {
                con.setRequestProperty(key, headers.get(key));
            }
        }

        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Content-Length", Integer.toString(parametersStr.getBytes().length));

        con.setUseCaches(false);
        con.setDoOutput(true);

        // Send request
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(parametersStr);
        wr.close();

        // Get Response

        InputStream is = con.getInputStream();
        NetworkResponse networkResponse = new NetworkResponse();
        networkResponse.setHeaders(con.getHeaderFields());
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            response.append(line);
        }
        rd.close();

        networkResponse.setBody(response.toString());
        return networkResponse;
    }
}
