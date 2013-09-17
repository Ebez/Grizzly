package net.cobem.grizzly.communication.composers.tests;

import net.cobem.grizzly.communication.EventResponse;
import net.cobem.grizzly.communication.HeaderLibrary;

public class DisplayTestContainerComposer {

   public static EventResponse compose(String Type) {
      EventResponse Message = new EventResponse();
      Message.Initialize(HeaderLibrary.DisplayTestContainerEvent);
      Message.addString(Type);
      Message.addInt(Integer.valueOf(5));
      Message.addInt(Integer.valueOf(5));
      Message.addInt(Integer.valueOf(7));
      Message.addInt(Integer.valueOf(0));
      Message.addInt(Integer.valueOf(1));
      Message.addInt(Integer.valueOf(6));
      return Message;
   }
}
