package net.cobem.grizzly.communication.events.trading;

import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.communication.EventRequest;
import net.cobem.grizzly.communication.EventResponse;
import net.cobem.grizzly.communication.GameEvent;
import net.cobem.grizzly.communication.HeaderLibrary;
import net.cobem.grizzly.habbohotel.sessions.Session;
import net.cobem.grizzly.habbohotel.users.items.trading.TradeObject;

public class InitializeTradeEvent implements GameEvent {

   public void compose(Session Session, EventRequest Request) {
      int Partner = Request.readInt().intValue();
      Session RealPartner = Grizzly.getHabboHotel().getSessions().getByID(Partner);
      if(RealPartner != null) {
         if(Session.getHabbo().getTrade() == null && !Session.getActor().hasStatus("trd")) {
            Session.getHabbo().setTrade(new TradeObject(Session, RealPartner));
            RealPartner.getHabbo().setTrade(new TradeObject(RealPartner, Session));
            Session.getActor().addStatus("trd", "", true);
            RealPartner.getActor().addStatus("trd", "", true);
            EventResponse Message = new EventResponse();
            Message.Initialize(HeaderLibrary.InitializeTradeEvent);
            Message.addInt(Integer.valueOf(Session.getHabbo().getID()));
            Message.addInt(Integer.valueOf(1));
            Message.addInt(Integer.valueOf(Partner));
            Message.addInt(Integer.valueOf(1));
            Session.sendResponse(Message);
            RealPartner.sendResponse(Message);
         }
      }
   }
}
