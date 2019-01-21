

import net.aksingh.owmjapis.api.APIException;
import net.aksingh.owmjapis.core.OWM;
import net.aksingh.owmjapis.model.CurrentWeather;
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
            OWM owm = new OWM("4a4b55c74080d5b7b7d2ab3f842e7beb");
            try {
                CurrentWeather cwd = owm.currentWeatherByCityName("Novosibirsk");
                if (cwd.hasRespCode() && cwd.getRespCode() == 200) {
                    if (cwd.hasCityName()) {
                        System.out.println("City: " + cwd.getCityName());
                    }
                    if (cwd.hasMainData() && cwd.getMainData().hasTempMax() && cwd.getMainData().hasTempMin()) {
                        System.out.println("Temperature: " + Math.round(cwd.getMainData().getTemp() - 273) + " C");

                    }
                }
            } catch (APIException e) {
                e.printStackTrace();
            }

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
