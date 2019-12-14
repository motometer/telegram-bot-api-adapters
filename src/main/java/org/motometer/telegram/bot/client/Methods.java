package org.motometer.telegram.bot.client;

import com.google.gson.reflect.TypeToken;
import org.motometer.telegram.bot.api.Message;
import org.motometer.telegram.bot.api.Update;
import org.motometer.telegram.bot.api.User;

import java.util.List;

final class Methods {

    static final Method<User> ME = of("getMe", new TypeToken<ApiResponse<User>>() {
    });
    static final Method<List<Update>> UPDATES = of("getUpdates", new TypeToken<ApiResponse<List<Update>>>() {
    });
    static final Method<Message> SEND_MESSAGE = of("sendMessage", new TypeToken<ApiResponse<Message>>() {
    });
    static final Method<Boolean> ANSWER_CALLBACK_QUERY = of("answerCallbackQuery",
        new TypeToken<ApiResponse<Boolean>>() {
        }
    );

    private static <T> ImmutableMethod<T> of(String value, TypeToken<ApiResponse<T>> typeToken) {
        return ImmutableMethod.of(value, typeToken);
    }
}
