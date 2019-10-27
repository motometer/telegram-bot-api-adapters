package org.motometer.telegram.bot.client.http;

import org.immutables.value.Value;

@Value.Immutable
public interface Response {

    int status();

    String content();
}
