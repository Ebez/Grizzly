package net.cobem.grizzly.communication.composers.rooms;

import net.cobem.grizzly.communication.EventResponse;
import net.cobem.grizzly.communication.HeaderLibrary;

public class SendRoomRightsComposer {

   public static EventResponse compose(int Level) {
      EventResponse Message = new EventResponse();
      Message.Initialize(HeaderLibrary.InitializeRoomRightHoldersEvent);
      Message.addInt(Integer.valueOf(Level));
      return Message;
   }
}
