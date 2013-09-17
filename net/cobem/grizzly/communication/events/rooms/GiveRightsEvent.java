package net.cobem.grizzly.communication.events.rooms;

import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.communication.EventRequest;
import net.cobem.grizzly.communication.GameEvent;
import net.cobem.grizzly.communication.composers.rooms.SendRoomRightsComposer;
import net.cobem.grizzly.communication.composers.rooms.SendRoomWhisperComposer;
import net.cobem.grizzly.habbohotel.sessions.Session;

public class GiveRightsEvent implements GameEvent {

   public void compose(Session Session, EventRequest Request) {
      int UserID = Request.readInt().intValue();
      if(!Session.getActor().CurrentRoom.getRightHolders().contains(Integer.valueOf(UserID))) {
         Session.getActor().CurrentRoom.getRightHolders().add(Integer.valueOf(UserID));
         Grizzly.getStorage().execute("INSERT INTO server_room_rights (room, user) VALUES (\'" + Session.getActor().CurrentRoom.ID + "\', \'" + UserID + "\')");
         Session User = Grizzly.getHabboHotel().getSessions().getByID(UserID);
         if(User != null) {
            User.sendResponse(SendRoomRightsComposer.compose(1));
            Session.getActor().addStatus("flatctrl 1", "", true);
            User.sendResponse(SendRoomWhisperComposer.compose(Session.getHabbo().getID(), "I gave you rights!", 0));
         }
      }

   }
}
