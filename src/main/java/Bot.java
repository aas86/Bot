
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Bot extends TelegramLongPollingBot {
    protected Bot(DefaultBotOptions botOptions) {
        super(botOptions);
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            SendMessage message = new SendMessage()
                    .setChatId(update.getMessage().getChatId())
                    .setText(update.getMessage().getText());
            try {
                System.out.println(update.getMessage().getText());
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getBotUsername() {
        return "Duxa_bot";
    }

    @Override
    public String getBotToken() {
        return "672951422:AAFv8ZHzgMQF4owgao-UuqJJoOpTJK4-fsc";
    }
}
