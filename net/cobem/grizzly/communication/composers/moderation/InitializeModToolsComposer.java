package net.cobem.grizzly.communication.composers.moderation;

import net.cobem.grizzly.communication.EventResponse;
import net.cobem.grizzly.communication.HeaderLibrary;

public class InitializeModToolsComposer {

   public static EventResponse compose() {
      EventResponse Message = new EventResponse();
      Message.Initialize(HeaderLibrary.InitializeModToolsEvent);
      Message.addInt(Integer.valueOf(-1));
      Message.addInt(Integer.valueOf(0));
      Message.addInt(Integer.valueOf(0));
      Message.addBool(Boolean.valueOf(true));
      Message.addBool(Boolean.valueOf(true));
      Message.addBool(Boolean.valueOf(true));
      Message.addBool(Boolean.valueOf(true));
      Message.addBool(Boolean.valueOf(true));
      Message.addBool(Boolean.valueOf(true));
      Message.addBool(Boolean.valueOf(true));
      Message.addInt(Integer.valueOf(0));
      Message.addBool(Boolean.valueOf(true));
      return Message;
   }
}
