package net.cobem.grizzly.communication.composers.rooms;

import net.cobem.grizzly.communication.EventResponse;
import net.cobem.grizzly.communication.HeaderLibrary;

public class LeaveRoomComposer {

   public static EventResponse compose(int User) {
      EventResponse Message = new EventResponse();
      Message.Initialize(HeaderLibrary.LeaveRoomEvent);
      Message.addString(Integer.valueOf(User));
      return Message;
   }
}
