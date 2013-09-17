package net.cobem.grizzly.communication.events.guild;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.communication.EventRequest;
import net.cobem.grizzly.communication.EventResponse;
import net.cobem.grizzly.communication.GameEvent;
import net.cobem.grizzly.communication.HeaderLibrary;
import net.cobem.grizzly.habbohotel.rooms.Room;
import net.cobem.grizzly.habbohotel.sessions.Session;

public class CreateGuildEvent implements GameEvent {

   public void compose(Session Session, EventRequest Request) {
      Object UserRooms = new HashMap();
      if(((Map)UserRooms).size() == 0) {
         UserRooms = Grizzly.getHabboHotel().getRooms().getByOwner(Session.getHabbo().getID(), true);
      }

      EventResponse Message = new EventResponse();
      Message.Initialize(HeaderLibrary.ShowGroupDialogEvent);
      Message.addInt(Integer.valueOf(10));
      Message.addInt(Integer.valueOf(((Map)UserRooms).size()));
      Iterator var6 = ((Map)UserRooms).values().iterator();

      while(var6.hasNext()) {
         Room Room = (Room)var6.next();
         Message.addInt(Integer.valueOf(Room.ID));
         Message.addString(Room.Title);
         Message.addBool(Boolean.valueOf(false));
      }

      Message.addInt(Integer.valueOf(5));
      Message.addInt(Integer.valueOf(10));
      Message.addInt(Integer.valueOf(3));
      Message.addInt(Integer.valueOf(4));
      Message.addInt(Integer.valueOf(25));
      Message.addInt(Integer.valueOf(17));
      Message.addInt(Integer.valueOf(5));
      Message.addInt(Integer.valueOf(25));
      Message.addInt(Integer.valueOf(17));
      Message.addInt(Integer.valueOf(3));
      Message.addInt(Integer.valueOf(29));
      Message.addInt(Integer.valueOf(11));
      Message.addInt(Integer.valueOf(4));
      Message.addInt(Integer.valueOf(0));
      Message.addInt(Integer.valueOf(0));
      Message.addInt(Integer.valueOf(0));
      Session.sendResponse(Message);
      Session.sendResponse(Grizzly.getHabboHotel().getGuilds().getGuildPieces().Serialize());
   }
}
