package net.cobem.grizzly.communication.composers.rooms;

import net.cobem.grizzly.communication.EventResponse;
import net.cobem.grizzly.communication.HeaderLibrary;

public class RemoveRoomUserEntityComposer {

   public static EventResponse compose() {
      EventResponse Message = new EventResponse();
      Message.Initialize(HeaderLibrary.RemoveRoomUserEntityEvent);
      Message.addBool(Boolean.valueOf(false));
      return Message;
   }
}
