package net.cobem.grizzly.communication.composers.rooms;

import net.cobem.grizzly.communication.EventResponse;
import net.cobem.grizzly.communication.HeaderLibrary;

public class RemoveRoomItemComposer {

   public static EventResponse compose(int ID) {
      EventResponse Message = new EventResponse();
      Message.Initialize(HeaderLibrary.RemoveRoomItemEvent);
      Message.addString(Integer.valueOf(ID));
      return Message;
   }
}
