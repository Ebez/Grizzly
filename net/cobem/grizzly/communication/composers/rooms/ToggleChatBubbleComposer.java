package net.cobem.grizzly.communication.composers.rooms;

import net.cobem.grizzly.communication.EventResponse;
import net.cobem.grizzly.communication.HeaderLibrary;

public class ToggleChatBubbleComposer {

   public static EventResponse compose(int ID, boolean On) {
      EventResponse Message = new EventResponse();
      Message.Initialize(HeaderLibrary.ToggleChatBubbleEvent);
      Message.addInt(Integer.valueOf(ID));
      Message.addInt(Integer.valueOf(On?1:0));
      return Message;
   }
}
