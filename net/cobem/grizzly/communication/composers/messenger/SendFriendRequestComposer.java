package net.cobem.grizzly.communication.composers.messenger;

import net.cobem.grizzly.communication.EventResponse;
import net.cobem.grizzly.communication.HeaderLibrary;

public class SendFriendRequestComposer {

   public static EventResponse compose(int ID, String Username, String Look) {
      EventResponse Message = new EventResponse();
      Message.Initialize(HeaderLibrary.SendFriendRequestEvent);
      Message.addInt(Integer.valueOf(ID));
      Message.addString(Username);
      Message.addString(Look);
      return Message;
   }
}
