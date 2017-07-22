package projet.k_ba.network;

import android.os.AsyncTask;
import android.util.Log;

import java.util.Map;

/**
 * Created by candice on 16/07/2017.
 */
public class NetworkPutTask extends AsyncTask<Object, Void, NetworkResponse> {

    private INetworkListener listener;

    public NetworkPutTask(INetworkListener listener) {
        this.listener = listener;
    }

    @Override
    protected NetworkResponse doInBackground(Object... args) {
        try {
            String url = (String)args[0];
            Map<String, Object> params = (Map<String, Object>)args[1];
            return WebService.put(url, params, args.length > 2 ? (Map<String, String>) args[2] : null );
        } catch (Exception e) {
            Log.e("Network", "PUT", e);
            return null;
        }
    }

    @Override
    protected void onPostExecute(NetworkResponse nr) {
        this.listener.onComplete(nr);
    }
}
