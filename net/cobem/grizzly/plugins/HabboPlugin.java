package net.cobem.grizzly.plugins;

import net.cobem.grizzly.habbohotel.sessions.Session;
import net.cobem.grizzly.plugins.HabboEvent;

public interface HabboPlugin {

   void OnEvent(HabboEvent var1, Session var2);

   void OnRegister(String var1, String var2, String var3);

   String GrabName();

   String GrabAuthor();

   String GrabVersion();
}
