package org.motometer.telegram.bot.core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapterFactory;
import lombok.SneakyThrows;
import org.motometer.telegram.bot.Bot;
import org.motometer.telegram.bot.core.http.HttpClient;

import java.util.ServiceLoader;

import static java.util.Objects.requireNonNull;

public class BotBuilder {

    private static final String DEFAULT_HOST = "https://api.telegram.org";
    private String token;
    private String host;
    private int readTimeout;
    private int connectTimeout;

    public BotBuilder() {
        host = DEFAULT_HOST;
        connectTimeout = 500;
        readTimeout = 2000;
    }

    @SneakyThrows
    public Bot build() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        for (TypeAdapterFactory factory : ServiceLoader.load(TypeAdapterFactory.class)) {
            gsonBuilder.registerTypeAdapterFactory(factory);
        }

        HttpClient httpClient = HttpClient.defaultClient(connectTimeout, readTimeout);

        Gson gson = gsonBuilder.create();
        return new DefaultBot(new BotTemplate(httpClient, baseUri(), gson));
    }

    private String baseUri() {
        return host + "/bot" + requireNonNull(token) + "/";
    }

    public BotBuilder token(String token) {
        this.token = token;
        return this;
    }

    public BotBuilder apiHost(String host) {
        this.host = host;
        return this;
    }
}
