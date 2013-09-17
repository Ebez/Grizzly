package net.cobem.grizzly.communication.composers.user;

import net.cobem.grizzly.communication.EventResponse;
import net.cobem.grizzly.communication.HeaderLibrary;

public class SendHotelViewWelcomeComposer {

   public static EventResponse compose() {
      EventResponse Message = new EventResponse();
      Message.Initialize(HeaderLibrary.SendHotelViewWelcomeEvent);
      Message.addInt(Integer.valueOf(0));
      return Message;
   }
}
