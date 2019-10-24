package org.motometer.telegram.bot.core;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import lombok.RequiredArgsConstructor;
import org.motometer.telegram.bot.BotException;
import org.motometer.telegram.bot.core.http.HttpClient;
import org.motometer.telegram.bot.core.http.ImmutableRequest;
import org.motometer.telegram.bot.core.http.Request;
import org.motometer.telegram.bot.core.http.Response;

import java.io.IOException;

@RequiredArgsConstructor
class BotTemplate {

    private final HttpClient httpClient;
    private final String baseUri;
    private final Gson gson;

    <T, R> R execute(T requestBody, Method<R> method) {
        Request request = request(method.getValue(), requestBody);
        return execute(method, request);
    }

    <T> T execute(Method<T> method) {
        return execute(method, request(method.getValue()));
    }

    private <T> T execute(Method<T> method, Request request) {
        try {
            Response response = httpClient.exchange(request);
            TypeAdapter<ApiResponse<T>> adapter = gson.getAdapter(method.getTypeToken());
            final ApiResponse<T> parsedResponse = adapter.fromJson(response.content());
            return checkError(parsedResponse);
        } catch (final IOException ex) {
            throw new BotException(ex);
        }
    }

    private <T> T checkError(final ApiResponse<T> response) {
        if (response.isOk()) {
            return response.result();
        }
        throw new BotException(response.description());
    }

    private <T> Request request(String method, T body) {
        return ImmutableRequest.builder()
            .url(baseUri + method)
            .body(gson.toJson(body))
            .method(Request.Method.POST)
            .contentType("application/json")
            .build();
    }

    private Request request(String method) {
        return ImmutableRequest.builder()
            .url(baseUri + method)
            .method(Request.Method.GET)
            .build();
    }
}
