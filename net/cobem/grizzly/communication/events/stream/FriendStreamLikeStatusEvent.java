package net.cobem.grizzly.communication.events.stream;

import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.communication.EventRequest;
import net.cobem.grizzly.communication.GameEvent;
import net.cobem.grizzly.habbohotel.sessions.Session;

public class FriendStreamLikeStatusEvent implements GameEvent {

   public void compose(Session Session, EventRequest Request) {
      int StreamID = Request.readInt().intValue();
      Grizzly.getStorage().execute("UPDATE server_stream SET likes = likes + 1 WHERE id = \'" + StreamID + "\'");
   }
}
