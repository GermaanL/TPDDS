package ar.edu.utn.frba.dds.utils.notificadores;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class BotFactory {
    private static TelegramBot bot;

    public static TelegramBot getInstance(String botToken, String botUsername) {
        if (bot == null) {
            bot = new TelegramBot(botToken, botUsername);
            try {
                TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
                botsApi.registerBot(bot);
                System.out.println("Bot registrado con éxito.");
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Error al registrar el bot", e);
            }
        }
        return bot;
    }
}
