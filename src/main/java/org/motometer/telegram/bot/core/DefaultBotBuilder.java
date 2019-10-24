package org.motometer.telegram.bot.core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapterFactory;
import org.jetbrains.annotations.NotNull;
import org.motometer.telegram.bot.Bot;
import org.motometer.telegram.bot.core.http.HttpClient;

import java.util.ServiceLoader;

import static java.util.Objects.requireNonNull;

class DefaultBotBuilder implements BotBuilder {

    private static final String DEFAULT_HOST = "https://api.telegram.org";
    private String token;
    private String host = DEFAULT_HOST;
    private int readTimeout = 2000;
    private int connectTimeout = 500;
    private HttpClient httpClient = HttpClient.defaultClient(connectTimeout, readTimeout);
    private Gson gson = createGson();

    @Override
    public Bot build() {
        return new DefaultBot(new BotTemplate(httpClient, baseUri(), gson), gson);
    }

    private String baseUri() {
        return host + "/bot" + token + "/";
    }

    @Override
    public BotBuilder token(String token) {
        this.token = requireNonNull(token, "token cannot be null");
        return this;
    }

    @Override
    public BotBuilder apiHost(String host) {
        this.host = requireNonNull(host, "host cannot be null");
        return this;
    }

    @Override
    public BotBuilder readTimeout(int value) {
        this.readTimeout = value;
        return this;
    }

    @Override
    public BotBuilder connectTimeout(int value) {
        this.connectTimeout = value;
        return this;
    }

    @Override
    public BotBuilder httpClient(HttpClient httpClient) {
        this.httpClient = requireNonNull(httpClient, "httpClient cannot be null");
        return null;
    }

    @NotNull
    private static Gson createGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        for (TypeAdapterFactory factory : ServiceLoader.load(TypeAdapterFactory.class)) {
            gsonBuilder.registerTypeAdapterFactory(factory);
        }
        return gsonBuilder.create();
    }
}
