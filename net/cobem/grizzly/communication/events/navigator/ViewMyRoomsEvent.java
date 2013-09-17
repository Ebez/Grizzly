package net.cobem.grizzly.communication.events.navigator;

import java.util.HashMap;
import java.util.Map;
import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.communication.EventRequest;
import net.cobem.grizzly.communication.GameEvent;
import net.cobem.grizzly.communication.composers.navigation.SendUserRoomsComposer;
import net.cobem.grizzly.habbohotel.sessions.Session;

public class ViewMyRoomsEvent implements GameEvent {

   public void compose(Session Session, EventRequest Request) {
      Object UserRooms = new HashMap();
      if(((Map)UserRooms).size() == 0) {
         UserRooms = Grizzly.getHabboHotel().getRooms().getByOwner(Session.getHabbo().getID(), true);
      }

      Session.sendResponse(SendUserRoomsComposer.compose((Map)UserRooms));
   }
}
