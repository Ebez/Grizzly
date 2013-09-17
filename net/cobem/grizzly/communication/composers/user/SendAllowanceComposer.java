package net.cobem.grizzly.communication.composers.user;

import net.cobem.grizzly.communication.EventResponse;
import net.cobem.grizzly.communication.HeaderLibrary;

public class SendAllowanceComposer {

   public static EventResponse compose(String Key, String Value) {
      EventResponse Message = new EventResponse();
      Message.Initialize(HeaderLibrary.SendAllowanceEvent);
      Message.addString(Key);
      Message.addString(Value);
      return Message;
   }
}
