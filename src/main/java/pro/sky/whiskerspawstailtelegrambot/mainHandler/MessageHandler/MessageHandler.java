package pro.sky.whiskerspawstailtelegrambot.mainHandler;


import java.util.Objects;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import pro.sky.whiskerspawstailtelegrambot.record.AdoptiveParentRecord;
import pro.sky.whiskerspawstailtelegrambot.service.AdoptiveParentService;
import pro.sky.whiskerspawstailtelegrambot.textAndButtonsAndKeyboard.AllText;
import pro.sky.whiskerspawstailtelegrambot.util.StateAdoptiveParent;


/**
 * обработка текстового сообщения
 */
@Slf4j
@Component("MessageHandler")
@Data
public class MessageHandler implements MainHandler {

  private final ReportAddHandler reportAddHandler;
  private final StandardReplyHandler standardReplyHandler;
  private final AdoptiveParentService adoptiveParentService;
  private final CallbackQueryHandler callbackQueryHandler;

  private final RegistrationHandler registrationHandler;


  public MessageHandler(ReportAddHandler reportAddHandler,
      StandardReplyHandler standardReplyHandler, AdoptiveParentService adoptiveParentService,
      CallbackQueryHandler callbackQueryHandler, RegistrationHandler registrationHandler) {
    this.reportAddHandler = reportAddHandler;
    this.standardReplyHandler = standardReplyHandler;
    this.adoptiveParentService = adoptiveParentService;
    this.callbackQueryHandler = callbackQueryHandler;
    this.registrationHandler = registrationHandler;
  }

  /**
   * Метод, который отвечает на входящее сообщение, либо на выбор в меню
   *
   * @param update адейт от пользователя в виде текста
   * @return отправляем ответ
   */
  @Override
  public SendMessage handler(Update update) {

    GetBaseInfoFromUpdate getBaseInfoFromUpdate = new GetBaseInfoFromUpdate(update);

    String chatId = getBaseInfoFromUpdate.getChatId();
    Message message = getBaseInfoFromUpdate.getMessage();
    String textMessage = getBaseInfoFromUpdate.getTextMessage();
    CallbackQuery callbackQuery = getBaseInfoFromUpdate.getCallbackQuery();
    boolean isCallbackQuery = getBaseInfoFromUpdate.isCallbackQuery();

    SendMessage sendMessage = new SendMessage(chatId, AllText.ERROR_REPLY_TEXT);

    try {

      if (textMessage != null && (textMessage.equals(CANCEL_TEXT) || textMessage.equals(
          REGISTRATION_CANCEL))) {
        return sendMessage = new ButtonCancelHandler().clickCancel(message, adoptiveParentService,
            chatId);
      }

        Long idChat = Long.parseLong(chatId);
        AdoptiveParentRecord adoptiveParentRecord = adoptiveParentService.getAdoptiveParentByChatId(
            idChat);
        StateAdoptiveParent state = adoptiveParentService.getStateAdoptiveParentByChatId(idChat);


      //Обязательно проверяем вначале CallbackQuery. Т.к. Message у них отличается
      // Проверям CallbackQuery от inline клавиатуры, и обрабатываем
      if (isCallbackQuery) {
        return sendMessage = callbackQueryHandler.handler(callbackQuery);
      }

        /*
       Проверям состояние пользователя,
       если состояни отличное от FREE,
        то обрабатываем состояние.
       */
      if (state != null && state != StateAdoptiveParent.FREE) {

        switch (state) {

          case THE_FIRST_STATE:
            return registrationHandler
                .handlerWithStatusTheFirstState(message, adoptiveParent, message.getText(), chatId);
          case ONLY_NAME:
            return registrationHandler
                .handlerWithStatusOnlyName(message, adoptiveParent, message.getText(), chatId);
          case START_SEND_REPORT:
            return sendMessage = reportAddHandler.handler(message);
        }
      }

      /*
       * Обработка стандартных сообщений от пользователя,
       * если он находится в свободном состоянии (например не в состоянии регистрации или отправки отчета)
       */
      if (update.getMessage().hasText()) {
        return sendMessage = standardReplyHandler.handler(message);
      }

    } catch (Exception e) {
      return sendMessage;

    }
    return sendMessage;
  }

}







