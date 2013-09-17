package net.cobem.grizzly.communication.events.rooms;

import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.communication.EventRequest;
import net.cobem.grizzly.communication.GameEvent;
import net.cobem.grizzly.communication.composers.rooms.SendRoomChatComposer;
import net.cobem.grizzly.communication.composers.rooms.SendRoomWhisperComposer;
import net.cobem.grizzly.habbohotel.sessions.Session;

public class SayEvent implements GameEvent {

   public void compose(Session Session, EventRequest Request) {
      if(Session.getActor().inRoom().booleanValue()) {
         String Str = Request.readString().trim();
         int Color = Request.readInt().intValue();
         if(!Str.startsWith(":") || !Grizzly.getHabboHotel().getCommands().parse(Session, Str.replace(":", ""))) {
            if(Session.getActor().CurrentRoom.getWiredUtility().onUserTalk(Session, Str)) {
               Session.sendResponse(SendRoomWhisperComposer.compose(Session.getHabbo().getID(), Str, 0));
            } else {
               Session.getActor().CurrentRoom.sendMessage(SendRoomChatComposer.compose(Session.getHabbo().getID(), Str, Grizzly.getHabboHotel().parseSmile(Str), Color));
            }

         }
      }
   }
}
