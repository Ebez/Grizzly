package net.cobem.grizzly.communication.composers.rooms;

import net.cobem.grizzly.communication.EventResponse;
import net.cobem.grizzly.communication.HeaderLibrary;

public class SendRoomInformationComposer {

   public static EventResponse compose(int ID, String Name) {
      EventResponse Message = new EventResponse();
      Message.Initialize(HeaderLibrary.SendRoomInformationEvent);
      Message.addInt(Integer.valueOf(ID));
      Message.addString(Name);
      return Message;
   }
}
