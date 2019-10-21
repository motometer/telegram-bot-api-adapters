package org.motometer.telegram.bot.core.http;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@RequiredArgsConstructor
public class SimpleHttpClient implements HttpClient {

    private final int connectTimeout;
    private final int readTimeout;

    @Override
    public Response exchange(Request request) throws IOException {
        HttpURLConnection con = createConnection(request);

        con.connect();

        String content = readContent(con);

        return ImmutableResponse.builder()
            .content(content)
            .status(con.getResponseCode())
            .build();
    }

    @NotNull
    private HttpURLConnection createConnection(Request request) throws IOException {
        URL url = new URL(request.url());
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod(request.method().name());
        con.setConnectTimeout(connectTimeout);
        con.setReadTimeout(readTimeout);
        return con;
    }

    private String readContent(HttpURLConnection con) throws IOException {
        InputStream inputStream = con.getInputStream();

        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();

        return content.toString();
    }
}
