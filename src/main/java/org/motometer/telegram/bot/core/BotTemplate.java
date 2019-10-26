package org.motometer.telegram.bot.core;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import lombok.RequiredArgsConstructor;
import org.motometer.telegram.bot.BotException;
import org.motometer.telegram.bot.core.http.HttpClient;
import org.motometer.telegram.bot.core.http.ImmutableRequest;
import org.motometer.telegram.bot.core.http.Request;
import org.motometer.telegram.bot.core.http.Response;

import javax.annotation.Nullable;
import java.io.IOException;

@RequiredArgsConstructor
class BotTemplate {

    private final HttpClient httpClient;
    private final String baseUri;
    private final Gson gson;

    <T, R> R execute(T requestBody, Method<R> method) {
        Request request = createRequest(method.value(), Request.HttpMethod.POST, gson.toJson(requestBody));
        return execute(method, request);
    }

    <T> T execute(Method<T> method) {
        return execute(method, createRequest(method.value()));
    }

    private <T> T execute(Method<T> method, Request request) {
        try {
            Response response = httpClient.exchange(request);
            TypeAdapter<ApiResponse<T>> adapter = gson.getAdapter(method.typeToken());
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

    private Request createRequest(String botMethod) {
        return createRequest(botMethod, Request.HttpMethod.GET, null);
    }

    private Request createRequest(String botMethod, Request.HttpMethod httpMethod, @Nullable String body) {
        return ImmutableRequest.builder()
            .url(baseUri + botMethod)
            .body(body)
            .httpMethod(httpMethod)
            .build();
    }
}
