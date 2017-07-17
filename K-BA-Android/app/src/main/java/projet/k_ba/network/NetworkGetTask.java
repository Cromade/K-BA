package projet.k_ba.network;

import android.os.AsyncTask;
import android.util.Log;

/**
 * Created by candice on 16/07/2017.
 */
public class NetworkGetTask extends AsyncTask<String, Void, NetworkResponse> {

    private INetworkListener listener;

    public NetworkGetTask(INetworkListener listener) {
        this.listener = listener;
    }

    @Override
    protected NetworkResponse doInBackground(String... url) {
        try {
            return WebService.get(url[0]);
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
