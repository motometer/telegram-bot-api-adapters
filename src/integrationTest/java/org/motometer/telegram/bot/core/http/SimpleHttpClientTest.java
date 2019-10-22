package org.motometer.telegram.bot.core.http;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.motometer.telegram.bot.core.AbstractIntegrationTest;
import org.skyscreamer.jsonassert.JSONAssert;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static java.nio.charset.Charset.defaultCharset;
import static org.apache.commons.io.IOUtils.resourceToString;
import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
class SimpleHttpClientTest extends AbstractIntegrationTest {

    @Test
    void exchange() throws IOException, JSONException {
        String body = resourceToString("/org/motometer/telegram/api/UpdateTest/update.json", defaultCharset());

        WireMock.stubFor(WireMock.get("/bottoken/getUpdates")
            .willReturn(okJson(body)));

        ImmutableRequest request = ImmutableRequest.builder()
            .method(Request.Method.GET)
            .url(getHost() + "/bottoken/getUpdates")
            .build();

        SimpleHttpClient client = new SimpleHttpClient(200, 1000);

        Response response = client.exchange(request);

        assertThat(response).isNotNull();
        assertThat(response.status()).isEqualTo(200);
        assertThat(response.content()).isNotEmpty();
        JSONAssert.assertEquals(body, response.content(), true);
    }
}