package org.motometer.telegram.bot.client;


import org.immutables.gson.Gson;
import org.immutables.value.Value;

import javax.annotation.Nullable;

@Value.Immutable
@Gson.TypeAdapters
interface ApiResponse<T> {

    @Gson.Named("ok")
    boolean isOk();

    @Nullable
    T result();

    @Nullable
    String description();
}
