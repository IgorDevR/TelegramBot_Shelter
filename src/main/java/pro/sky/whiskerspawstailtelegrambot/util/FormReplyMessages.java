package pro.sky.whiskerspawstailtelegrambot.util;


import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

/**
 * сервис для формирования сообщений
 */
@Service
@Getter
@Setter
public class FormReplyMessages {

    public boolean isSuccessful;

    /**
     * сформировать ответное сообщение, с указаным текстом и клавиатурой
     */
    public SendMessage replyMessage(Message message, String textReplyMessage, ReplyKeyboardMarkup keyboardMarkup) {
        String chatId = String.valueOf(message.getChatId());
        SendMessage sendMessage = new SendMessage(chatId, textReplyMessage);
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }

    /**
     * сформировать ответное сообщение, с указаным текстом и inline клавиатурой
     */
    public SendMessage replyMessage(Message message, String textReplyMessage, InlineKeyboardMarkup inlineKeyboardMarkup) {
        String chatId = String.valueOf(message.getChatId());
        SendMessage sendMessage = new SendMessage(chatId, textReplyMessage);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        return sendMessage;
    }
    /**
     * сформировать ответное сообщение по chatId, с указаным текстом и inline клавиатурой
     */
    public SendMessage replyMessage(String chatId, String textReplyMessage, InlineKeyboardMarkup inlineKeyboardMarkup) {
        SendMessage sendMessage = new SendMessage(chatId, textReplyMessage);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        return sendMessage;
    }
    public SendMessage replyMessage(String chatId, String textReplyMessage, ReplyKeyboardMarkup keyboardMarkup) {
        SendMessage sendMessage = new SendMessage(chatId, textReplyMessage);
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }
    /**
     * сформировать ответное сообщение с сообщение об ошибке, с указаным текстом и inline клавиатурой
     * @param message
     * @param textReplyMessage
     * @param inlineKeyboardMarkup
     * @return
     */
    public SendMessage replyMessageError(Message message, String textReplyMessage, InlineKeyboardMarkup inlineKeyboardMarkup) {
        String chatId = String.valueOf(message.getChatId());
        SendMessage sendMessage = new SendMessage(chatId, textReplyMessage);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        return sendMessage;
    }

    /**
     * сформировать ответное сообщение с сообщение об ошибке, с указаным текстом и обычной клавиатурой
     * @param message
     * @param textReplyMessage
     * @param replyKeyboardMarkup
     * @return
     */
    public SendMessage replyMessageError(Message message, String textReplyMessage, ReplyKeyboardMarkup replyKeyboardMarkup) {
        String chatId = String.valueOf(message.getChatId());
        SendMessage sendMessage = new SendMessage(chatId, textReplyMessage);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        return sendMessage;
    }


}
