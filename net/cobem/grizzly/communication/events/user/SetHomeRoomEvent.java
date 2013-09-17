package net.cobem.grizzly.communication.events.user;

import net.cobem.grizzly.communication.EventRequest;
import net.cobem.grizzly.communication.GameEvent;
import net.cobem.grizzly.habbohotel.sessions.Session;

public class SetHomeRoomEvent implements GameEvent {

   public void compose(Session Session, EventRequest Request) {
      int ID = Request.readInt().intValue();
      Session.getHabbo().setHome(ID);
      Session.getHabbo().append();
   }
}
