package net.cobem.grizzly.communication.composers.handshake;

import net.cobem.grizzly.communication.EventResponse;
import net.cobem.grizzly.communication.HeaderLibrary;

public class SendAuthenticationOkayComposer {

   public static EventResponse compose() {
      EventResponse Message = new EventResponse();
      Message.Initialize(HeaderLibrary.SendAuthenticationOkayEvent);
      return Message;
   }
}
