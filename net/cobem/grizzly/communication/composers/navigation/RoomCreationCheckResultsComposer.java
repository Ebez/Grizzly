package net.cobem.grizzly.communication.composers.navigation;

import net.cobem.grizzly.communication.EventResponse;
import net.cobem.grizzly.communication.HeaderLibrary;

public class RoomCreationCheckResultsComposer {

   public static EventResponse compose() {
      EventResponse Message = new EventResponse();
      Message.Initialize(HeaderLibrary.RoomCreationCheckResultsEvent);
      Message.addInt(Integer.valueOf(0));
      Message.addInt(Integer.valueOf(25));
      return Message;
   }
}
