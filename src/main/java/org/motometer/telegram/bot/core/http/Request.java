package org.motometer.telegram.bot.core.http;

import org.immutables.value.Value;
import org.jetbrains.annotations.Nullable;

@Value.Immutable
public interface Request {

    Method method();

    String url();

    @Nullable
    String body();

    enum Method {
        GET,
        POST
    }
}
