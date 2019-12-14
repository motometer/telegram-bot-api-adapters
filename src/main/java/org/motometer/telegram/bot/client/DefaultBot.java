package org.motometer.telegram.bot.client;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.motometer.telegram.bot.Bot;
import org.motometer.telegram.bot.BotException;
import org.motometer.telegram.bot.UpdateListener;
import org.motometer.telegram.bot.WebHookListener;
import org.motometer.telegram.bot.api.CallbackQueryAnswer;
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
        return botTemplate.execute(Methods.ME);
    }

    @Override
    public List<Update> updates() {
        return botTemplate.execute(Methods.UPDATES);
    }

    @Override
    public Message sendMessage(SendMessage message) {
        return botTemplate.execute(message, Methods.SEND_MESSAGE);
    }

    @Override
    public boolean answerCallbackQuery(CallbackQueryAnswer answer) throws BotException {
        return botTemplate.execute(answer, Methods.ANSWER_CALLBACK_QUERY);
    }
}
