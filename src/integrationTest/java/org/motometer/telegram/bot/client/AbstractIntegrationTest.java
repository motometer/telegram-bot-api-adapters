package org.motometer.telegram.bot.client;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.BeforeEach;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;

/**
 * Due to issue https://github.com/testcontainers/testcontainers-java/issues/1843
 * need to put {@link org.testcontainers.junit.jupiter.Testcontainers} on every subclass
 */
public abstract class AbstractIntegrationTest {

    @Container
    private static GenericContainer wireMock = new GenericContainer("rodolpheche/wiremock").withExposedPorts(8080);

    @BeforeEach
    void setUpWireMock() {
        WireMock.configureFor(mappedPort());
    }

    protected String host() {
        return "http://localhost:" + mappedPort();
    }

    private Integer mappedPort() {
        return wireMock.getMappedPort(8080);
    }
}
