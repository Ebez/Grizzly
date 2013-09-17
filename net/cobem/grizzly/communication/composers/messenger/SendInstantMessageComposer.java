package net.cobem.grizzly.communication.composers.messenger;

import net.cobem.grizzly.communication.EventResponse;
import net.cobem.grizzly.communication.HeaderLibrary;

public class SendInstantMessageComposer {

   public static EventResponse compose(int ID, String Str) {
      EventResponse Message = new EventResponse();
      Message.Initialize(HeaderLibrary.SendInstantMessageEvent);
      Message.addInt(Integer.valueOf(ID));
      Message.addString(Str);
      Message.addInt(Integer.valueOf(0));
      return Message;
   }
}
