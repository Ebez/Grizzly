package net.cobem.grizzly.communication.composers.rooms;

import net.cobem.grizzly.communication.EventResponse;
import net.cobem.grizzly.communication.HeaderLibrary;
import net.cobem.grizzly.habbohotel.pathfinding.Position;

public class SendRoomUserStatusComposer {

   public static EventResponse compose(int User, Position CurrentPosition, int Rotation, String Status) {
      EventResponse Message = new EventResponse();
      Message.Initialize(HeaderLibrary.InitializeRoomUserStatusesEvent);
      Message.addInt(Integer.valueOf(1));
      Message.addInt(Integer.valueOf(User));
      Message.addInt(Integer.valueOf(CurrentPosition.X));
      Message.addInt(Integer.valueOf(CurrentPosition.Y));
      Message.addString(Double.toString(CurrentPosition.Z));
      Message.addInt(Integer.valueOf(Rotation));
      Message.addInt(Integer.valueOf(Rotation));
      Message.addString(Status);
      return Message;
   }
}
