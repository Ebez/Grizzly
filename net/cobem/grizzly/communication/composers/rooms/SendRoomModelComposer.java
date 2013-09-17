package net.cobem.grizzly.communication.composers.rooms;

import net.cobem.grizzly.communication.EventResponse;
import net.cobem.grizzly.communication.HeaderLibrary;

public class SendRoomModelComposer {

   public static EventResponse compose(String Model, int RoomID) {
      EventResponse Message = new EventResponse();
      Message.Initialize(HeaderLibrary.SendRoomModelEvent);
      Message.addString(Model);
      Message.addInt(Integer.valueOf(RoomID));
      return Message;
   }
}
