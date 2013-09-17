package net.cobem.grizzly.habbohotel.rooms.items.interactions.randoms;

import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.habbohotel.pathfinding.Position;
import net.cobem.grizzly.habbohotel.rooms.actors.Actor;
import net.cobem.grizzly.habbohotel.rooms.items.FloorItem;
import net.cobem.grizzly.habbohotel.rooms.items.interactions.Interaction;
import net.cobem.grizzly.habbohotel.sessions.Session;

public class VendingInteraction implements Interaction {

   public void onPlace(Session Session, Object Item) {}

   public void onRemove(Session Session, Object Item) {}

   public void onTrigger(Session Session, Object Item, int Request) {
      Actor Actor = Session.getActor();
      FloorItem RoomItem = (FloorItem)Item;
      if(RoomItem.Interactor == null && RoomItem.getBase().Vendors.size() != 0 && RoomItem.ExtraData != "-1") {
         Position ItemPosition;
         try {
            ItemPosition = new Position(RoomItem.X, RoomItem.Y, (double)RoomItem.Height.intValue());
         } catch (Exception var8) {
            return;
         }

         if(!Actor.CurrentPosition.TilesTouching(ItemPosition)) {
            Actor.GoalPosition = ItemPosition.GetPositionInFront(Actor.Rotation);
         } else {
            RoomItem.Interactor = Session;
            Session.getActor().Rotation = RoomItem.Rotation;
            RoomItem.UpdateCounter = 2;
            RoomItem.ExtraData = "1";
            RoomItem.serializeUpdate(Session);
            RoomItem.UpdateNeeded = true;
         }

      }
   }

   public void onCycle(Object Item) {
      FloorItem RoomItem = (FloorItem)Item;
      if(RoomItem.Interactor == null) {
         RoomItem.UpdateCounter = 0;
         RoomItem.UpdateNeeded = false;
         RoomItem.serializeUpdate(Grizzly.getHabboHotel().getRooms().getByID(RoomItem.Room));
         RoomItem.saveState();
      } else {
         RoomItem.Interactor.getActor().carry(RoomItem.getBase().getRandomVendor());
         RoomItem.Interactor = null;
         RoomItem.ExtraData = "0";
      }
   }
}
