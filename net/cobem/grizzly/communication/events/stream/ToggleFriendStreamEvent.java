package net.cobem.grizzly.communication.events.stream;

import net.cobem.grizzly.communication.EventRequest;
import net.cobem.grizzly.communication.GameEvent;
import net.cobem.grizzly.habbohotel.sessions.Session;

public class ToggleFriendStreamEvent implements GameEvent {

   public void compose(Session Session, EventRequest Request) {
      int Toggle = Request.readInt().intValue();
   }
}
