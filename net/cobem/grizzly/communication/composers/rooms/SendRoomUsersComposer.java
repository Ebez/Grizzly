package net.cobem.grizzly.communication.composers.rooms;

import gnu.trove.map.hash.THashMap;
import java.util.Iterator;
import net.cobem.grizzly.communication.EventResponse;
import net.cobem.grizzly.communication.HeaderLibrary;
import net.cobem.grizzly.habbohotel.guild.Guild;
import net.cobem.grizzly.habbohotel.sessions.Session;

public class SendRoomUsersComposer {

   public static EventResponse compose(THashMap<Integer, Session> Party) {
      EventResponse Message = new EventResponse();
      Message.Initialize(HeaderLibrary.InitializeRoomUsersEvent);
      Message.addInt(Integer.valueOf(Party.size()));

      for(Iterator var3 = Party.values().iterator(); var3.hasNext(); Message.addInt(Integer.valueOf(1))) {
         Session Actor = (Session)var3.next();
         Message.addInt(Integer.valueOf(Actor.getHabbo().getID()));
         Message.addString(Actor.getHabbo().getUsername());
         Message.addString(Actor.getHabbo().getMotto());
         Message.addString(Actor.getHabbo().getLook());
         Message.addInt(Integer.valueOf(Actor.getHabbo().getID()));
         Message.addInt(Integer.valueOf(Actor.getActor().CurrentPosition.X));
         Message.addInt(Integer.valueOf(Actor.getActor().CurrentPosition.Y));
         Message.addString(Double.toString(Actor.getActor().CurrentPosition.Z));
         Message.addInt(Integer.valueOf(Actor.getActor().Rotation));
         Message.addInt(Integer.valueOf(1));
         Message.addString(Actor.getHabbo().getGender().toString().toLowerCase());
         if(Actor.getHabbo().getGuild() == 0) {
            Message.addInt(Integer.valueOf(-1));
            Message.addInt(Integer.valueOf(-1));
            Message.addInt(Integer.valueOf(0));
         } else {
            Guild guild = new Guild(Actor.getHabbo().getGuild());
            Message.addInt(Integer.valueOf(Actor.getHabbo().getGuild()));
            Message.addInt(Integer.valueOf(2));
            Message.addString(guild.getTitle());
            Message.addString(guild.getImage());
         }
      }

      return Message;
   }
}
