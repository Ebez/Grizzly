package net.cobem.grizzly.communication.composers.rooms;

import net.cobem.grizzly.communication.EventResponse;
import net.cobem.grizzly.communication.HeaderLibrary;

public class SendDanceStatusComposer {

   public static EventResponse compose(int ID, int Dance) {
      EventResponse Message = new EventResponse();
      Message.Initialize(HeaderLibrary.SendDanceStatusEvent);
      Message.addInt(Integer.valueOf(ID));
      Message.addInt(Integer.valueOf(Dance));
      return Message;
   }
}
