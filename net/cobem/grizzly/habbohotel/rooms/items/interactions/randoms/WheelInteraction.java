package net.cobem.grizzly.habbohotel.rooms.items.interactions.randoms;

import java.security.SecureRandom;
import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.habbohotel.rooms.actors.Actor;
import net.cobem.grizzly.habbohotel.rooms.items.WallItem;
import net.cobem.grizzly.habbohotel.rooms.items.interactions.Interaction;
import net.cobem.grizzly.habbohotel.sessions.Session;

public class WheelInteraction implements Interaction {

   public void onPlace(Session Session, Object Item) {
      if(Session.getActor().CurrentRoom.OwnerByName.equals(Session.getHabbo().getUsername()) && Session.getActor().CurrentRoom.getRightHolders().contains(Integer.valueOf(Session.getHabbo().getID())) && Session.getHabbo().getRank() > 4) {
         WallItem RoomItem = (WallItem)Item;
         RoomItem.ExtraData = "-1";
         RoomItem.UpdateCounter = 10;
         RoomItem.UpdateNeeded = true;
      }
   }

   public void onRemove(Session Session, Object Item) {}

   public void onTrigger(Session Session, Object Item, int Request) {
      Actor Actor = Session.getActor();
      WallItem RoomItem = (WallItem)Item;
      Actor.CurrentRoom.getRightHolders().contains(Integer.valueOf(Session.getHabbo().getID()));
      if(RoomItem.ExtraData != "-1") {
         RoomItem.ExtraData = "-1";
         RoomItem.saveState();
         RoomItem.UpdateCounter = 10;
         RoomItem.UpdateNeeded = true;
      }

   }

   public void onCycle(Object Item) {
      WallItem RoomItem = (WallItem)Item;
      RoomItem.ExtraData = String.valueOf((new SecureRandom()).nextInt(10) + 1);
      RoomItem.serializeUpdate(Grizzly.getHabboHotel().getRooms().getByID(RoomItem.Room));
      RoomItem.saveState();
   }
}
