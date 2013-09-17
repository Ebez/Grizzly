package net.cobem.grizzly.habbohotel.misc.commands;

import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.communication.composers.misc.SendNotificationComposer;
import net.cobem.grizzly.habbohotel.misc.ChatCommand;
import net.cobem.grizzly.habbohotel.sessions.Session;

public class Summon implements ChatCommand {

   public int rank() {
      return 4;
   }

   public void execute(Session session, String[] args) {
      if(args.length != 1) {
         session.sendResponse(SendNotificationComposer.compose("Invalid number of arguments!"));
      } else {
         String TargetName = args[0];
         Session Target = Grizzly.getHabboHotel().getSessions().getByName(TargetName);
         if(Target == null) {
            session.sendResponse(SendNotificationComposer.compose(TargetName + " is offline!"));
         } else {
            Target.travel(session.getActor().CurrentRoom.ID);
         }
      }
   }
}
