package net.cobem.grizzly.communication.events.misc;

import net.cobem.grizzly.communication.EventRequest;
import net.cobem.grizzly.communication.EventResponse;
import net.cobem.grizzly.communication.GameEvent;
import net.cobem.grizzly.communication.HeaderLibrary;
import net.cobem.grizzly.habbohotel.sessions.Session;

public class SaveNewNameEvent implements GameEvent {

   public void compose(Session Session, EventRequest Request) {
      String NewUsername = Request.readString();
      EventResponse Message = new EventResponse();
      Message.Initialize(HeaderLibrary.SaveChangeNameEvent);
      Message.addInt(Integer.valueOf(0));
      Message.addString(NewUsername);
      Message.addInt(Integer.valueOf(0));
      Session.sendResponse(Message);
      Session.getHabbo().getUsername();
   }
}
