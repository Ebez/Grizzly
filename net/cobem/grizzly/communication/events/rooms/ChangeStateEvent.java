package net.cobem.grizzly.communication.events.rooms;

import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.communication.EventRequest;
import net.cobem.grizzly.communication.GameEvent;
import net.cobem.grizzly.habbohotel.rooms.Room;
import net.cobem.grizzly.habbohotel.rooms.items.FloorItem;
import net.cobem.grizzly.habbohotel.sessions.Session;

public class ChangeStateEvent implements GameEvent {

   public void compose(Session Session, EventRequest Request) {
      int ID = Request.readInt().intValue();
      Room mRoom = Session.getActor().CurrentRoom;
      FloorItem Item = (FloorItem)mRoom.getFloors().get(Integer.valueOf(ID));
      Session.getActor().CurrentRoom.getWiredUtility().onToggleFurniState(Session, Item);
      Grizzly.getHabboHotel().getInteractions().onTrigger(Item.getBase().Interaction, Session, Item, 1);
   }
}
