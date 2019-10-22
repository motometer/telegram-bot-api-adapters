package org.motometer.telegram.bot.core.http;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.requireNonNull;

@RequiredArgsConstructor
class SimpleHttpClient implements HttpClient {

    private final int connectTimeout;
    private final int readTimeout;

    private Map<Request.Method, ConnectionFactory> factory;

    {
        Map<Request.Method, ConnectionFactory> map = new HashMap<>();
        map.put(Request.Method.GET, this::createGETConnection);
        map.put(Request.Method.POST, this::createPOSTConnection);
        factory = Collections.unmodifiableMap(map);
    }

    @Override
    public Response exchange(Request request) throws IOException {
        HttpURLConnection urlConnection = factory.get(request.method()).create(request);

        urlConnection.connect();

        return ImmutableResponse.builder()
            .content(readContent(urlConnection))
            .status(urlConnection.getResponseCode())
            .build();
    }

    @NotNull
    private HttpURLConnection createGETConnection(Request request) throws IOException {
        URL url = new URL(request.url());
        HttpURLConnection result = (HttpURLConnection) url.openConnection();
        result.setRequestMethod(request.method().name());
        result.setConnectTimeout(connectTimeout);
        result.setReadTimeout(readTimeout);
        return result;
    }

    @NotNull
    private HttpURLConnection createPOSTConnection(Request request) throws IOException {
        HttpURLConnection result = createGETConnection(request);
        result.setDoInput(true);
        result.setDoOutput(true);
        try (OutputStream os = result.getOutputStream()) {
            String body = requireNonNull(request.body());
            os.write(body.getBytes(Charset.defaultCharset()));
        }
        return result;
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

    interface ConnectionFactory {
        HttpURLConnection create(Request request) throws IOException;
    }
}
