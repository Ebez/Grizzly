package net.cobem.grizzly.communication.composers.rooms;

import net.cobem.grizzly.communication.EventResponse;
import net.cobem.grizzly.communication.HeaderLibrary;

public class SendRoomFullAlertComposer {

   public static EventResponse compose() {
      EventResponse Message = new EventResponse();
      Message.Initialize(HeaderLibrary.SendRoomFullAlertEvent);
      Message.addInt(Integer.valueOf(1));
      Message.addBool(Boolean.valueOf(false));
      Message.addBool(Boolean.valueOf(false));
      return Message;
   }
}
