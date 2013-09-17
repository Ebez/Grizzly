package net.cobem.grizzly.habbohotel.misc.commands;

import java.util.Date;
import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.communication.composers.misc.SendNotificationComposer;
import net.cobem.grizzly.habbohotel.misc.ChatCommand;
import net.cobem.grizzly.habbohotel.sessions.Session;
import net.cobem.grizzly.habbohotel.users.User;
import net.cobem.grizzly.habbohotel.users.UserDAO;

public class Ban implements ChatCommand {

   public int rank() {
      return 5;
   }

   public void execute(Session session, String[] args) {
      if(args.length < 2) {
         session.sendResponse(SendNotificationComposer.compose("Invalid number of arguments!"));
      } else {
         User target = UserDAO.generateUser(args[0]);
         if(target == null) {
            session.sendResponse(SendNotificationComposer.compose("Cannot find that user!"));
         } else {
            int minutes = (new Integer(args[1])).intValue();
            if(minutes <= 0) {
               session.sendResponse(SendNotificationComposer.compose("Invalid number of minutes for ban!"));
            } else if(target.getRank() >= 4) {
               session.sendResponse(SendNotificationComposer.compose("You cannot ban a staff member, de-rank them first!"));
            } else {
               Date whatever = new Date();
               whatever.setTime(whatever.getTime() + (long)(minutes * 60));
               UserDAO.banUser(target.getID(), whatever);
               if(Grizzly.getHabboHotel().getSessions().getByID(target.getID()) != null) {
                  Grizzly.getHabboHotel().getSessions().getByID(target.getID()).getChannel().disconnect();
               }

            }
         }
      }
   }
}
