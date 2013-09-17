package net.cobem.grizzly.communication.composers.user;

import net.cobem.grizzly.communication.EventResponse;
import net.cobem.grizzly.communication.HeaderLibrary;

public class SendToHomeRoomComposer {

   public static EventResponse compose(int Room) {
      EventResponse Message = new EventResponse();
      Message.Initialize(HeaderLibrary.SendToHomeRoomEvent);
      Message.addInt(Integer.valueOf(Room));
      Message.addInt(Integer.valueOf(Room));
      return Message;
   }
}
