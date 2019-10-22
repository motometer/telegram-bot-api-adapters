package org.motometer.telegram.bot.core.http;

import java.io.IOException;

public interface HttpClient {

    Response exchange(Request request) throws IOException;

    static HttpClient defaultClient(int connectTimeout, int readTimeout) {
        return new SimpleHttpClient(connectTimeout, readTimeout);
    }
}
