package org.motometer.telegram.bot.core.http;

import org.immutables.value.Value;

import javax.annotation.Nullable;

@Value.Immutable
public interface Request {

    Method method();

    String url();

    @Nullable
    String body();

    @Nullable
    String contentType();

    enum Method {
        GET,
        POST
    }
}
