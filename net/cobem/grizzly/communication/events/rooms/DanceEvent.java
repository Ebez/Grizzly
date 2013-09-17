package net.cobem.grizzly.communication.events.rooms;

import net.cobem.grizzly.communication.EventRequest;
import net.cobem.grizzly.communication.GameEvent;
import net.cobem.grizzly.communication.composers.rooms.SendDanceStatusComposer;
import net.cobem.grizzly.habbohotel.sessions.Session;

public class DanceEvent implements GameEvent {

   public void compose(Session Session, EventRequest Request) {
      int Dance = Request.readInt().intValue();
      Session.getActor().CurrentRoom.sendMessage(SendDanceStatusComposer.compose(Session.getHabbo().getID(), Dance));
   }
}
