package net.cobem.grizzly.communication.events.rooms;

import net.cobem.grizzly.communication.EventRequest;
import net.cobem.grizzly.communication.GameEvent;
import net.cobem.grizzly.communication.composers.rooms.ToggleChatBubbleComposer;
import net.cobem.grizzly.habbohotel.sessions.Session;

public class StartTypingEvent implements GameEvent {

   public void compose(Session Session, EventRequest Request) {
      Session.getActor().unidle();
      Session.getActor().CurrentRoom.sendMessage(ToggleChatBubbleComposer.compose(Session.getHabbo().getID(), true));
   }
}
