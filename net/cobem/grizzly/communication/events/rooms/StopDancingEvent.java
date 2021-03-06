package net.cobem.grizzly.communication.events.rooms;

import net.cobem.grizzly.communication.EventRequest;
import net.cobem.grizzly.communication.GameEvent;
import net.cobem.grizzly.communication.composers.rooms.SendDanceStatusComposer;
import net.cobem.grizzly.habbohotel.sessions.Session;

public class StopDancingEvent implements GameEvent {

   public void compose(Session Session, EventRequest Request) {
      Session.getActor().CurrentRoom.sendMessage(SendDanceStatusComposer.compose(Session.getHabbo().getID(), 0));
   }
}
