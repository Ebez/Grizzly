package net.cobem.grizzly.communication.composers.rooms;

import net.cobem.grizzly.communication.EventResponse;
import net.cobem.grizzly.communication.HeaderLibrary;

public class SendNewRoomUserInformationComposer {

   public static EventResponse compose(int ID, String Username) {
      EventResponse Message = new EventResponse();
      Message.Initialize(HeaderLibrary.SendNewRoomUserInformationEvent);
      Message.addInt(Integer.valueOf(1));
      Message.addInt(Integer.valueOf(ID));
      Message.addString(Username);
      Message.addInt(Integer.valueOf(0));
      return Message;
   }
}
