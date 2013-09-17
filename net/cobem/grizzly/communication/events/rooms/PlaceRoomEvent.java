package net.cobem.grizzly.communication.events.rooms;

import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.communication.EventRequest;
import net.cobem.grizzly.communication.GameEvent;
import net.cobem.grizzly.communication.composers.inventory.DisposeItemFromInventoryComposer;
import net.cobem.grizzly.communication.composers.rooms.SendRoomPapersComposer;
import net.cobem.grizzly.communication.events.user.InitializeInventoryEvent;
import net.cobem.grizzly.habbohotel.rooms.Room;
import net.cobem.grizzly.habbohotel.sessions.Session;
import net.cobem.grizzly.habbohotel.users.items.InventoryItem;

public class PlaceRoomEvent implements GameEvent {

   public void compose(Session Session, EventRequest Request) {
      Room Room = Session.getActor().CurrentRoom;
      if(Room.Owner == Session.getHabbo().getID()) {
         int ID = Request.readInt().intValue();
         InventoryItem Item = (InventoryItem)Session.getHabbo().getItems().getWalls().get(Integer.valueOf(ID));
         String Extra = Item.Extra;
         if(Item.getBase().ItemTitle.contains("wallpaper")) {
            Grizzly.getStorage().execute("UPDATE server_rooms SET wallpaper = \'" + Extra + "\' WHERE id = \'" + Room.ID + "\'");
            Room.sendMessage(SendRoomPapersComposer.compose("wallpaper", Extra));
            Session.getHabbo().getItems().remove(ID, true);
            Session.sendResponse(DisposeItemFromInventoryComposer.compose(ID));
            (new InitializeInventoryEvent()).compose(Session, Request);
         } else if(Item.getBase().ItemTitle.contains("a2")) {
            Grizzly.getStorage().execute("UPDATE server_rooms SET floor = \'" + Extra + "\' WHERE id = \'" + Room.ID + "\'");
            Room.sendMessage(SendRoomPapersComposer.compose("floor", Extra));
            Session.getHabbo().getItems().remove(ID, true);
            Session.sendResponse(DisposeItemFromInventoryComposer.compose(ID));
            (new InitializeInventoryEvent()).compose(Session, Request);
         } else if(Item.getBase().ItemTitle.contains("landscape")) {
            Grizzly.getStorage().execute("UPDATE server_rooms SET outside = \'" + Extra + "\' WHERE id = \'" + Room.ID + "\'");
            Room.sendMessage(SendRoomPapersComposer.compose("landscape", Extra));
            Session.getHabbo().getItems().remove(ID, true);
            Session.sendResponse(DisposeItemFromInventoryComposer.compose(ID));
            (new InitializeInventoryEvent()).compose(Session, Request);
         }

      }
   }
}
