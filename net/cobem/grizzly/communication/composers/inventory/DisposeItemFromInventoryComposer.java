package net.cobem.grizzly.communication.composers.inventory;

import net.cobem.grizzly.communication.EventResponse;
import net.cobem.grizzly.communication.HeaderLibrary;

public class DisposeItemFromInventoryComposer {

   public static EventResponse compose(int ID) {
      EventResponse Message = new EventResponse();
      Message.Initialize(HeaderLibrary.DisposeItemFromInventoryEvent);
      Message.addInt(Integer.valueOf(ID));
      return Message;
   }
}
