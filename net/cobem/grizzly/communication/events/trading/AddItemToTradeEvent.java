package net.cobem.grizzly.communication.events.trading;

import net.cobem.grizzly.communication.EventRequest;
import net.cobem.grizzly.communication.GameEvent;
import net.cobem.grizzly.habbohotel.sessions.Session;
import net.cobem.grizzly.habbohotel.users.items.InventoryItem;

public class AddItemToTradeEvent implements GameEvent {

   public void compose(Session Session, EventRequest Request) {
      if(Session.getHabbo().getTrade() != null) {
         int id = Request.readInt().intValue();
         InventoryItem Item = Session.getHabbo().getItems().getItem(id);
         if(Item != null) {
            Session.getHabbo().getTrade().addOffer(Item);
            Session.getHabbo().getTrade().updateUsers();
         }
      }
   }
}
