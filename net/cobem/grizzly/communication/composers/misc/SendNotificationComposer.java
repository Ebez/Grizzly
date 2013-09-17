package net.cobem.grizzly.communication.composers.misc;

import net.cobem.grizzly.communication.EventResponse;
import net.cobem.grizzly.communication.HeaderLibrary;

public class SendNotificationComposer {

   public static EventResponse compose(String Str) {
      EventResponse Message = new EventResponse();
      Message.Initialize(HeaderLibrary.SendNotificationEvent);
      Message.addString(Str);
      Message.addString("");
      return Message;
   }
}
