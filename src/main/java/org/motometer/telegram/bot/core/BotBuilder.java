package org.motometer.telegram.bot.core;

import org.motometer.telegram.bot.Bot;

public interface BotBuilder {

    Bot build();

    BotBuilder token(String token);

    BotBuilder apiHost(String host);

    static BotBuilder defaultBuilder() {
        return new DefaultBotBuilder();
    }
}
