package org.motometer.telegram.bot.core;

import com.google.gson.reflect.TypeToken;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.motometer.telegram.bot.api.Message;
import org.motometer.telegram.bot.api.Update;
import org.motometer.telegram.bot.api.User;

import java.util.List;

@Getter
@RequiredArgsConstructor
class Method<T> {

    static final Method<User> ME = new Method<>("getMe", new TypeToken<ApiResponse<User>>() {
    });
    static final Method<List<Update>> UPDATES = new Method<>("getUpdates", new TypeToken<ApiResponse<List<Update>>>() {
    });
    static final Method<Message> SEND_MESSAGE = new Method<>("sendMessage", new TypeToken<ApiResponse<Message>>() {
    });

    private final String value;
    private final TypeToken<ApiResponse<T>> typeToken;
}
