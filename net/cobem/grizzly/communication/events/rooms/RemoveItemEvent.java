package net.cobem.grizzly.communication.events.rooms;

import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.communication.EventRequest;
import net.cobem.grizzly.communication.EventResponse;
import net.cobem.grizzly.communication.GameEvent;
import net.cobem.grizzly.communication.composers.inventory.UpdateUserInventoryComposer;
import net.cobem.grizzly.communication.composers.rooms.RemoveFloorItemComposer;
import net.cobem.grizzly.communication.composers.rooms.RemoveWallItemComposer;
import net.cobem.grizzly.communication.events.user.InitializeInventoryEvent;
import net.cobem.grizzly.habbohotel.rooms.items.FloorItem;
import net.cobem.grizzly.habbohotel.rooms.items.WallItem;
import net.cobem.grizzly.habbohotel.sessions.Session;

public class RemoveItemEvent implements GameEvent {

   public void compose(Session Session, EventRequest Request) {
      if(Session.getActor().CurrentRoom.Owner == Session.getHabbo().getID() || Session.getHabbo().getRank() >= 6) {
         Request.readInt();
         int ID = Request.readInt().intValue();
         new EventResponse();
         if(Session.getActor().CurrentRoom.getFloors().containsKey(Integer.valueOf(ID))) {
            FloorItem Item = (FloorItem)Session.getActor().CurrentRoom.getFloors().get(Integer.valueOf(ID));
            Session.getActor().CurrentRoom.sendMessage(RemoveFloorItemComposer.compose(ID, Session.getHabbo().getID()));
            Session.getHabbo().getItems().add(Item.getBase(), false, Item.ExtraData);
            Grizzly.getStorage().execute("UPDATE server_items SET room = \'0\' WHERE id = \'" + ID + "\'");
            Session.getActor().CurrentRoom.getFloors().remove(Integer.valueOf(ID));
            Session.getActor().CurrentRoom.getWiredUtility().removeFurni(Item);
         } else if(Session.getActor().CurrentRoom.getWalls().containsKey(Integer.valueOf(ID))) {
            WallItem Item1 = (WallItem)Session.getActor().CurrentRoom.getWalls().get(Integer.valueOf(ID));
            Session.getActor().CurrentRoom.sendMessage(RemoveWallItemComposer.compose(Item1.ID, Integer.parseInt(Item1.ExtraData)));
            Session.getHabbo().getItems().add(Item1.getBase(), false, Item1.ExtraData);
            Grizzly.getStorage().execute("UPDATE server_items SET room = \'0\' WHERE id = \'" + ID + "\'");
            Session.getActor().CurrentRoom.getWalls().remove(Integer.valueOf(ID));
         }

         Session.sendResponse(UpdateUserInventoryComposer.compose());
         (new InitializeInventoryEvent()).compose(Session, Request);
         Session.getActor().CurrentRoom.getMap().ReGenerateCollisionMap();
      }
   }
}
