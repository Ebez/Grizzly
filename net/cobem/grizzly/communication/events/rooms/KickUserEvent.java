package net.cobem.grizzly.communication.events.rooms;

import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.communication.EventRequest;
import net.cobem.grizzly.communication.GameEvent;
import net.cobem.grizzly.habbohotel.sessions.Session;

public class KickUserEvent implements GameEvent {

   public void compose(Session Session, EventRequest Request) {
      if(Session.getActor().CurrentRoom.getRightHolders().contains(Integer.valueOf(Session.getHabbo().getID())) || Session.getHabbo().getRank() >= 5 || Session.getActor().CurrentRoom.Owner == Session.getHabbo().getID()) {
         int UserID = Request.readInt().intValue();
         Session User = Grizzly.getHabboHotel().getSessions().getByID(UserID);
         if(User != null) {
            User.getActor().GoalRoom = 0;
            User.leaveRoom(false);
         }
      }
   }
}
