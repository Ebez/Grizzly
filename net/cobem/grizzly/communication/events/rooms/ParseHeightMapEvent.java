package net.cobem.grizzly.communication.events.rooms;

import net.cobem.grizzly.communication.EventRequest;
import net.cobem.grizzly.communication.GameEvent;
import net.cobem.grizzly.communication.composers.rooms.SendFirstHeightMapComposer;
import net.cobem.grizzly.communication.composers.rooms.SendSecondHeightMapComposer;
import net.cobem.grizzly.communication.events.rooms.FinishRoomLoadEvent;
import net.cobem.grizzly.habbohotel.rooms.Room;
import net.cobem.grizzly.habbohotel.sessions.Session;

public class ParseHeightMapEvent implements GameEvent {

   public void compose(Session Session, EventRequest Request) {
      Room mRoom = Session.getActor().CurrentRoom;
      if(mRoom != null) {
         Session.sendResponse(SendFirstHeightMapComposer.compose(mRoom.getModel().heightmap()));
         Session.sendResponse(SendSecondHeightMapComposer.compose(mRoom.getModel().relativeHeightMap()));
      }

      (new FinishRoomLoadEvent()).compose(Session, Request);
   }
}
