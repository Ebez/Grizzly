package net.cobem.grizzly.habbohotel.rooms.items.interactions.wired.effects;

import java.util.ArrayList;
import java.util.Iterator;
import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.communication.composers.rooms.UpdateFloorItemComposer;
import net.cobem.grizzly.communication.composers.wired.SendWiredEffectDialogExtraComposer;
import net.cobem.grizzly.habbohotel.pathfinding.Position;
import net.cobem.grizzly.habbohotel.rooms.Room;
import net.cobem.grizzly.habbohotel.rooms.items.FloorItem;
import net.cobem.grizzly.habbohotel.rooms.items.interactions.Interaction;
import net.cobem.grizzly.habbohotel.rooms.models.SquareState;
import net.cobem.grizzly.habbohotel.sessions.Session;

public class WiredMoveAndRotateFurniInteraction implements Interaction {

   public void onPlace(Session Session, Object Item) {}

   public void onRemove(Session Session, Object Item) {}

   public void onTrigger(Session Session, Object Item, int Request) {
      String[] Split;
      if(Request == 2) {
         Room RoomItem = Session.getActor().CurrentRoom;
         FloorItem Furnis = (FloorItem)Item;
         if(!Furnis.ExtraData.equals("") || Furnis.ExtraData.length() >= 2) {
            Split = Furnis.ExtraData.split("_");
            if(Split.length < 2) {
               return;
            }

            String[] Furni = Split[0].split(";");
            String[] Furnis1 = Split[1].split(";");
            String[] var12 = Furnis1;
            int var11 = Furnis1.length;

            for(int var10 = 0; var10 < var11; ++var10) {
               String ItemID = var12[var10];
               FloorItem item = RoomItem.getItemByID((new Integer(ItemID)).intValue());
               int ID = item.ID;
               Position NewPosition = RoomItem.getWiredUtility().calculateMovement((new Integer(Furni[0])).intValue(), new Position(item.X, item.Y, (double)item.Height.floatValue()));
               int NewRotation = RoomItem.getWiredUtility().calculateRotation((new Integer(Furni[1])).intValue(), item.Rotation);
               if(RoomItem.getModel().Squares[NewPosition.X][NewPosition.Y] != SquareState.CLOSED) {
                  item.X = NewPosition.X;
                  item.Y = NewPosition.Y;
                  item.Rotation = NewRotation;
                  Float Z = Float.valueOf((float)RoomItem.getModel().getTileHeight(NewPosition.X, NewPosition.Y));
                  Iterator var19 = RoomItem.getItemsByPosition(new Position(NewPosition.X, NewPosition.Y, 0.0D)).values().iterator();

                  while(var19.hasNext()) {
                     FloorItem mPosition = (FloorItem)var19.next();
                     if(mPosition.ID != item.ID) {
                        if(!mPosition.getBase().Stackable.booleanValue()) {
                           return;
                        }

                        if(mPosition.Height.floatValue() != 0.0F) {
                           if(mPosition.Height.floatValue() > Z.floatValue()) {
                              Z = Float.valueOf(mPosition.getBase().StackHeight.floatValue() + mPosition.Height.floatValue());
                           }
                        } else {
                           Z = mPosition.getBase().StackHeight;
                        }
                     }
                  }

                  item.Height = Z;
                  var19 = RoomItem.getPositions().values().iterator();

                  while(var19.hasNext()) {
                     Position var26 = (Position)var19.next();
                     if(var26.X == NewPosition.X && var26.Y == NewPosition.Y) {
                        ;
                     }
                  }

                  Grizzly.getStorage().execute("UPDATE server_items SET x = \'" + item.X + "\', y = \'" + item.Y + "\', rotation = \'" + item.Rotation + "\', height = \'" + item.Height + "\' WHERE id = \'" + ID + "\'");
                  RoomItem.getFloors().remove(Integer.valueOf(ID));
                  RoomItem.getFloors().put(Integer.valueOf(ID), new FloorItem(ID));
                  RoomItem.getMap().ReGenerateCollisionMap();
                  RoomItem.sendMessage(UpdateFloorItemComposer.compose(ID, item, RoomItem.Owner));
               }
            }
         }
      } else {
         FloorItem var20 = (FloorItem)Item;
         ArrayList var21 = new ArrayList();
         Split = var20.ExtraData.split("_");
         if(Split.length >= 2) {
            String[] var25;
            int var24 = (var25 = Split[1].split(";")).length;

            for(int var23 = 0; var23 < var24; ++var23) {
               String var22 = var25[var23];
               var21.add(Session.getActor().CurrentRoom.getItemByID((new Integer(var22)).intValue()));
            }
         }

         Session.sendResponse(SendWiredEffectDialogExtraComposer.compose(5, var20, var21, var20.ExtraData, 2, 0, 0, 0, 4));
      }

   }

   public void onCycle(Object Item) {}
}
