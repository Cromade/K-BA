package projet.k_ba.network;

import android.os.AsyncTask;
import android.util.Log;

import java.util.Map;

/**
 * Created by candice on 16/07/2017.
 */
public class NetworkGetTask extends AsyncTask<Object, Void, NetworkResponse> {

    private INetworkListener listener;

    public NetworkGetTask(INetworkListener listener) {
        this.listener = listener;
    }

    @Override
    protected NetworkResponse doInBackground(Object... url) {
        try {

            return WebService.get((String)url[0], url.length > 1 ? (Map<String, String>)url[1] : null);
        } catch (Exception e) {
            Log.e("Network", "GET", e);
            return null;
        }
    }

    @Override
    protected void onPostExecute(NetworkResponse nr) {
        this.listener.onComplete(nr);
    }

}
