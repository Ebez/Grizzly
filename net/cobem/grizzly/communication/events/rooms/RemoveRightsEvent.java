package net.cobem.grizzly.communication.events.rooms;

import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.communication.EventRequest;
import net.cobem.grizzly.communication.GameEvent;
import net.cobem.grizzly.communication.composers.rooms.SendRoomRightsComposer;
import net.cobem.grizzly.communication.composers.rooms.SendRoomWhisperComposer;
import net.cobem.grizzly.habbohotel.sessions.Session;

public class RemoveRightsEvent implements GameEvent {

   public void compose(Session Session, EventRequest Request) {
      Request.readInt();
      int UserID = Request.readInt().intValue();
      if(Session.getActor().CurrentRoom.getRightHolders().contains(Integer.valueOf(UserID))) {
         Session.getActor().CurrentRoom.getRightHolders().remove(UserID);
         Grizzly.getStorage().execute("DELETE FROM server_room_rights WHERE room = \'" + Session.getActor().CurrentRoom.ID + "\',  user = \'" + UserID + "\'");
         Session User = Grizzly.getHabboHotel().getSessions().getByID(UserID);
         if(User != null) {
            User.sendResponse(SendRoomRightsComposer.compose(0));
            Session.getActor().addStatus("flatctrl 0", "", true);
            User.sendResponse(SendRoomWhisperComposer.compose(Session.getHabbo().getID(), "I removed your rights!", 0));
         }
      }

   }
}
