package org.motometer.telegram.bot.core;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.motometer.telegram.bot.Bot;
import org.motometer.telegram.bot.api.Update;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
class DefaultBotTest extends AbstractIntegrationTest {

    private static final String UPDATES = "/org/motometer/telegram/api/UpdateTest/updates.json";

    private Bot bot;

    @Override
    @BeforeEach
    void setUpWireMock() {
        super.setUpWireMock();
        bot = new DefaultBotBuilder()
            .token("token")
            .apiHost(host())
            .build();
    }

    @Test
    void updates() throws IOException {
        WireMock.stubFor(WireMock.get("/bottoken/getUpdates")
            .willReturn(okJson(IOUtils.resourceToString(UPDATES, Charset.defaultCharset()))));

        List<Update> updates = bot.updates();

        assertThat(updates)
            .isNotEmpty()
            .hasSize(1);
    }
}