package org.motometer.telegram.bot.client;

import org.motometer.telegram.bot.Bot;
import org.motometer.telegram.bot.client.http.HttpClient;

public interface BotBuilder {

    Bot build();

    BotBuilder token(String token);

    BotBuilder apiHost(String host);

    BotBuilder readTimeout(int value);

    BotBuilder connectTimeout(int value);

    BotBuilder httpClient(HttpClient httpClient);

    static BotBuilder defaultBuilder() {
        return new DefaultBotBuilder();
    }
}
