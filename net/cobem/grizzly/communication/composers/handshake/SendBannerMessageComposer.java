package net.cobem.grizzly.communication.composers.handshake;

import net.cobem.grizzly.communication.EventResponse;
import net.cobem.grizzly.communication.HeaderLibrary;

public class SendBannerMessageComposer {

   public static EventResponse compose() {
      EventResponse Message = new EventResponse();
      Message.Initialize(HeaderLibrary.SendBannerMessageEvent);
      Message.addString("12f449917de4f94a8c48dbadd92b6276");
      Message.addBool(Boolean.valueOf(false));
      return Message;
   }
}
