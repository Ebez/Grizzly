package net.cobem.grizzly.communication.events.rooms;

import java.util.Iterator;
import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.communication.EventRequest;
import net.cobem.grizzly.communication.GameEvent;
import net.cobem.grizzly.communication.composers.inventory.DisposeItemFromInventoryComposer;
import net.cobem.grizzly.communication.composers.rooms.SendFloorItemToRoomComposer;
import net.cobem.grizzly.communication.composers.rooms.SendWallItemToRoomComposer;
import net.cobem.grizzly.communication.events.user.InitializeInventoryEvent;
import net.cobem.grizzly.habbohotel.pathfinding.Position;
import net.cobem.grizzly.habbohotel.rooms.items.FloorItem;
import net.cobem.grizzly.habbohotel.rooms.items.WallItem;
import net.cobem.grizzly.habbohotel.sessions.Session;
import net.cobem.grizzly.habbohotel.users.items.InventoryItem;

public class PlaceItemEvent implements GameEvent {

   public void compose(Session Session, EventRequest Request) {
      if(Session.getActor().CurrentRoom.Owner == Session.getHabbo().getID() || Session.getHabbo().getRank() >= 6) {
         String PlaceData = Request.readString();
         String[] Bits = PlaceData.split(" ");
         Integer ID = Integer.valueOf(Integer.parseInt(Bits[0]));
         if(Bits[1].startsWith(":")) {
            String X = Bits[1] + " " + Bits[2] + " " + Bits[3];
            InventoryItem Y = (InventoryItem)Session.getHabbo().getItems().getWalls().get(ID);

            try {
               Grizzly.getStorage().execute("UPDATE server_items SET room = \'" + Session.getActor().CurrentRoom.ID + "\', base = \'" + Y.getBase().ID + "\', position = \'" + X + "\', extra = \'0\', floor = \'0\' WHERE id = \'" + ID + "\'");
            } catch (Exception var14) {
               ;
            }

            int Rotation = Grizzly.getStorage().getLastEntry("server_items", "room = \'" + Session.getActor().CurrentRoom.ID + "\'");
            Session.getActor().CurrentRoom.sendMessage(SendWallItemToRoomComposer.compose(Rotation, Y.getBase().Sprite, X, Session.getActor().CurrentRoom));
            Session.getHabbo().getItems().remove(ID.intValue(), false);
            Session.sendResponse(DisposeItemFromInventoryComposer.compose(ID.intValue()));
            (new InitializeInventoryEvent()).compose(Session, Request);
            Session.getActor().CurrentRoom.generateItem(ID.intValue());
            WallItem Height = null;
            Iterator mItem = Session.getActor().CurrentRoom.getWalls().values().iterator();

            while(mItem.hasNext()) {
               WallItem Item = (WallItem)mItem.next();
               if(Item.Position.equals(X)) {
                  Height = Item;
                  break;
               }
            }

            if(Height != null) {
               Grizzly.getHabboHotel().getInteractions().onPlace(Height.getBase().Interaction, Session, Height);
            }
         } else {
            Integer X1 = Integer.valueOf(Integer.parseInt(Bits[1]));
            Integer Y1 = Integer.valueOf(Integer.parseInt(Bits[2]));
            Integer Rotation1 = Integer.valueOf(Integer.parseInt(Bits[3]));
            Float Height1 = Float.valueOf((float)Session.getActor().CurrentRoom.getModel().getTileHeight(X1.intValue(), Y1.intValue()));
            if(Session.getActor().CurrentRoom.nonStackableTile(new Position(X1.intValue(), Y1.intValue(), 0.0D))) {
               return;
            }

            Height1 = Float.valueOf(Height1.floatValue() + (float)Session.getActor().CurrentRoom.getTopHeight(new Position(X1.intValue(), Y1.intValue(), 0.0D), (FloorItem)null));
            Grizzly.write(Height1);

            try {
               InventoryItem Item1 = (InventoryItem)Session.getHabbo().getItems().getFloors().get(ID);
               Grizzly.getStorage().execute("UPDATE server_items SET room = \'" + Session.getActor().CurrentRoom.ID + "\', base = \'" + Item1.getBase().ID + "\', x = \'" + X1 + "\', y = \'" + Y1 + "\', extra = \' \', floor = \'1\', rotation = \'" + Rotation1 + "\', height = \'" + Height1 + "\' WHERE id = \'" + ID + "\'");
               Session.getActor().CurrentRoom.sendMessage(SendFloorItemToRoomComposer.compose(ID.intValue(), Item1.getBase().Sprite, X1.intValue(), Y1.intValue(), Rotation1.intValue(), Float.toString(Height1.floatValue()), Session.getActor().CurrentRoom));
               Session.getHabbo().getItems().remove(ID.intValue(), false);
               Session.sendResponse(DisposeItemFromInventoryComposer.compose(ID.intValue()));
               (new InitializeInventoryEvent()).compose(Session, Request);
               Session.getActor().CurrentRoom.generateItem(ID.intValue());
               if(Session.getActor().CurrentRoom.getItemsByPosition(new Position(X1.intValue(), Y1.intValue(), (double)Height1.intValue())) != null) {
                  FloorItem mItem1 = null;
                  Iterator var13 = Session.getActor().CurrentRoom.getItemsByPosition(new Position(X1.intValue(), Y1.intValue(), 0.0D)).values().iterator();

                  while(var13.hasNext()) {
                     FloorItem Furni = (FloorItem)var13.next();
                     if(Furni.Height.equals(Height1)) {
                        mItem1 = Furni;
                        break;
                     }
                  }

                  if(mItem1 != null) {
                     Grizzly.getHabboHotel().getInteractions().onPlace(mItem1.getBase().Interaction, Session, mItem1);
                  }
               }
            } catch (Exception var15) {
               ;
            }
         }

      }
   }
}
