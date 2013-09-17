package net.cobem.grizzly.communication.composers.inventory;

import net.cobem.grizzly.communication.EventResponse;
import net.cobem.grizzly.communication.HeaderLibrary;

public class UpdateUserInventoryComposer {

   public static EventResponse compose() {
      EventResponse Message = new EventResponse();
      Message.Initialize(HeaderLibrary.UpdateUserInventoryEvent);
      return Message;
   }
}
