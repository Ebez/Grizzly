package net.cobem.grizzly.communication.events.rooms;

import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.communication.EventRequest;
import net.cobem.grizzly.communication.GameEvent;
import net.cobem.grizzly.communication.composers.rooms.SendIgnoreStatusEvent;
import net.cobem.grizzly.habbohotel.sessions.Session;

public class ListenUserEvent implements GameEvent {

   public void compose(Session Session, EventRequest Request) {
      String Username = Request.readString();
      Session mSession = Grizzly.getHabboHotel().getSessions().getByName(Username);
      if(mSession != null) {
         int ID = mSession.getHabbo().getID();
         Grizzly.getStorage().execute("DELETE FROM server_user_ignores WHERE user = \'" + Session.getHabbo().getID() + "\' AND ignored_user = \'" + ID + "\'");
         Session.getHabbo().getIgnored().remove(ID);
         Session.sendResponse(SendIgnoreStatusEvent.compose(Username, false));
      }
   }
}
