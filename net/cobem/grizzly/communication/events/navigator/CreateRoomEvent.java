package net.cobem.grizzly.communication.events.navigator;

import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.communication.EventRequest;
import net.cobem.grizzly.communication.GameEvent;
import net.cobem.grizzly.communication.composers.user.SendToHomeRoomComposer;
import net.cobem.grizzly.habbohotel.sessions.Session;
import net.cobem.grizzly.utils.UserInputFilter;

public class CreateRoomEvent implements GameEvent {

   public void compose(Session Session, EventRequest Request) {
      String Name = UserInputFilter.filterString(Request.readString(), false);
      String Model = Request.readString();
      if(Name.contains("\'")) {
         Name = Name.replaceAll("\'", "");
      }

      int Owner = Session.getHabbo().getID();
      int RoomID = Grizzly.getHabboHotel().getRooms().create(Name, Owner, Model);
      Session.sendResponse(SendToHomeRoomComposer.compose(RoomID));
   }
}
