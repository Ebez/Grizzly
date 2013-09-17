package net.cobem.grizzly.habbohotel.misc.commands;

import net.cobem.grizzly.communication.composers.misc.SendNotificationComposer;
import net.cobem.grizzly.habbohotel.misc.ChatCommand;
import net.cobem.grizzly.habbohotel.sessions.Session;

public class DestroyBot implements ChatCommand {

   public int rank() {
      return 5;
   }

   public void execute(Session session, String[] args) {
      if(args == null) {
         session.sendResponse(SendNotificationComposer.compose("Invalid number of arguments"));
      }

      assert args != null;

      if(args[0].equals("all")) {
         session.getActor().CurrentRoom.removeAllRobots();
      }

   }
}
