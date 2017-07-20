package projet.k_ba.network;

import android.os.AsyncTask;
import android.util.Log;

import java.util.Map;
import java.util.Objects;

/**
 * Created by candice on 16/07/2017.
 */
public class NetworkPostTask extends AsyncTask<Object, Void, NetworkResponse> {

    private INetworkListener listener;

    public NetworkPostTask(INetworkListener listener) {
        this.listener = listener;
    }

    @Override
    protected NetworkResponse doInBackground(Object... args) {
        try {
            String url = (String)args[0];
            Map<String, String> params = (Map<String, String>)args[1];
            return WebService.post(url, params, args.length > 2 ? (Map<String, String>) args[2] : null );
        } catch (Exception e) {
            Log.e("Network", "POST", e);
            return null;
        }
    }

    @Override
    protected void onPostExecute(NetworkResponse nr) {
        this.listener.onComplete(nr);
    }
}
