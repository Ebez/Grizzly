package net.cobem.grizzly.habbohotel.rooms.items.interactions;

import net.cobem.grizzly.habbohotel.rooms.items.FloorItem;
import net.cobem.grizzly.habbohotel.rooms.items.interactions.Interaction;
import net.cobem.grizzly.habbohotel.sessions.Session;

public class GateInteraction implements Interaction {

   public void onPlace(Session Session, Object Item) {}

   public void onRemove(Session Session, Object Item) {}

   public void onTrigger(Session Session, Object Item, int Request) {
      if(Session.getActor().CurrentRoom.OwnerByName.equals(Session.getHabbo().getUsername()) && Session.getActor().CurrentRoom.getRightHolders().contains(Integer.valueOf(Session.getHabbo().getID())) && Session.getHabbo().getRank() > 4) {
         FloorItem RoomItem = (FloorItem)Item;
         RoomItem.changeState();
         RoomItem.saveState();
         RoomItem.serializeUpdate(Session);
      }
   }

   public void onCycle(Object Item) {}
}
