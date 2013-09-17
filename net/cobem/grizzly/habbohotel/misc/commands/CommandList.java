package net.cobem.grizzly.habbohotel.misc.commands;

import java.util.Iterator;
import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.communication.composers.misc.SendFrankNotificationComposer;
import net.cobem.grizzly.habbohotel.misc.ChatCommand;
import net.cobem.grizzly.habbohotel.sessions.Session;

public class CommandList implements ChatCommand {

   public int rank() {
      return 0;
   }

   public void execute(Session session, String[] args) {
      String str = "Available Commands\n";
      boolean NewLine = false;

      for(Iterator var6 = Grizzly.getHabboHotel().getCommands().getList().keySet().iterator(); var6.hasNext(); NewLine = !NewLine) {
         String command = (String)var6.next();
         str = str + command;
         if(NewLine) {
            str = str + "\n";
         } else {
            str = str + ",         ";
         }
      }

      session.sendResponse(SendFrankNotificationComposer.compose(str));
   }
}
