package net.cobem.grizzly.communication.composers.handshake;

import net.cobem.grizzly.communication.EventResponse;
import net.cobem.grizzly.communication.HeaderLibrary;

public class SendMachineIdComposer {

   public static EventResponse compose(String MachineID) {
      EventResponse Message = new EventResponse();
      Message.Initialize(HeaderLibrary.SendMachineIDEvent);
      Message.addString(MachineID);
      return Message;
   }
}
