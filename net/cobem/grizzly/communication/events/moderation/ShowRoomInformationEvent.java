package net.cobem.grizzly.communication.events.moderation;

import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.communication.EventRequest;
import net.cobem.grizzly.communication.EventResponse;
import net.cobem.grizzly.communication.GameEvent;
import net.cobem.grizzly.communication.HeaderLibrary;
import net.cobem.grizzly.habbohotel.rooms.Room;
import net.cobem.grizzly.habbohotel.sessions.Session;

public class ShowRoomInformationEvent implements GameEvent {

   public void compose(Session Session, EventRequest Request) {
      EventResponse Message = new EventResponse();
      int ID = Request.readInt().intValue();
      boolean RoomWasntLoaded = false;
      Room mRoom;
      if(Grizzly.getHabboHotel().getRooms().getByID(ID) == null) {
         RoomWasntLoaded = true;
         mRoom = new Room(ID);
      } else {
         mRoom = Grizzly.getHabboHotel().getRooms().getByID(ID);
      }

      Message.Initialize(HeaderLibrary.SendModToolsRoomInformationEvent);
      Message.addInt(Integer.valueOf(mRoom.ID));
      Message.addInt(Integer.valueOf(RoomWasntLoaded?0:mRoom.getParty().size()));
      Message.addBool(Boolean.valueOf(mRoom.Owner == Session.getHabbo().getID()));
      Message.addInt(Integer.valueOf(mRoom.Owner));
      Message.addString(mRoom.OwnerByName);
      Message.addBool(Boolean.valueOf(false));
      Message.addBool(Boolean.valueOf(true));
      Message.addString(mRoom.Title);
      Message.addString(mRoom.Description);
      Message.addInt(Integer.valueOf(0));
      Message.addInt(Integer.valueOf(0));
      Message.addInt(Integer.valueOf(0));
      Message.addInt(Integer.valueOf(0));
      Session.sendResponse(Message);
   }
}
