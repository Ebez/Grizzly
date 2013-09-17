package net.cobem.grizzly.habbohotel.misc.commands;

import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.habbohotel.misc.ChatCommand;
import net.cobem.grizzly.habbohotel.sessions.Session;

public class DestructPlugin implements ChatCommand {

   public int rank() {
      return 6;
   }

   public void execute(Session session, String[] args) {
      if(args.length != 0) {
         if(Grizzly.getPlugins().DestructPlugin(session, args[0])) {
            session.getActor().say("*Disables the plugin " + args[0] + "*", true);
         }

      }
   }
}
