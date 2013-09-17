package net.cobem.grizzly.communication.composers.rooms;

import net.cobem.grizzly.communication.EventResponse;
import net.cobem.grizzly.communication.HeaderLibrary;

public class SendRoomPapersComposer {

   public static EventResponse compose(String Type, String Value) {
      EventResponse Message = new EventResponse();
      Message.Initialize(HeaderLibrary.SendRoomPapersEvent);
      Message.addString(Type);
      Message.addString(Value);
      return Message;
   }
}
