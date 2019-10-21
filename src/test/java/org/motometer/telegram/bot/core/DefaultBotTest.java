package org.motometer.telegram.bot.core;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.motometer.telegram.bot.Bot;
import org.motometer.telegram.bot.api.Message;
import org.motometer.telegram.bot.api.Update;

import java.io.IOException;
import java.util.List;

import static java.nio.charset.Charset.defaultCharset;
import static org.assertj.core.api.Assertions.assertThat;

class DefaultBotTest {

    private static final String PAYLOAD_PATH = "/org/motometer/telegram/api/UpdateTest/update.json";

    private Bot bot;

    @BeforeEach
    void setUp() {
        bot = new BotBuilder()
            .token("token")
            .apiHost("http://5dae0512c7e88c0014aa321c.mockapi.io")
            .build();
    }

    @Test
    void updates() {
        List<Update> updates = bot.updates();

        assertThat(updates).isNotEmpty();
    }

    @Test
    void handleWebhook() throws IOException {
        String input = IOUtils.resourceToString(PAYLOAD_PATH, defaultCharset());

        Update actual = null;

        assertThat(actual).isNotNull();
        Message message = actual.message();
        assertThat(message).isNotNull();
        assertThat(message.chat()).isNotNull();
    }
}