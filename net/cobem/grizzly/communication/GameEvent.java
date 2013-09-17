package net.cobem.grizzly.communication;

import net.cobem.grizzly.communication.EventRequest;
import net.cobem.grizzly.habbohotel.sessions.Session;

public interface GameEvent {

   void compose(Session var1, EventRequest var2);
}
