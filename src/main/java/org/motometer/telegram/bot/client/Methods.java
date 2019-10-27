package org.motometer.telegram.bot.client;

import com.google.gson.reflect.TypeToken;
import org.motometer.telegram.bot.api.Message;
import org.motometer.telegram.bot.api.Update;
import org.motometer.telegram.bot.api.User;

import java.util.List;

final class Methods {

    static final Method<User> ME = create("getMe", new TypeToken<ApiResponse<User>>() {
    });
    static final Method<List<Update>> UPDATES = create("getUpdates", new TypeToken<ApiResponse<List<Update>>>() {
    });
    static final Method<Message> SEND_MESSAGE = create("sendMessage", new TypeToken<ApiResponse<Message>>() {
    });

    private static <T> ImmutableMethod<T> create(String value, TypeToken<ApiResponse<T>> typeToken) {
        return ImmutableMethod.of(value, typeToken);
    }
}
