package net.cobem.grizzly.communication.events.rooms;

import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.communication.EventRequest;
import net.cobem.grizzly.communication.GameEvent;
import net.cobem.grizzly.communication.composers.rooms.SendActionStatusComposer;
import net.cobem.grizzly.communication.composers.rooms.SendRespectStatusComposer;
import net.cobem.grizzly.habbohotel.sessions.Session;

public class GiveRespectEvent implements GameEvent {

   public void compose(Session Session, EventRequest Request) {
      int UserID = Request.readInt().intValue();
      Session User = Grizzly.getHabboHotel().getSessions().getByID(UserID);
      if(User != null) {
         if(Session.getHabbo().getDailyRespect() != 0) {
            User.getHabbo().setRespect(User.getHabbo().getRespect() + 1);
            User.getHabbo().append();
            Session.getHabbo().setDailyRespect(Session.getHabbo().getDailyRespect() - 1);
            Session.getHabbo().append();
            Session.getActor().CurrentRoom.sendMessage(SendActionStatusComposer.compose(Session.getHabbo().getID(), 7));
            Session.getActor().CurrentRoom.sendMessage(SendRespectStatusComposer.compose(User.getHabbo().getID(), User.getHabbo().getRespect()));
         }
      }
   }
}
