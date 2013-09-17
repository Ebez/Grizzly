package net.cobem.grizzly.communication.events.rooms;

import java.util.Iterator;
import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.communication.EventRequest;
import net.cobem.grizzly.communication.GameEvent;
import net.cobem.grizzly.communication.composers.rooms.UpdateFloorItemComposer;
import net.cobem.grizzly.habbohotel.pathfinding.Position;
import net.cobem.grizzly.habbohotel.rooms.Room;
import net.cobem.grizzly.habbohotel.rooms.items.FloorItem;
import net.cobem.grizzly.habbohotel.sessions.Session;

public class ItemPositionChangeEvent implements GameEvent {

   public void compose(Session Session, EventRequest Request) {
      if(!Session.getActor().CurrentRoom.getRightHolders().contains(Integer.valueOf(Session.getHabbo().getID())) && Session.getHabbo().getRank() < 5) {
         Grizzly.write(Session.getHabbo().getUsername() + " tried to move something!");
      } else {
         Room mRoom = Session.getActor().CurrentRoom;
         int ID = Request.readInt().intValue();
         int X = Request.readInt().intValue();
         int Y = Request.readInt().intValue();
         int Rotation = Request.readInt().intValue();
         FloorItem Item = (FloorItem)mRoom.getFloors().get(Integer.valueOf(ID));
         if(Item != null) {
            if(!mRoom.nonStackableTile(new Position(X, Y, 0.0D)) || mRoom.getItemsByPosition(new Position(X, Y, 0.0D)).size() == 1 && mRoom.getItemByPosition(new Position(X, Y, 0.0D)).ID == ID) {
               Iterator var10 = mRoom.getPositions().values().iterator();

               Position Z;
               do {
                  if(!var10.hasNext()) {
                     Item.X = X;
                     Item.Y = Y;
                     Float Z1 = Float.valueOf((float)mRoom.getModel().getTileHeight(X, Y));
                     Item.Height = Float.valueOf((float)mRoom.getTopHeight(new Position(X, Y, 0.0D), Item));
                     Item.Rotation = Rotation;
                     Grizzly.getStorage().execute("UPDATE server_items SET x = \'" + Item.X + "\', y = \'" + Item.Y + "\', rotation = \'" + Item.Rotation + "\', height = \'" + Item.Height + "\' WHERE id = \'" + ID + "\'");
                     mRoom.getFloors().remove(Integer.valueOf(ID));
                     mRoom.getFloors().put(Integer.valueOf(ID), new FloorItem(ID));
                     mRoom.getMap().ReGenerateCollisionMap();
                     Session.getActor().CurrentRoom.sendMessage(UpdateFloorItemComposer.compose(ID, Item, Session.getActor().CurrentRoom.Owner));
                     return;
                  }

                  Z = (Position)var10.next();
               } while(Z.X != X || Z.Y != Y);

            }
         }
      }
   }
}
