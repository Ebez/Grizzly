package net.cobem.grizzly.communication.events.navigator;

import net.cobem.grizzly.communication.EventRequest;
import net.cobem.grizzly.communication.GameEvent;
import net.cobem.grizzly.communication.composers.navigation.RoomCreationCheckResultsComposer;
import net.cobem.grizzly.habbohotel.sessions.Session;

public class RoomCreationCheckEvent implements GameEvent {

   public void compose(Session Session, EventRequest Request) {
      Session.sendResponse(RoomCreationCheckResultsComposer.compose());
   }
}
