package net.cobem.grizzly.habbohotel.misc.commands;

import net.cobem.grizzly.habbohotel.misc.ChatCommand;
import net.cobem.grizzly.habbohotel.sessions.Session;

public class ToggleStatus implements ChatCommand {

   public int rank() {
      return 5;
   }

   public void execute(Session session, String[] args) {
      session.getActor().Ghost = true;
   }
}
