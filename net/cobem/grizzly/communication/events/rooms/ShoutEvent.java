package net.cobem.grizzly.communication.events.rooms;

import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.communication.EventRequest;
import net.cobem.grizzly.communication.EventResponse;
import net.cobem.grizzly.communication.GameEvent;
import net.cobem.grizzly.communication.composers.rooms.SendRoomShoutComposer;
import net.cobem.grizzly.communication.composers.rooms.SendRoomWhisperComposer;
import net.cobem.grizzly.habbohotel.sessions.Session;

public class ShoutEvent implements GameEvent {

   public void compose(Session Session, EventRequest Request) {
      if(Session.getActor().inRoom().booleanValue()) {
         String Str = Request.readString().trim();
         int Color = Request.readInt().intValue();
         new EventResponse();
         if(Session.getActor().CurrentRoom.getWiredUtility().onUserTalk(Session, Str)) {
            Session.sendResponse(SendRoomWhisperComposer.compose(Session.getHabbo().getID(), Str, 0));
         } else {
            Session.getActor().CurrentRoom.sendMessage(SendRoomShoutComposer.compose(Session.getHabbo().getID(), Str, Grizzly.getHabboHotel().parseSmile(Str), Color));
         }

      }
   }
}
