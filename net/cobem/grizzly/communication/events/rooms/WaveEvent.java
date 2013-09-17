package net.cobem.grizzly.communication.events.rooms;

import net.cobem.grizzly.communication.EventRequest;
import net.cobem.grizzly.communication.GameEvent;
import net.cobem.grizzly.communication.composers.rooms.SendActionStatusComposer;
import net.cobem.grizzly.habbohotel.sessions.Session;

public class WaveEvent implements GameEvent {

   public void compose(Session Session, EventRequest Request) {
      int Action = Request.readInt().intValue();
      Session.getActor().CurrentRoom.sendMessage(SendActionStatusComposer.compose(Session.getHabbo().getID(), Action));
      if(Action == 5) {
         Session.getActor().idle();
      }

   }
}
