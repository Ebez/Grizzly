package net.cobem.grizzly.communication.events.moderation;

import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.communication.EventRequest;
import net.cobem.grizzly.communication.EventResponse;
import net.cobem.grizzly.communication.GameEvent;
import net.cobem.grizzly.communication.HeaderLibrary;
import net.cobem.grizzly.habbohotel.sessions.Session;
import net.cobem.grizzly.habbohotel.users.User;
import net.cobem.grizzly.habbohotel.users.UserDAO;

public class ShowUserInformationEvent implements GameEvent {

   public void compose(Session Session, EventRequest Request) {
      EventResponse Message = new EventResponse();
      int UserID = Request.readInt().intValue();
      boolean Online = false;
      User mUser;
      if(Grizzly.getHabboHotel().getSessions().getByID(UserID) == null) {
         mUser = UserDAO.generateUser(UserID);
      } else {
         mUser = Grizzly.getHabboHotel().getSessions().getByID(UserID).getHabbo();
         Online = true;
      }

      Message.Initialize(HeaderLibrary.SendModToolsUserInformationEvent);
      Message.addInt(Integer.valueOf(mUser.getID()));
      Message.addString(mUser.getUsername());
      Message.addInt(Integer.valueOf(0));
      Message.addInt(Integer.valueOf(0));
      Message.addBool(Boolean.valueOf(Online));
      Message.addInt(Integer.valueOf(0));
      Message.addInt(Integer.valueOf(0));
      Message.addInt(Integer.valueOf(0));
      Message.addInt(Integer.valueOf(0));
      Session.sendResponse(Message);
   }
}
