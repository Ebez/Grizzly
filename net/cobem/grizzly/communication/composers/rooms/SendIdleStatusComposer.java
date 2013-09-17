package net.cobem.grizzly.communication.composers.rooms;

import net.cobem.grizzly.communication.EventResponse;
import net.cobem.grizzly.communication.HeaderLibrary;

public class SendIdleStatusComposer {

   public static EventResponse compose(int User, boolean IsIdle) {
      EventResponse Message = new EventResponse();
      Message.Initialize(HeaderLibrary.SendIdleStatusEvent);
      Message.addInt(Integer.valueOf(User));
      Message.addBool(Boolean.valueOf(IsIdle));
      return Message;
   }
}
