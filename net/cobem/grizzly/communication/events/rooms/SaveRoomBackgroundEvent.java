package net.cobem.grizzly.communication.events.rooms;

import net.cobem.grizzly.communication.EventRequest;
import net.cobem.grizzly.communication.GameEvent;
import net.cobem.grizzly.communication.composers.rooms.SendFloorItemsComposer;
import net.cobem.grizzly.communication.composers.rooms.SendWallItemsComposer;
import net.cobem.grizzly.habbohotel.sessions.Session;

public class SaveRoomBackgroundEvent implements GameEvent {

   public void compose(Session Session, EventRequest Request) {
      int ID = Request.readInt().intValue();
      int Data = Request.readInt().intValue();
      String BrandData = "state   0";

      for(int i = 1; i <= Data; ++i) {
         BrandData = BrandData + "   " + Request.readString();
      }

      Session.getActor().CurrentRoom.getItemByID(ID).ExtraData = BrandData;
      Session.getActor().CurrentRoom.getItemByID(ID).serializeUpdate(Session.getActor().CurrentRoom);
      Session.getActor().CurrentRoom.getItemByID(ID).saveState();
      Session.getActor().CurrentRoom.sendMessage(SendFloorItemsComposer.compose(Session.getActor().CurrentRoom.Owner, Session.getActor().CurrentRoom.OwnerByName, Session.getActor().CurrentRoom.getFloors()));
      Session.getActor().CurrentRoom.sendMessage(SendWallItemsComposer.compose(Session.getActor().CurrentRoom.Owner, Session.getActor().CurrentRoom.OwnerByName, Session.getActor().CurrentRoom.getWalls()));
   }
}
