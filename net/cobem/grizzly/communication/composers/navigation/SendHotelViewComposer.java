package net.cobem.grizzly.communication.composers.navigation;

import net.cobem.grizzly.communication.EventResponse;
import net.cobem.grizzly.communication.HeaderLibrary;

public class SendHotelViewComposer {

   public static EventResponse compose() {
      EventResponse Message = new EventResponse();
      Message.Initialize(HeaderLibrary.SendHotelViewEvent);
      Message.addString("");
      return Message;
   }
}
