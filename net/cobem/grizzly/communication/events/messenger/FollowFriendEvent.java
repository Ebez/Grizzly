package net.cobem.grizzly.communication.events.messenger;

import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.communication.EventRequest;
import net.cobem.grizzly.communication.GameEvent;
import net.cobem.grizzly.habbohotel.sessions.Session;

public class FollowFriendEvent implements GameEvent {

   public void compose(Session Session, EventRequest Request) {
      int Friend = Request.readInt().intValue();
      Session.travel(Grizzly.getHabboHotel().getSessions().getByID(Friend).getActor().CurrentRoom.ID);
   }
}
