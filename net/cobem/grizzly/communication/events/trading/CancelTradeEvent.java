package net.cobem.grizzly.communication.events.trading;

import net.cobem.grizzly.communication.EventRequest;
import net.cobem.grizzly.communication.GameEvent;
import net.cobem.grizzly.habbohotel.sessions.Session;

public class CancelTradeEvent implements GameEvent {

   public void compose(Session Session, EventRequest Request) {
      if(Session.getHabbo().getTrade() != null) {
         Session.getHabbo().getTrade().cancelTrade();
      }
   }
}
