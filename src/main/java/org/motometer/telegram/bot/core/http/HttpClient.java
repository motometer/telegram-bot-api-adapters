package org.motometer.telegram.bot.core.http;

import java.io.IOException;

public interface HttpClient {

    Response exchange(Request request) throws IOException;
}
