package org.motometer.telegram.bot.core.http;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class SimpleHttpClientTest {

    private static final String EXPECTED = "[{\"id\":\"1\",\"createdAt\":\"2019-10-21T17:55:48.292Z\",\"name\":"
        + "\"Omari Steuber\",\"avatar\":\"https://s3.amazonaws.com/uifaces/faces/twitter/traneblow/128.jpg\"}]";
    private static final String MOCK_URL = "https://5dae0512c7e88c0014aa321c.mockapi.io/updates";

    @Test
    void exchange() throws IOException {
        ImmutableRequest request = ImmutableRequest.builder()
            .method(Request.Method.GET)
            .url(MOCK_URL)
            .build();

        SimpleHttpClient client = new SimpleHttpClient(200, 1000);

        Response response = client.exchange(request);

        assertThat(response).isNotNull();
        assertThat(response.status()).isEqualTo(200);
        assertThat(response.content()).isNotEmpty();
        assertThat(response.content()).isEqualTo(EXPECTED);
    }
}