package org.motometer.telegram.bot.core.http;

import org.immutables.value.Value;

@Value.Immutable
public interface Response {

    int status();

    String content();
}
