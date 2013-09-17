package net.cobem.grizzly.communication.composers.rooms;

import net.cobem.grizzly.communication.EventResponse;
import net.cobem.grizzly.communication.HeaderLibrary;

public class RemoveFloorItemComposer {

   public static EventResponse compose(int ID, int Owner) {
      EventResponse Message = new EventResponse();
      Message.Initialize(HeaderLibrary.RemoveRoomFloorEvent);
      Message.addString(Integer.valueOf(ID));
      Message.addInt(Integer.valueOf(0));
      Message.addInt(Integer.valueOf(Owner));
      return Message;
   }
}
