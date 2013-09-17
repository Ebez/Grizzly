package net.cobem.grizzly.communication.composers.handshake;

import net.cobem.grizzly.communication.EventResponse;
import net.cobem.grizzly.communication.HeaderLibrary;

public class SendSecretKeyComposer {

   public static EventResponse compose() {
      EventResponse Message = new EventResponse();
      Message.Initialize(HeaderLibrary.SendSecretKeyEvent);
      Message.addString("24231219992253632572058933470468103090824667747608911151318774416044820318109");
      return Message;
   }
}
