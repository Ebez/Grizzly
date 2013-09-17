package net.cobem.grizzly.communication.events.misc;

import net.cobem.grizzly.communication.EventRequest;
import net.cobem.grizzly.communication.GameEvent;
import net.cobem.grizzly.communication.composers.user.SendAllowanceComposer;
import net.cobem.grizzly.habbohotel.sessions.Session;

public class SendHotelViewPieceEvent implements GameEvent {

   public void compose(Session Session, EventRequest Request) {
      if(Request.body().length() <= 6) {
         Session.sendResponse(SendAllowanceComposer.compose("", ""));
      } else {
         String Str = Request.readString();
         Str = Str.replace("*", "");
         String Value = Str.split(",")[1];
         Session.sendResponse(SendAllowanceComposer.compose(Str, Value.split(";")[0]));
      }
   }
}
