package kba.network;

import java.util.List;
import java.util.Map;

/**
 * Created by candice on 17/07/2017.
 */
public class NetworkResponse {
    public Map<String, List<String>> headers;
    private String body;

    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, List<String>> headers) {
        this.headers = headers;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getFirstHeader(String name) {
        if(headers != null) {
            List<String> list = headers.get(name);
            if(list != null && list.size() > 0) {
                return list.get(0);
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "NetworkResponse{" +
                "headers=" + headers +
                ", body='" + body + '\'' +
                '}';
    }
}
