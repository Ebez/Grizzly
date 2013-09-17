package net.cobem.grizzly.habbohotel.rooms.items.interactions.games.football;

import java.util.ArrayList;
import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.habbohotel.games.soccer.BallThread;
import net.cobem.grizzly.habbohotel.pathfinding.Position;
import net.cobem.grizzly.habbohotel.rooms.actors.Actor;
import net.cobem.grizzly.habbohotel.rooms.items.FloorItem;
import net.cobem.grizzly.habbohotel.rooms.items.interactions.Interaction;
import net.cobem.grizzly.habbohotel.rooms.models.SquareState;
import net.cobem.grizzly.habbohotel.sessions.Session;

public class BallInteraction implements Interaction {

   public void onPlace(Session Session, Object Item) {}

   public void onRemove(Session Session, Object Item) {}

   public void onTrigger(Session Session, Object Item, int Request) {
      Actor Actor = Session.getActor();
      FloorItem RoomItem = (FloorItem)Item;
      Position ItemPosition = new Position(RoomItem.X, RoomItem.Y, (double)RoomItem.Height.intValue());
      int Cycles = Grizzly.rand(2, 6);
      ArrayList Path = new ArrayList();
      Position NextPosition = ItemPosition.GetPositionInFront(Actor.Rotation);
      boolean GoBackwards = false;

      for(int i = 0; i < Cycles; ++i) {
         if(Actor.CurrentRoom.getModel().Squares[NextPosition.X][NextPosition.Y] != SquareState.CLOSED && Actor.CurrentRoom.getMap().realTileOpen(NextPosition)) {
            Path.add(NextPosition);
            if(GoBackwards) {
               NextPosition = NextPosition.GetPositionBehind(Actor.Rotation);
            } else {
               NextPosition = NextPosition.GetPositionInFront(Actor.Rotation);
            }
         } else {
            GoBackwards = true;
            NextPosition = NextPosition.GetPositionBehind(Actor.Rotation);
         }
      }

      new BallThread(RoomItem, Path, Session);
   }

   public void onCycle(Object Item) {}
}
