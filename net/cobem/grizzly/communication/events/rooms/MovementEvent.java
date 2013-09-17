package net.cobem.grizzly.communication.events.rooms;

import net.cobem.grizzly.communication.EventRequest;
import net.cobem.grizzly.communication.GameEvent;
import net.cobem.grizzly.habbohotel.pathfinding.Position;
import net.cobem.grizzly.habbohotel.sessions.Session;

public class MovementEvent implements GameEvent {

   public void compose(Session Session, EventRequest Request) {
      if(Session.getActor().CanWalk) {
         int X = Request.readInt().intValue();
         int Y = Request.readInt().intValue();
         Session.getActor().GoalPosition = new Position(X, Y, Session.getActor().CurrentPosition.Z);
         Session.getActor().IsMoving = true;
         Session.getActor().unidle();
      }
   }
}
