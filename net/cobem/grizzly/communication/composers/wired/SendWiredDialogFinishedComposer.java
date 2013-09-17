package net.cobem.grizzly.communication.composers.wired;

import net.cobem.grizzly.communication.EventResponse;
import net.cobem.grizzly.communication.HeaderLibrary;

public class SendWiredDialogFinishedComposer {

   public static EventResponse compose() {
      EventResponse Message = new EventResponse();
      Message.Initialize(HeaderLibrary.SendWiredDialogFinishedEvent);
      return Message;
   }
}
