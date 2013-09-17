package net.cobem.grizzly.habbohotel.misc.commands;

import net.cobem.grizzly.habbohotel.misc.ChatCommand;
import net.cobem.grizzly.habbohotel.sessions.Session;

public class Overrider implements ChatCommand {

   public int rank() {
      return 2;
   }

   public void execute(Session session, String[] args) {
      session.getActor().Ghost = !session.getActor().Ghost;
   }
}
