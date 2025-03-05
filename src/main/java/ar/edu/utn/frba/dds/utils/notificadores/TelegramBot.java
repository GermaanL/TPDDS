package ar.edu.utn.frba.dds.utils.notificadores;

import ar.edu.utn.frba.dds.utils.FileWriter;
import lombok.Getter;
import lombok.Setter;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
@Getter
@Setter
public class TelegramBot extends TelegramLongPollingBot {
    private final String botToken;
    private final String botUsername;

    public TelegramBot(String botToken, String botUsername) {
        this.botToken = botToken;
        this.botUsername = botUsername;
    }

    @Override
    public void onUpdateReceived(Update update) {
        // Verificamos si la actualización contiene un mensaje de texto
        if (update.hasMessage() && update.getMessage().hasText()) {
            // Obtener el chatId desde el mensaje
            long chatId = update.getMessage().getChatId();

            // Imprimir el chatId en la consola (puedes guardarlo en tu base de datos)
            System.out.println("Chat ID: " + chatId);

            // Enviar un mensaje al usuario (opcional)
            SendMessage message = SendMessage.builder()
                    .chatId(chatId)
                    .text("¡Hola, tu chat ID es: " + chatId + "!")
                    .build();
            try {
                execute(message); // Enviar el mensaje
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessageToUser(long chatId, String text) {
        SendMessage message = SendMessage.builder()
                .chatId(chatId)
                .text(text)
                .build();

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}