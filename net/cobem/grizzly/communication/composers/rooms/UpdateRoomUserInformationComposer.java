package net.cobem.grizzly.communication.composers.rooms;

import net.cobem.grizzly.communication.EventResponse;
import net.cobem.grizzly.communication.HeaderLibrary;

public class UpdateRoomUserInformationComposer {

   public static EventResponse compose(int ID, String Look, String Gender, String Motto) {
      EventResponse Message = new EventResponse();
      Message.Initialize(HeaderLibrary.UpdateRoomUserInformationEvent);
      Message.addInt(Integer.valueOf(ID));
      Message.addString(Look);
      Message.addString(Gender.toLowerCase());
      Message.addString(Motto);
      Message.addInt(Integer.valueOf(0));
      return Message;
   }
}
