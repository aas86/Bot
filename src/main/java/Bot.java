import net.aksingh.owmjapis.api.APIException;
import net.aksingh.owmjapis.core.OWM;
import net.aksingh.owmjapis.model.CurrentWeather;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class Bot extends TelegramLongPollingBot {
    protected Bot(DefaultBotOptions botOptions) {
        super(botOptions);
    }

    @Override
    public void onUpdateReceived(Update update) {
        SendMessage message = new SendMessage();
        setButtons(message);
        if (update.hasMessage() && update.getMessage().getText().equals("Погода в НСК")) {

            message.setChatId(update.getMessage().getChatId());
            OWM owm = new OWM("4a4b55c74080d5b7b7d2ab3f842e7beb");
            try {
                CurrentWeather cwd = owm.currentWeatherByCityName("Novosibirsk");
                if (cwd.hasRespCode() && cwd.getRespCode() == 200) {
                    if (cwd.hasCityName()) {
                        System.out.println("City: " + cwd.getCityName());
                    }
                    if (cwd.hasMainData() && cwd.getMainData().hasTempMax() && cwd.getMainData().hasTempMin()) {
                        System.out.println("Temperature: " + Math.round(cwd.getMainData().getTemp() - 273) + " C");
                        message.setText("Temperature: " + Math.round(cwd.getMainData().getTemp() - 273));
                    }
                }
            } catch (APIException e) {
                e.printStackTrace();
            }
        } else if (update.hasMessage() && update.getMessage().hasText()) {
            message.setChatId(update.getMessage().getChatId()).setText(update.getMessage().getText());
        }
        try {
            System.out.println(update.getMessage().getText());
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return "Duxa_bot";
    }

    @Override
    public String getBotToken() {
        return "672951422:AAFGUpS0k3MBJAgSdBRwPqBa7p_0qbRZojc";
    }
    public synchronized void setButtons(SendMessage sendMessage) {
        // Создаем клавиуатуру
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        // Создаем список строк клавиатуры
        List<KeyboardRow> keyboard = new ArrayList<>();

        // Первая строчка клавиатуры
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        // Добавляем кнопки в первую строчку клавиатуры
        keyboardFirstRow.add(new KeyboardButton("Погода в НСК"));


        // Добавляем все строчки клавиатуры в список
        keyboard.add(keyboardFirstRow);

        // и устанваливаем этот список нашей клавиатуре
        replyKeyboardMarkup.setKeyboard(keyboard);
    }


}