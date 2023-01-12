package pro.sky.whiskerspawstailtelegrambot.mainHandler;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import pro.sky.whiskerspawstailtelegrambot.mainHandler.reportHandler.ReportAddHandler;
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

    String chatId = null;
    SendMessage sendMessage = null;
    Message message = null;
    String textMessage = null;
    StateAdoptiveParent state = StateAdoptiveParent.FREE;
    AdoptiveParentRecord adoptiveParent = null;
    try {
      /*
       Проверям CallbackQuery от inline клавиатуры,
       если не null то обрабатываем его в callbackQueryHandler
       иначе идем дальше.
       В зависимости от варианта берем chatId
       */
      if (update.getCallbackQuery() != null) {
        chatId = update.getCallbackQuery().getMessage().getChatId().toString();
        return sendMessage = callbackQueryHandler.handler(update.getCallbackQuery());
      } else {
        message = update.getMessage();
        textMessage = message.getText();
        chatId = message.getChatId().toString();
        state = adoptiveParentService.getStateAdoptiveParentByChatId(
            Long.parseLong(chatId));
        adoptiveParent = adoptiveParentService
            .getAdoptiveParentByChatId(Long.parseLong(chatId));
      }

        /*
       Проверям состояние пользователя,
       если состояни отличное от свободного,
        то обрабатываем состояние.
       */
      if (state != null && state.ordinal() >= 1) {

        switch (state) {

          case THE_FIRST_STATE:
            return sendMessage = registrationHandler
                .handlerWithStatusTheFirstState(message, adoptiveParent, message.getText(), chatId);
          case ONLY_NAME:
            return sendMessage = registrationHandler
                .handlerWithStatusOnlyName(message, adoptiveParent, message.getText(), chatId);
          case SUCCESS_REG:
            //
            break;
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

    } catch (
        Exception e) {
      return sendMessage = new SendMessage(chatId, AllText.ERROR_REPLY_TEXT);

    }
    return sendMessage = new

        SendMessage(chatId, AllText.ERROR_REPLY_TEXT);
  }

}







