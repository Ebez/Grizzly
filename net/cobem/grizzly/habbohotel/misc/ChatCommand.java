package net.cobem.grizzly.habbohotel.misc;

import net.cobem.grizzly.habbohotel.sessions.Session;

public interface ChatCommand {

   int rank();

   void execute(Session var1, String[] var2);
}
