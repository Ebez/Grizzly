package net.cobem.grizzly.communication.composers.rooms;

import gnu.trove.map.hash.THashMap;
import java.util.Iterator;
import net.cobem.grizzly.communication.EventResponse;
import net.cobem.grizzly.communication.HeaderLibrary;
import net.cobem.grizzly.habbohotel.sessions.Session;

public class SendRoomUserStatusesComposer {

   public static EventResponse compose(THashMap<Integer, Session> Party) {
      EventResponse Message = new EventResponse();
      Message.Initialize(HeaderLibrary.InitializeRoomUserStatusesEvent);
      Message.addInt(Integer.valueOf(Party.size()));
      Iterator var3 = Party.values().iterator();

      while(var3.hasNext()) {
         Session Actor = (Session)var3.next();
         Message.addInt(Integer.valueOf(Actor.getHabbo().getID()));
         Message.addInt(Integer.valueOf(Actor.getActor().CurrentPosition.X));
         Message.addInt(Integer.valueOf(Actor.getActor().CurrentPosition.Y));
         Message.addString(Double.toString(Actor.getActor().CurrentPosition.Z));
         Message.addInt(Integer.valueOf(Actor.getActor().Rotation));
         Message.addInt(Integer.valueOf(Actor.getActor().Rotation));
         Message.addString("/flatctrl 4/");
      }

      return Message;
   }
}
