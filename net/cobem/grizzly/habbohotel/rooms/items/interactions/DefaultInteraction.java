package net.cobem.grizzly.habbohotel.rooms.items.interactions;

import net.cobem.grizzly.habbohotel.pathfinding.Position;
import net.cobem.grizzly.habbohotel.rooms.actors.Actor;
import net.cobem.grizzly.habbohotel.rooms.items.FloorItem;
import net.cobem.grizzly.habbohotel.rooms.items.WallItem;
import net.cobem.grizzly.habbohotel.rooms.items.interactions.Interaction;
import net.cobem.grizzly.habbohotel.sessions.Session;

public class DefaultInteraction implements Interaction {

   public void onPlace(Session Session, Object Item) {}

   public void onRemove(Session Session, Object Item) {}

   public void onTrigger(Session Session, Object Item, int Request) {
      boolean IsWall = false;
      Actor Actor = Session.getActor();

      FloorItem RoomItem;
      try {
         RoomItem = (FloorItem)Item;
      } catch (Exception var8) {
         IsWall = true;
      }

      if(IsWall) {
         WallItem RoomItem1 = (WallItem)Item;
         RoomItem1.changeState();
         RoomItem1.serializeUpdate(Actor.CurrentRoom);
         RoomItem1.saveState();
      } else {
         RoomItem = (FloorItem)Item;
         Position ItemPosition = new Position(RoomItem.X, RoomItem.Y, (double)RoomItem.Height.intValue());
         if(!Actor.CurrentPosition.TilesTouching(ItemPosition)) {
            Session.getActor().GoalPosition = ItemPosition.GetPositionInFront(Actor.Rotation);
         } else {
            RoomItem.changeState();
            RoomItem.serializeUpdate(Actor.CurrentRoom);
            RoomItem.saveState();
         }
      }

   }

   public void onCycle(Object Item) {}
}
