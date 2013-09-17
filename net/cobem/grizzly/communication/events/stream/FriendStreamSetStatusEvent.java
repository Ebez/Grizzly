package net.cobem.grizzly.communication.events.stream;

import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.communication.EventRequest;
import net.cobem.grizzly.communication.GameEvent;
import net.cobem.grizzly.communication.composers.stream.InitializeFriendStreamComposer;
import net.cobem.grizzly.habbohotel.sessions.Session;
import net.cobem.grizzly.utils.UserInputFilter;

public class FriendStreamSetStatusEvent implements GameEvent {

   public void compose(Session Session, EventRequest Request) {
      String Message = UserInputFilter.filterString(Request.readString(), false);
      Grizzly.getHabboHotel().getStream().setStatus(Session, Message);
      Session.sendResponse(InitializeFriendStreamComposer.compose());
   }
}
