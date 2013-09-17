package net.cobem.grizzly.communication.composers.rooms;

import net.cobem.grizzly.communication.EventResponse;
import net.cobem.grizzly.communication.HeaderLibrary;

public class SendRespectStatusComposer {

   public static EventResponse compose(int User, int Respect) {
      EventResponse Message = new EventResponse();
      Message.Initialize(HeaderLibrary.SentRespectStatusEvent);
      Message.addInt(Integer.valueOf(User));
      Message.addInt(Integer.valueOf(Respect));
      return Message;
   }
}
