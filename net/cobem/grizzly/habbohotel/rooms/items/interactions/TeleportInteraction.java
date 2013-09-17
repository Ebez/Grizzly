package net.cobem.grizzly.habbohotel.rooms.items.interactions;

import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.habbohotel.pathfinding.Position;
import net.cobem.grizzly.habbohotel.rooms.Room;
import net.cobem.grizzly.habbohotel.rooms.actors.Actor;
import net.cobem.grizzly.habbohotel.rooms.items.FloorItem;
import net.cobem.grizzly.habbohotel.rooms.items.interactions.Interaction;
import net.cobem.grizzly.habbohotel.sessions.Session;

public class TeleportInteraction implements Interaction {

   public void onPlace(Session Session, Object Item) {}

   public void onRemove(Session Session, Object Item) {}

   public void onTrigger(Session Session, Object Item, int Request) {
      Actor Actor = Session.getActor();
      FloorItem RoomItem = (FloorItem)Item;
      if(RoomItem.Interactor == null) {
         Position ItemPosition;
         try {
            ItemPosition = new Position(RoomItem.X, RoomItem.Y, (double)RoomItem.Height.intValue());
         } catch (Exception var8) {
            return;
         }

         if(Actor.CurrentPosition.TilesTouching(ItemPosition)) {
            RoomItem.Interactor = Session;
            RoomItem.UpdateNeeded = true;
            RoomItem.UpdateCounter = 3;
         }

      }
   }

   public void onCycle(Object Item) {
      FloorItem RoomItem = (FloorItem)Item;
      Session Interactor = RoomItem.Interactor;
      Position Position = new Position(RoomItem.X, RoomItem.Y, (double)RoomItem.Height.intValue());
      Room CurrentRoom = Grizzly.getHabboHotel().getRooms().getByID(RoomItem.Room);
      if(RoomItem.UpdateCounter == 4) {
         RoomItem.ExtraData = "0";
         RoomItem.serializeUpdate(CurrentRoom);
         RoomItem.UpdateCounter = 0;
      }

      if(RoomItem.UpdateCounter == 1) {
         RoomItem.ExtraData = "0";
         RoomItem.serializeUpdate(CurrentRoom);
         Interactor.getActor().CanWalk = true;
         Interactor.getActor().GoalPosition = Position.GetPositionInFront(RoomItem.Rotation);
         Interactor.getActor().IsMoving = true;
         RoomItem.Interactor = null;
      } else if(RoomItem.UpdateCounter == 2) {
         RoomItem.ExtraData = "2";
         RoomItem.serializeUpdate(CurrentRoom);
      } else {
         int Partner = Grizzly.getHabboHotel().getFurniture().getTeleporters().getPair(RoomItem.ID);
         if(Partner == 0) {
            RoomItem.UpdateCounter = 1;
         } else {
            FloorItem Pair;
            if(CurrentRoom.getItemByID(Partner) != null) {
               Pair = CurrentRoom.getItemByID(Partner);
               if(Pair == null) {
                  RoomItem.UpdateCounter = 1;
                  return;
               }

               if(Pair.Interactor != null) {
                  return;
               }

               Interactor.getActor().stopMoving(true);
               Interactor.getActor().move(new Position(Pair.X, Pair.Y, (double)Pair.Height.intValue()), Pair.Rotation);
               RoomItem.ExtraData = "2";
               RoomItem.serializeUpdate(CurrentRoom);
               Pair.Interactor = Interactor;
               Pair.UpdateCounter = 2;
               Pair.UpdateNeeded = true;
               RoomItem.UpdateCounter = 4;
               RoomItem.Interactor = null;
            } else {
               Pair = new FloorItem(Partner);
               if(Pair.ID == 0) {
                  RoomItem.UpdateCounter = 1;
                  return;
               }

               if(Pair.Interactor != null) {
                  return;
               }

               if(Grizzly.getHabboHotel().getRooms().getByID(Pair.Room) == null) {
                  try {
                     Grizzly.getHabboHotel().getRooms().getRooms().put(Integer.valueOf(Pair.Room), new Room(Pair.Room));
                  } catch (Exception var10) {
                     RoomItem.Interactor = null;
                     RoomItem.UpdateCounter = 0;
                     return;
                  }
               }

               Room PairRoom = Grizzly.getHabboHotel().getRooms().getByID(Pair.Room);
               Pair = PairRoom.getItemByID(Partner);
               RoomItem.ExtraData = "2";
               RoomItem.serializeUpdate(CurrentRoom);
               Interactor.getActor().OverridePosition = new Position(Pair.X, Pair.Y, (double)Pair.Height.intValue());
               Interactor.travel(PairRoom.ID);
               Pair.Interactor = Interactor;
               Pair.UpdateCounter = 2;
               Pair.UpdateNeeded = true;
               RoomItem.UpdateCounter = 4;
               RoomItem.Interactor = null;
            }

         }
      }
   }
}
