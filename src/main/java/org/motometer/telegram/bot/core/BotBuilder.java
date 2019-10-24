package org.motometer.telegram.bot.core;

import org.motometer.telegram.bot.Bot;

public interface BotBuilder {

    Bot build();

    BotBuilder token(String token);

    BotBuilder apiHost(String host);

    BotBuilder readTimeout(int value);

    BotBuilder connectTimeout(int value);

    static BotBuilder defaultBuilder() {
        return new DefaultBotBuilder();
    }
}
