package net.cobem.grizzly.communication.composers.rooms;

import net.cobem.grizzly.communication.EventResponse;
import net.cobem.grizzly.communication.HeaderLibrary;

public class SendActionStatusComposer {

   public static EventResponse compose(int User, int ID) {
      EventResponse Message = new EventResponse();
      Message.Initialize(HeaderLibrary.SendWaveStatusEvent);
      Message.addInt(Integer.valueOf(User));
      Message.addInt(Integer.valueOf(ID));
      return Message;
   }
}
