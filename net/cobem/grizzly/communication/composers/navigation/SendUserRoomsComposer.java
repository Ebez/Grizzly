package net.cobem.grizzly.communication.composers.navigation;

import java.util.Iterator;
import java.util.Map;
import net.cobem.grizzly.communication.EventResponse;
import net.cobem.grizzly.communication.HeaderLibrary;
import net.cobem.grizzly.habbohotel.rooms.Room;

public class SendUserRoomsComposer {

   public static EventResponse compose(Map<Integer, Room> PopulatedRooms) {
      EventResponse Message = new EventResponse();
      Message.Initialize(HeaderLibrary.ViewUserRoomsEvent);
      Message.addInt(Integer.valueOf(5));
      Message.addString("");
      Message.addInt(Integer.valueOf(PopulatedRooms.size()));
      Iterator var3 = PopulatedRooms.values().iterator();

      while(var3.hasNext()) {
         Room mRoom = (Room)var3.next();
         Message.addInt(Integer.valueOf(mRoom.ID));
         Message.addString(mRoom.Title);
         Message.addBool(Boolean.valueOf(true));
         Message.addInt(Integer.valueOf(mRoom.Owner));
         Message.addString(mRoom.OwnerByName);
         Message.addInt(Integer.valueOf(mRoom.State));
         Message.addInt(Integer.valueOf(mRoom.getParty().size()));
         Message.addInt(Integer.valueOf(50));
         Message.addString(mRoom.Description);
         Message.addInt(Integer.valueOf(0));
         Message.addInt(Integer.valueOf(0));
         Message.addInt(Integer.valueOf(0));
         Message.addInt(Integer.valueOf(0));
         Message.addInt(Integer.valueOf(0));
         Message.addInt(Integer.valueOf(0));
         Message.addInt(Integer.valueOf(0));
         Message.addString("");
         Message.addInt(Integer.valueOf(1));
         Message.addString("nigga");
         Message.addInt(Integer.valueOf(0));
         Message.addInt(Integer.valueOf(0));
         Message.addInt(Integer.valueOf(0));
         Message.addBool(Boolean.valueOf(true));
         Message.addBool(Boolean.valueOf(true));
         Message.addInt(Integer.valueOf(0));
         Message.addInt(Integer.valueOf(0));
      }

      Message.addInt(Integer.valueOf(0));
      Message.addInt(Integer.valueOf(0));
      Message.addBool(Boolean.valueOf(false));
      return Message;
   }
}
