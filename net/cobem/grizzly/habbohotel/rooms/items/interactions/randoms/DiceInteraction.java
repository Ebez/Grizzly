package net.cobem.grizzly.habbohotel.rooms.items.interactions.randoms;

import java.security.SecureRandom;
import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.habbohotel.pathfinding.Position;
import net.cobem.grizzly.habbohotel.rooms.actors.Actor;
import net.cobem.grizzly.habbohotel.rooms.items.FloorItem;
import net.cobem.grizzly.habbohotel.rooms.items.interactions.Interaction;
import net.cobem.grizzly.habbohotel.sessions.Session;

public class DiceInteraction implements Interaction {

   public void onPlace(Session Session, Object Item) {}

   public void onRemove(Session Session, Object Item) {}

   public void onTrigger(Session Session, Object Item, int Request) {
      if(Session.getActor().CurrentRoom.OwnerByName.equals(Session.getHabbo().getUsername()) && Session.getActor().CurrentRoom.getRightHolders().contains(Integer.valueOf(Session.getHabbo().getID())) && Session.getHabbo().getRank() > 4) {
         Actor Actor = Session.getActor();
         FloorItem RoomItem = (FloorItem)Item;

         Position ItemPosition;
         try {
            ItemPosition = new Position(RoomItem.X, RoomItem.Y, (double)RoomItem.Height.intValue());
         } catch (Exception var8) {
            return;
         }

         if(!Actor.CurrentPosition.TilesTouching(ItemPosition)) {
            Session.getActor().GoalPosition = ItemPosition.GetPositionInFront(Actor.Rotation);
         } else {
            if(Request != 0) {
               RoomItem.ExtraData = "-1";
               RoomItem.UpdateCounter = 3;
            } else {
               RoomItem.ExtraData = "0";
            }

            RoomItem.serializeUpdate(Session);
            RoomItem.saveState();
            RoomItem.UpdateNeeded = true;
         }

      }
   }

   public void onCycle(Object Item) {
      FloorItem RoomItem = (FloorItem)Item;
      if(RoomItem.UpdateCounter == 1) {
         RoomItem.ExtraData = String.valueOf((new SecureRandom()).nextInt(6) + 1);
         RoomItem.serializeUpdate(Grizzly.getHabboHotel().getRooms().getByID(RoomItem.Room));
         RoomItem.saveState();
      }

   }
}
