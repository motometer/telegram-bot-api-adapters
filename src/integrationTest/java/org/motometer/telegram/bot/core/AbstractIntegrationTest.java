package org.motometer.telegram.bot.core;

import com.github.tomakehurst.wiremock.client.WireMock;
import lombok.Getter;
import org.junit.jupiter.api.BeforeEach;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;

/**
 * Due to issue https://github.com/testcontainers/testcontainers-java/issues/1843
 * need to put {@link org.testcontainers.junit.jupiter.Testcontainers} on every subclass
 */
public abstract class AbstractIntegrationTest {

    @Container
    private static GenericContainer wiremock = new GenericContainer("rodolpheche/wiremock").withExposedPorts(8080);

    @Getter
    private String host;

    @BeforeEach
    void setUp() {
        Integer wireMockPort = wiremock.getMappedPort(8080);
        WireMock.configureFor(wireMockPort);
        host = "http://localhost:" + wireMockPort;
    }
}
