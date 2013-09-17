package net.cobem.grizzly.habbohotel.wired;

import net.cobem.grizzly.communication.EventRequest;
import net.cobem.grizzly.habbohotel.rooms.items.FloorItem;
import net.cobem.grizzly.habbohotel.sessions.Session;

public interface WiredObject {

   void save(Session var1, FloorItem var2, EventRequest var3);
}
