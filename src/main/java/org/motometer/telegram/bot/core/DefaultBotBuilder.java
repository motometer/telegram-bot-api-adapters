package org.motometer.telegram.bot.core;

import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapterFactory;
import org.motometer.telegram.bot.Bot;
import org.motometer.telegram.bot.core.http.HttpClient;

import java.util.ServiceLoader;

import static java.util.Objects.requireNonNull;

class DefaultBotBuilder implements BotBuilder {

    private static final String DEFAULT_HOST = "https://api.telegram.org";
    private String token;
    private String host;
    private int readTimeout;
    private int connectTimeout;

    DefaultBotBuilder() {
        host = DEFAULT_HOST;
        connectTimeout = 500;
        readTimeout = 2000;
    }

    @Override
    public Bot build() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        for (TypeAdapterFactory factory : ServiceLoader.load(TypeAdapterFactory.class)) {
            gsonBuilder.registerTypeAdapterFactory(factory);
        }

        HttpClient httpClient = HttpClient.defaultClient(connectTimeout, readTimeout);

        return new DefaultBot(new BotTemplate(httpClient, baseUri(), gsonBuilder.create()));
    }

    private String baseUri() {
        return host + "/bot" + requireNonNull(token) + "/";
    }

    @Override
    public BotBuilder token(String token) {
        this.token = token;
        return this;
    }

    @Override
    public BotBuilder apiHost(String host) {
        this.host = host;
        return this;
    }
}
