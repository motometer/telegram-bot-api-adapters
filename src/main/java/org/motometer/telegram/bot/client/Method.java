package org.motometer.telegram.bot.client;

import com.google.gson.reflect.TypeToken;
import org.immutables.value.Value;

@Value.Immutable
interface Method<T> {

    @Value.Parameter
    String value();

    @Value.Parameter
    TypeToken<ApiResponse<T>> typeToken();
}
