package net.cobem.grizzly.communication.events.navigator;

import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.communication.EventRequest;
import net.cobem.grizzly.communication.GameEvent;
import net.cobem.grizzly.communication.composers.navigation.SendUserRoomsComposer;
import net.cobem.grizzly.habbohotel.sessions.Session;

public class LoadPopulatedRoomsEvent implements GameEvent {

   public void compose(Session Session, EventRequest Request) {
      Session.sendResponse(SendUserRoomsComposer.compose(Grizzly.getHabboHotel().getRooms().getPopulated()));
   }
}
