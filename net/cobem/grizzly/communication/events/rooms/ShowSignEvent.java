package net.cobem.grizzly.communication.events.rooms;

import net.cobem.grizzly.communication.EventRequest;
import net.cobem.grizzly.communication.GameEvent;
import net.cobem.grizzly.habbohotel.sessions.Session;

public class ShowSignEvent implements GameEvent {

   public void compose(Session Session, EventRequest Request) {
      int Sign = Request.readInt().intValue();
      Session.getActor().addStatus("sign", Integer.valueOf(Sign), true);
      Session.getActor().SignTimer = 5;
   }
}
