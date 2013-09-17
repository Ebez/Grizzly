package net.cobem.grizzly.communication.events.moderation;

import net.cobem.grizzly.communication.EventRequest;
import net.cobem.grizzly.communication.EventResponse;
import net.cobem.grizzly.communication.GameEvent;
import net.cobem.grizzly.communication.HeaderLibrary;
import net.cobem.grizzly.habbohotel.sessions.Session;

public class RoomAlertEvent implements GameEvent {

   public void compose(Session Session, EventRequest Request) {
      Request.readInt();
      Request.readInt();
      String Alert = Request.readString();
      EventResponse Message = new EventResponse();
      Message.Initialize(HeaderLibrary.SendFrankNotificationEvent);
      Message.addString(Alert);
      Message.addString("");
      Session.getActor().CurrentRoom.sendMessage(Message);
   }
}
