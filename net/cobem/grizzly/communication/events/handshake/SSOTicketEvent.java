package net.cobem.grizzly.communication.events.handshake;

import net.cobem.grizzly.communication.EventRequest;
import net.cobem.grizzly.communication.GameEvent;
import net.cobem.grizzly.communication.composers.handshake.SendAuthenticationOkayComposer;
import net.cobem.grizzly.communication.composers.handshake.SendMachineIdComposer;
import net.cobem.grizzly.habbohotel.sessions.Session;

public class SSOTicketEvent implements GameEvent {

   public void compose(Session Session, EventRequest Request) {
      String Ticket = Request.readString();
      Session.tryLogin(Ticket);
      Session.sendResponse(SendMachineIdComposer.compose(Session.MachineID));
      Session.sendResponse(SendAuthenticationOkayComposer.compose());
   }
}
