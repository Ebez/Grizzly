package net.cobem.grizzly.communication.events.rooms;

import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.communication.EventRequest;
import net.cobem.grizzly.communication.GameEvent;
import net.cobem.grizzly.habbohotel.rooms.Room;
import net.cobem.grizzly.habbohotel.rooms.items.FloorItem;
import net.cobem.grizzly.habbohotel.sessions.Session;

public class CloseDiceEvent implements GameEvent {

   public void compose(Session Session, EventRequest Request) {
      if(Session.getActor().CurrentRoom.getRightHolders().contains(Integer.valueOf(Session.getHabbo().getID()))) {
         int ID = Request.readInt().intValue();
         Room mRoom = Session.getActor().CurrentRoom;
         FloorItem Item = (FloorItem)mRoom.getFloors().get(Integer.valueOf(ID));
         Grizzly.getHabboHotel().getInteractions().onTrigger("dice", Session, Item, 0);
      }
   }
}
