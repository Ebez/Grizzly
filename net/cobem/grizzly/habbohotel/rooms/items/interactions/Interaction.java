package net.cobem.grizzly.habbohotel.rooms.items.interactions;

import net.cobem.grizzly.habbohotel.sessions.Session;

public interface Interaction {

   void onPlace(Session var1, Object var2);

   void onRemove(Session var1, Object var2);

   void onTrigger(Session var1, Object var2, int var3);

   void onCycle(Object var1);
}
