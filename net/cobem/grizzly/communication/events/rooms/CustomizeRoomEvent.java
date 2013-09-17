package net.cobem.grizzly.communication.events.rooms;

import net.cobem.grizzly.communication.EventRequest;
import net.cobem.grizzly.communication.EventResponse;
import net.cobem.grizzly.communication.GameEvent;
import net.cobem.grizzly.communication.HeaderLibrary;
import net.cobem.grizzly.communication.composers.rooms.SendRoomUserRightsComposer;
import net.cobem.grizzly.habbohotel.rooms.Room;
import net.cobem.grizzly.habbohotel.sessions.Session;

public class CustomizeRoomEvent implements GameEvent {

   public void compose(Session Session, EventRequest Request) {
      if(Session.getActor().CurrentRoom.Owner == Session.getHabbo().getID() || Session.getHabbo().getRank() >= 6) {
         Room Room = Session.getActor().CurrentRoom;
         EventResponse Message = new EventResponse();
         Message.Initialize(HeaderLibrary.SendSettingsDialogEvent);
         Message.addInt(Integer.valueOf(Room.ID));
         Message.addString(Room.Title);
         Message.addString(Room.Description);
         Message.addInt(Integer.valueOf(Room.State));
         Message.addInt(Integer.valueOf(0));
         Message.addInt(Integer.valueOf(50));
         Message.addInt(Integer.valueOf(50));
         Message.addInt(Integer.valueOf(0));
         Message.addInt(Integer.valueOf(Room.getRightHolders().size()));
         Message.addInt(Integer.valueOf(1));
         Message.addInt(Integer.valueOf(0));
         Message.addInt(Integer.valueOf(0));
         Message.addInt(Integer.valueOf(0));
         Message.addInt(Integer.valueOf(1));
         Message.addInt(Integer.valueOf(1));
         Message.addInt(Integer.valueOf(0));
         Message.addInt(Integer.valueOf(0));
         Message.addInt(Integer.valueOf(0));
         Session.sendResponse(Message);
         Session.sendResponse(SendRoomUserRightsComposer.compose(Room));
      }
   }
}
