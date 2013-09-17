package net.cobem.grizzly.communication.events.rooms;

import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.communication.EventRequest;
import net.cobem.grizzly.communication.GameEvent;
import net.cobem.grizzly.communication.composers.rooms.SendIgnoreStatusEvent;
import net.cobem.grizzly.habbohotel.sessions.Session;

public class IgnoreUserEvent implements GameEvent {

   public void compose(Session Session, EventRequest Request) {
      String Username = Request.readString();
      Session mSession = Grizzly.getHabboHotel().getSessions().getByName(Username);
      if(mSession != null) {
         int ID = mSession.getHabbo().getID();
         Grizzly.getStorage().execute("INSERT INTO server_user_ignores (user, ignored_user) VALUES (\'" + Session.getHabbo().getID() + "\', \'" + ID + "\')");
         Session.getHabbo().getIgnored().add(Integer.valueOf(ID));
         Session.sendResponse(SendIgnoreStatusEvent.compose(Username, true));
      }
   }
}
