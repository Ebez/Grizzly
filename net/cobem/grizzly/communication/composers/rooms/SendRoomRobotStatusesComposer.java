package net.cobem.grizzly.communication.composers.rooms;

import java.util.Iterator;
import net.cobem.grizzly.communication.EventResponse;
import net.cobem.grizzly.communication.HeaderLibrary;
import net.cobem.grizzly.habbohotel.rooms.robots.Robot;
import net.cobem.grizzly.habbohotel.rooms.robots.RobotHandler;

public class SendRoomRobotStatusesComposer {

   public static EventResponse compose(RobotHandler Handler) {
      EventResponse Message = new EventResponse();
      Message.Initialize(HeaderLibrary.InitializeRoomUserStatusesEvent);
      Message.addInt(Integer.valueOf(Handler.count()));
      Iterator var3 = Handler.getRobots().values().iterator();

      while(var3.hasNext()) {
         Robot Bot = (Robot)var3.next();
         Message.addInt(Integer.valueOf(Bot.ID));
         Message.addInt(Integer.valueOf(Bot.X));
         Message.addInt(Integer.valueOf(Bot.Y));
         Message.addString(Double.toString((double)Bot.Height));
         Message.addInt(Integer.valueOf(Bot.Rotation));
         Message.addInt(Integer.valueOf(Bot.Rotation));
         Message.addString("//");
      }

      return Message;
   }
}
