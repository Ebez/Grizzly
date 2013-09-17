package net.cobem.grizzly.habbohotel.rooms.items.interactions;

import net.cobem.grizzly.habbohotel.rooms.actors.Actor;
import net.cobem.grizzly.habbohotel.rooms.items.FloorItem;
import net.cobem.grizzly.habbohotel.rooms.items.interactions.Interaction;
import net.cobem.grizzly.habbohotel.sessions.Session;

public class BedInteraction implements Interaction {

   public void onPlace(Session Session, Object Item) {}

   public void onRemove(Session Session, Object Item) {}

   public void onTrigger(Session Session, Object Item, int Request) {
      Actor Actor = Session.getActor();
      FloorItem RoomItem = (FloorItem)Item;
      Actor.Rotation = RoomItem.Rotation;
      Actor.removeStatus("sit", Boolean.valueOf(false));
      Actor.removeStatus("mv", Boolean.valueOf(false));
      Actor.addStatus("lay", RoomItem.getBase().StackHeight, true);
   }

   public void onCycle(Object Item) {}
}
