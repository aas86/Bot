import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.telegram.telegrambots.ApiContext;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;

import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.generics.LongPollingBot;


import java.util.List;


public class Bot extends TelegramLongPollingBot {

    private static String BOT_NAME = "Duxa_bot";
    private static String BOT_TOKEN = "672951422:AAFv8ZHzgMQF4owgao-UuqJJoOpTJK4-fsc" /* your bot's token here */;

    private static String PROXY_HOST = "138.201.6.102" /* proxy host */;
    private static Integer PROXY_PORT = 1080/* proxy port */;
    private static String PROXY_USER = "" /* proxy user */;
    private static String PROXY_PASSWORD = "" /* proxy password */;



    protected Bot(DefaultBotOptions options) {
        super(options);
    }

    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {

            ApiContextInitializer.init();

            // Create the TelegramBotsApi object to register your bots
            TelegramBotsApi botsApi = new TelegramBotsApi();

            // Set up Http proxy
            DefaultBotOptions botOptions = ApiContext.getInstance(DefaultBotOptions.class);

            CredentialsProvider credsProvider = new BasicCredentialsProvider();
            credsProvider.setCredentials(
                    new AuthScope(PROXY_HOST, PROXY_PORT),
                    new UsernamePasswordCredentials(PROXY_USER, PROXY_PASSWORD));

            HttpHost httpHost = new HttpHost(PROXY_HOST, PROXY_PORT);

            RequestConfig requestConfig = RequestConfig.custom().setProxy(httpHost).setAuthenticationEnabled(true).build();
            botOptions.setRequestConfig(requestConfig);
            botOptions.setCredentialsProvider(credsProvider);
            botOptions.setHttpProxy(httpHost);

            botsApi.registerBot((LongPollingBot) new Bot(botOptions));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

   /* public static void main(String[] args) {

        ApiContextInitializer.init(); // Инициализируем апи
        TelegramBotsApi botapi = new TelegramBotsApi();

        try {
            telegramBotsApi.registerBot(new Bot());
        } catch (TelegramApiRequestException e) {

            botapi.registerBot(new Bot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    }*/


    private void sendMsg(Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        //sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(text);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    /* @Override
     public void onUpdateReceived(Update update) {
         Message message = update.getMessage();
         if (message != null && message.hasText()) {
             switch (message.getText()) {
                 case "/help":
                     sendMSg(message, "Can I help you?");
                     break;
                 case "/settings":
                     sendMSg(message, "settings");
                     break;
             }
         }
     }*/
    /**
     * Метод для приема сообщений.
     * @param update Содержит сообщение от пользователя.
     */
    @Override
    public void onUpdateReceived(Update update) {
        String message = update.getMessage().getText();
        sendMsg(update.getMessage().getChatId().toString(), message);
        sendMsg(update.getMessage(), message);
    }

    /**
     * Метод для настройки сообщения и его отправки.
     * @param chatId id чата
     * @param s Строка, которую необходимот отправить в качестве сообщения.
     */
    public synchronized void sendMsg(String chatId, String s) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setText(s);
        try {
            sendMessage(sendMessage);
        } catch (TelegramApiException e) {
           e.printStackTrace();
        }
    @Override
    public void onUpdatesReceived(List<Update> updates) {

    }

    /**
     * Метод возвращает имя бота, указанное при регистрации.
     * @return имя бота
     */

    @Override
    public String getBotUsername() {
        return "BotName";
        return "Duxa_bot";
    }

    /**
     * Метод возвращает token бота для связи с сервером Telegram
     * @return token для бота
     */
    @Override
    public String getBotToken() {
        return "BotToken";
        return "672951422:AAFv8ZHzgMQF4owgao-UuqJJoOpTJK4-fsc";
    }

    @Override
    public void onClosing() {

    }

}
