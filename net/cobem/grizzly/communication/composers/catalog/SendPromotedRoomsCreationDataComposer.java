package net.cobem.grizzly.communication.composers.catalog;

import java.util.Iterator;
import java.util.Map;
import net.cobem.grizzly.communication.EventResponse;
import net.cobem.grizzly.communication.HeaderLibrary;
import net.cobem.grizzly.habbohotel.rooms.Room;

public class SendPromotedRoomsCreationDataComposer {

   public static EventResponse compose(Map<Integer, Room> Rooms) {
      EventResponse Message = new EventResponse();
      Message.Initialize(HeaderLibrary.SendPromotedRoomsCreationDataEvent);
      Message.addBool(Boolean.valueOf(false));
      Message.addInt(Integer.valueOf(Rooms.size()));
      Iterator var3 = Rooms.values().iterator();

      while(var3.hasNext()) {
         Room mRoom = (Room)var3.next();
         Message.addInt(Integer.valueOf(mRoom.ID));
         Message.addString(mRoom.Title);
         Message.addBool(Boolean.valueOf(false));
      }

      return Message;
   }
}
