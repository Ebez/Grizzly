package net.cobem.grizzly.communication.composers.rooms;

import net.cobem.grizzly.communication.EventResponse;
import net.cobem.grizzly.communication.HeaderLibrary;

public class RemoveWallItemComposer {

   public static EventResponse compose(int ID, int Extradata) {
      EventResponse Message = new EventResponse();
      Message.Initialize(HeaderLibrary.RemoveRoomWallEvent);
      Message.addString(Integer.valueOf(ID));
      Message.addInt(Integer.valueOf(Extradata));
      return Message;
   }
}
