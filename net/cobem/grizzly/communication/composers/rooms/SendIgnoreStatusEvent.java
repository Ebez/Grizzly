package net.cobem.grizzly.communication.composers.rooms;

import net.cobem.grizzly.communication.EventResponse;
import net.cobem.grizzly.communication.HeaderLibrary;

public class SendIgnoreStatusEvent {

   public static EventResponse compose(String Username, boolean Ignore) {
      EventResponse Message = new EventResponse();
      Message.Initialize(HeaderLibrary.SendIgnoreStatusEvent);
      Message.addInt(Integer.valueOf(Ignore?1:0));
      Message.addString(Username);
      return Message;
   }
}
