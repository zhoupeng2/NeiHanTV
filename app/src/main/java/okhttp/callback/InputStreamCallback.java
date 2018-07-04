package okhttp.callback;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.Response;

/**
 * Created by wjh on 16/8/4.
 */
public abstract class InputStreamCallback extends Callback<InputStream> {
	@Override
	public InputStream parseNetworkResponse(Response response)
			throws IOException {
		return response.body().byteStream();
	}

}
