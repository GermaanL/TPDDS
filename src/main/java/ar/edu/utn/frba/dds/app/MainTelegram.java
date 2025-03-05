package ar.edu.utn.frba.dds.app;

import ar.edu.utn.frba.dds.utils.notificadores.BotFactory;
import ar.edu.utn.frba.dds.utils.notificadores.TelegramBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class MainTelegram {
    public static void main(String[] args) {
        TelegramBot bot = BotFactory.getInstance("<BotToken>", "<BotUsername>");
        try {
            bot.sendMessageToUser(123, "¡Hola desde el bot de Telegram!");
            System.out.println("TelegramBot iniciado con éxito!");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}