package pro.sky.whiskerspawstailtelegrambot.mainHandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import pro.sky.whiskerspawstailtelegrambot.service.ShelterService;
import pro.sky.whiskerspawstailtelegrambot.service.VolunteerService;
import pro.sky.whiskerspawstailtelegrambot.textAndButtonsAndKeyboard.AllText;
import pro.sky.whiskerspawstailtelegrambot.textAndButtonsAndKeyboard.ConfigKeyboard;
import pro.sky.whiskerspawstailtelegrambot.util.FormReplyMessages;
import pro.sky.whiskerspawstailtelegrambot.util.ParserToBot;

/**
 * Обработчик стандартных сообщений от пользователя,
 * в том числе и из обычной клавиатуры
 */
@Slf4j
@Component
public class StandardReplyHandler {

  private final FormReplyMessages formReplyMessages;
  private final ConfigKeyboard configKeyboard;
  private final VolunteerService volunteerService;
  private final ReportAddHandler reportAddHandler;
  private final ParserToBot parserToBot;
  private final ShelterService shelterService;

  public StandardReplyHandler(FormReplyMessages formReplyMessages, ConfigKeyboard configKeyboard,
      VolunteerService volunteerService, ReportAddHandler reportAddHandler, ParserToBot parserToBot,
      ShelterService shelterService) {
    this.formReplyMessages = formReplyMessages;
    this.configKeyboard = configKeyboard;
    this.volunteerService = volunteerService;
    this.reportAddHandler = reportAddHandler;
    this.parserToBot = parserToBot;
    this.shelterService = shelterService;
  }

  /**
   * обработка текста отправленого пользователем
   * @param message сообщение из Update
   * @return SendMessage
   */
  public SendMessage handler(Message message) {

    SendMessage sendMessage = null;

    log.debug("Вызов метода handler класса" + this.getClass().getName());
    String chatId = message.getChatId().toString();
    String textMessage = message.getText();
    //здесь инжект текст кнопок, любой текст крч
    switch (textMessage) {

      case (AllText.START_TEXT):
        return sendMessage = formReplyMessages.replyMessage(message, AllText.WELCOME_MESSAGE_TEXT,
            configKeyboard.initKeyboardOnClickStart());

      case (AllText.CANCEL_TEXT)://реакция на кнопку отмена - возврат в главное меню
        return sendMessage = formReplyMessages.replyMessage(message,
            AllText.CANCEL_RETURN_MAIN_MENU_TEXT,
            configKeyboard.initKeyboardOnClickStart());

      case (AllText.CALL_TO_VOLUNTEER_TEXT): //ответ на позвать волонтера, просто инфа про волонтеров
        return sendMessage = formReplyMessages.replyMessage(message,
            parserToBot.parserVolunteer(volunteerService.getAllVolunteers()),
            configKeyboard.initKeyboardOnClickStart());

      //region реализация логики Отправить отчет о питомце
      case (AllText.SEND_PET_REPORT_TEXT):     // нажатие кнопки Отправить отчет о питомце
        return sendMessage = reportAddHandler.getSendMessageReport(message,
            AllText.MENU_SEND_PET_REPORT_TEXT,
            configKeyboard.formReplyKeyboardInOneRowInline(AllText.SHOW_ALL_YOUR_PET_TEXT,
                AllText.SEND_REPORT_TEXT, AllText.CANCEL_TEXT));
      //endregion

      case (AllText.HOW_TAKE_DOG):
        return  sendMessage = formReplyMessages.replyMessage(message, AllText.HOW_TAKE_DOG_SHELTER,
            configKeyboard.initKeyboardOnClickStart());

      case (AllText.INFO_SHELTER_TEXT):
        return sendMessage = formReplyMessages.replyMessage(message,
            shelterService.getOfShelterMessage(1L),
            configKeyboard.initKeyboardOnClickStart());

      default:
        return sendMessage = new SendMessage(chatId, AllText.UNKNOWN_COMMAND_TEXT);
    }
  }
}
