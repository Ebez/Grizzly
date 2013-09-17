package net.cobem.grizzly.habbohotel.misc.commands;

import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.communication.EventResponse;
import net.cobem.grizzly.communication.composers.misc.SendNotificationComposer;
import net.cobem.grizzly.communication.composers.user.SendCreditsComposer;
import net.cobem.grizzly.habbohotel.misc.ChatCommand;
import net.cobem.grizzly.habbohotel.sessions.Session;

public class Credits implements ChatCommand {

   public int rank() {
      return 5;
   }

   public void execute(Session session, String[] args) {
      if(args.length != 2) {
         session.sendResponse(SendNotificationComposer.compose("Invalid number of arguments!"));
      } else {
         String TargetName = args[0];
         EventResponse Message = new EventResponse();
         if(!TargetName.equals("me") && !TargetName.equals(session.getHabbo().getUsername())) {
            Session Target = Grizzly.getHabboHotel().getSessions().getByName(TargetName);
            if(Target == null) {
               session.sendResponse(SendNotificationComposer.compose(TargetName + " is offline!"));
            } else {
               session.getHabbo().setCredits(session.getHabbo().getCredits() + Integer.parseInt(args[1]));
               session.getHabbo().append();
               session.sendResponse(SendCreditsComposer.compose(session.getHabbo().getCredits()));
            }
         } else {
            session.getHabbo().setCredits(session.getHabbo().getCredits() + Integer.parseInt(args[1]));
            session.getHabbo().append();
            session.sendResponse(SendCreditsComposer.compose(session.getHabbo().getCredits()));
            Message.Initialize(475);
            Message.addInt(Integer.valueOf(session.getHabbo().getID()));
            Message.addInt(Integer.valueOf(3));
            Message.addInt(Integer.valueOf(0));
            session.getActor().CurrentRoom.sendMessage(Message);
         }
      }
   }
}
