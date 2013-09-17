package net.cobem.grizzly.communication.events.user;

import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.communication.EventRequest;
import net.cobem.grizzly.communication.GameEvent;
import net.cobem.grizzly.communication.composers.user.SendUserProfileComposer;
import net.cobem.grizzly.habbohotel.sessions.Session;
import net.cobem.grizzly.habbohotel.users.User;
import net.cobem.grizzly.habbohotel.users.UserDAO;

public class LoadUserProfileEvent implements GameEvent {

   public void compose(Session Session, EventRequest Request) {
      Integer Id = Request.readInt();
      User Habbo = UserDAO.generateUser(Id.intValue());
      if(Habbo != null) {
         if(Habbo.getMessenger() == null) {
            try {
               Habbo.startMessenger();
            } catch (Exception var6) {
               ;
            }
         }

         Session.sendResponse(SendUserProfileComposer.compose(Habbo, Habbo.getMessenger().IsFriend(Session.getHabbo().getID()), Grizzly.getHabboHotel().getSessions().getByID(Habbo.getID()) != null));
      }
   }
}
