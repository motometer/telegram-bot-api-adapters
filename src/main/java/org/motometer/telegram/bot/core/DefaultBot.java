package org.motometer.telegram.bot.core;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.motometer.telegram.bot.Bot;
import org.motometer.telegram.bot.UpdateListener;
import org.motometer.telegram.bot.WebHookListener;
import org.motometer.telegram.bot.api.Message;
import org.motometer.telegram.bot.api.SendMessage;
import org.motometer.telegram.bot.api.Update;
import org.motometer.telegram.bot.api.User;

import java.util.List;


@RequiredArgsConstructor
class DefaultBot implements Bot {

    private final BotTemplate botTemplate;
    private final Gson gson;

    @Override
    public WebHookListener adaptListener(UpdateListener listener) {
        return event -> listener.onEvent(gson.fromJson(event, Update.class));
    }

    @Override
    public User me() {
        return botTemplate.execute(Method.ME);
    }

    @Override
    public List<Update> updates() {
        return botTemplate.execute(Method.UPDATES);
    }

    @Override
    public Message sendMessage(SendMessage message) {
        return botTemplate.execute(message, Method.SEND_MESSAGE);
    }
}
