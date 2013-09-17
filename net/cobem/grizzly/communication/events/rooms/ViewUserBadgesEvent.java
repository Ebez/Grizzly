package net.cobem.grizzly.communication.events.rooms;

import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.communication.EventRequest;
import net.cobem.grizzly.communication.GameEvent;
import net.cobem.grizzly.communication.composers.user.SendUserBadgesComposer;
import net.cobem.grizzly.communication.events.user.LoadUserTagsEvent;
import net.cobem.grizzly.habbohotel.sessions.Session;

public class ViewUserBadgesEvent implements GameEvent {

   public void compose(Session Session, EventRequest Request) {
      int UserID = Request.readInt().intValue();
      Session User = Grizzly.getHabboHotel().getSessions().getByID(UserID);
      if(User != null) {
         Session.sendResponse(SendUserBadgesComposer.compose(UserID, User.getHabbo().getEquippedBadges()));
         (new LoadUserTagsEvent()).compose(Session, Request);
      }
   }
}
