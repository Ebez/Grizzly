package net.cobem.grizzly.communication.events.rooms;

import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.communication.EventRequest;
import net.cobem.grizzly.communication.GameEvent;
import net.cobem.grizzly.communication.composers.rooms.SendRoomWhisperComposer;
import net.cobem.grizzly.habbohotel.sessions.Session;

public class WhisperEvent implements GameEvent {

   public void compose(Session Session, EventRequest Request) {
      String Text = Request.readString();
      String Receiver = Text.split(" ")[0];
      String RealText = Text.replace(Receiver + " ", "");
      Session.sendResponse(SendRoomWhisperComposer.compose(Session.getHabbo().getID(), RealText, Grizzly.getHabboHotel().parseSmile(RealText)));
      Grizzly.getHabboHotel().getSessions().getByName(Receiver).sendResponse(SendRoomWhisperComposer.compose(Session.getHabbo().getID(), RealText, Grizzly.getHabboHotel().parseSmile(RealText)));
   }
}
