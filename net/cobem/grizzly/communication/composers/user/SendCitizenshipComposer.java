package net.cobem.grizzly.communication.composers.user;

import net.cobem.grizzly.communication.EventResponse;
import net.cobem.grizzly.communication.HeaderLibrary;

public class SendCitizenshipComposer {

   public static EventResponse compose() {
      EventResponse Message = new EventResponse();
      Message.Initialize(HeaderLibrary.InitializeCitizenshipPanelEvent);
      Message.addString("citizenship");
      Message.addInt(Integer.valueOf(1));
      Message.addInt(Integer.valueOf(4));
      return Message;
   }
}
