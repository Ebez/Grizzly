package net.cobem.grizzly.communication.events.rooms;

import net.cobem.grizzly.communication.EventRequest;
import net.cobem.grizzly.communication.GameEvent;
import net.cobem.grizzly.habbohotel.sessions.Session;

public class SitEvent implements GameEvent {

   public void compose(Session Session, EventRequest Request) {
      int User = Request.readInt().intValue();
      if(!Session.getActor().hasStatus("sit") && !Session.getActor().hasStatus("lay")) {
         if(Session.getActor().Rotation % 2 != 0) {
            ++Session.getActor().Rotation;
         }

         Session.getActor().addStatus("sit", Double.valueOf(Session.getActor().CurrentPosition.Z + 0.65D), true);
      }
   }
}
