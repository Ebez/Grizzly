package net.cobem.grizzly.communication.events.rooms;

import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.communication.EventRequest;
import net.cobem.grizzly.communication.GameEvent;
import net.cobem.grizzly.communication.composers.rooms.RemoveRoomUserEntityComposer;
import net.cobem.grizzly.habbohotel.sessions.Session;

public class LeaveRoomEvent implements GameEvent {

   public void compose(Session Session, EventRequest Request) {
      if(Session.getActor().inRoom().booleanValue()) {
         Session.sendResponse(RemoveRoomUserEntityComposer.compose());
         if(Session.getActor().CurrentRoom.getParty().size() == 1) {
            Grizzly.getHabboHotel().getRooms().dispose(Session.getActor().CurrentRoom.ID, false);
         }

         Session.getActor().CurrentRoom.removeUser(Session, false);
      }
   }
}
