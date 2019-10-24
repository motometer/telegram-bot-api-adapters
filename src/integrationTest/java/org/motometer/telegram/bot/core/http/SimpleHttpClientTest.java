package org.motometer.telegram.bot.core.http;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.motometer.telegram.bot.core.AbstractIntegrationTest;
import org.skyscreamer.jsonassert.JSONAssert;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static java.nio.charset.Charset.defaultCharset;
import static org.apache.commons.io.IOUtils.resourceToString;
import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
class SimpleHttpClientTest extends AbstractIntegrationTest {

    private String body;

    @BeforeEach
    void setUp() throws IOException {
        body = resourceToString("/org/motometer/telegram/api/UpdateTest/update.json", defaultCharset());
    }

    @Test
    void exchangeGET() throws IOException, JSONException {

        WireMock.stubFor(WireMock.get("/bottoken/getUpdates")
            .willReturn(okJson(body)));

        ImmutableRequest request = ImmutableRequest.builder()
            .method(Request.Method.GET)
            .url(host() + "/bottoken/getUpdates")
            .build();

        SimpleHttpClient client = new SimpleHttpClient(200, 1000);

        Response response = client.exchange(request);

        assertResponse(body, response);
    }

    @Test
    void exchangePOST() throws IOException, JSONException {

        WireMock.stubFor(WireMock.post("/bottoken/getUpdates")
            .withHeader("Content-Type", equalTo("application/json"))
            .withRequestBody(equalTo("{}"))
            .willReturn(okJson(body)));

        ImmutableRequest request = ImmutableRequest.builder()
            .method(Request.Method.POST)
            .url(host() + "/bottoken/getUpdates")
            .contentType("application/json")
            .body("{}")
            .build();

        SimpleHttpClient client = new SimpleHttpClient(200, 1000);

        Response response = client.exchange(request);

        assertResponse(body, response);
    }

    private void assertResponse(String body, Response response) throws JSONException {
        assertThat(response).isNotNull();
        assertThat(response.status()).isEqualTo(200);
        assertThat(response.content()).isNotEmpty();
        JSONAssert.assertEquals(body, response.content(), true);
    }
}