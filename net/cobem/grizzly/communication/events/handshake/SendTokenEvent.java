package net.cobem.grizzly.communication.events.handshake;

import net.cobem.grizzly.communication.EventRequest;
import net.cobem.grizzly.communication.GameEvent;
import net.cobem.grizzly.communication.composers.handshake.SendSecretKeyComposer;
import net.cobem.grizzly.habbohotel.sessions.Session;

public class SendTokenEvent implements GameEvent {

   public void compose(Session Session, EventRequest Request) {
      Session.sendResponse(SendSecretKeyComposer.compose());
   }
}
