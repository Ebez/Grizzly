package net.cobem.grizzly.communication.composers.user;

import net.cobem.grizzly.communication.EventResponse;
import net.cobem.grizzly.communication.HeaderLibrary;

public class SendCreditsComposer {

   public static EventResponse compose(int Credits) {
      EventResponse Message = new EventResponse();
      Message.Initialize(HeaderLibrary.InitializeCreditsEvent);
      Message.addString(Credits + ".0");
      return Message;
   }
}
