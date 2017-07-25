package projet.k_ba.network;

import java.util.Map;

/**
 * Created by candice on 16/07/2017.
 */
public class AsyncWebServices {

    public static final String BASE_URL = "http://51.255.196.182:3000";

    public static void get(String url, INetworkListener listener) {
        new NetworkGetTask(listener).execute(BASE_URL + url);
    }
    public static void get(String url, INetworkListener listener, Map<String,String> headers) {
        new NetworkGetTask(listener).execute(BASE_URL + url, headers);
    }

    public static void delete(String url, INetworkListener listener) {
        new NetworkDeleteTask(listener).execute(BASE_URL + url);
    }
    public static void delete(String url, INetworkListener listener, Map<String,String> headers) {
        new NetworkDeleteTask(listener).execute(BASE_URL + url,headers);
    }

    public static void post(String url, Map<String, Object> parameters, INetworkListener listener) {
        new NetworkPostTask(listener).execute(BASE_URL + url, parameters);
    }

    public static void post(String url, Map<String, Object> parameters, INetworkListener listener, Map<String,String> headers) {
        new NetworkPostTask(listener).execute(BASE_URL + url, parameters, headers);
    }

    public static void put(String url, Map<String, Object> parameters, INetworkListener listener) {
        new NetworkPutTask(listener).execute(BASE_URL + url, parameters);
    }

    public static void put(String url, Map<String, Object> parameters, INetworkListener listener, Map<String,String> headers) {
        new NetworkPutTask(listener).execute(BASE_URL + url, parameters, headers);
    }
}
