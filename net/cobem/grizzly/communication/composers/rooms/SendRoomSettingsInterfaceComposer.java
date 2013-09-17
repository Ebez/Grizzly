package net.cobem.grizzly.communication.composers.rooms;

import net.cobem.grizzly.communication.EventResponse;
import net.cobem.grizzly.communication.HeaderLibrary;

public class SendRoomSettingsInterfaceComposer {

   public static EventResponse compose(int RoomID) {
      EventResponse Message = new EventResponse();
      Message.Initialize(HeaderLibrary.SendRoomSettingsInterfaceEvent);
      Message.addBool(Boolean.valueOf(true));
      Message.addInt(Integer.valueOf(RoomID));
      Message.addBool(Boolean.valueOf(true));
      return Message;
   }
}
