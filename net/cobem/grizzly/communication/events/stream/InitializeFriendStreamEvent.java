package net.cobem.grizzly.communication.events.stream;

import net.cobem.grizzly.communication.EventRequest;
import net.cobem.grizzly.communication.GameEvent;
import net.cobem.grizzly.communication.composers.stream.InitializeFriendStreamComposer;
import net.cobem.grizzly.habbohotel.sessions.Session;

public class InitializeFriendStreamEvent implements GameEvent {

   public void compose(Session Session, EventRequest Request) {
      Session.sendResponse(InitializeFriendStreamComposer.compose());
   }
}
