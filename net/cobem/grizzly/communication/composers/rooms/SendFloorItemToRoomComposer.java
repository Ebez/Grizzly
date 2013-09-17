package net.cobem.grizzly.communication.composers.rooms;

import net.cobem.grizzly.communication.EventResponse;
import net.cobem.grizzly.communication.HeaderLibrary;
import net.cobem.grizzly.habbohotel.rooms.Room;

public class SendFloorItemToRoomComposer {

   public static EventResponse compose(int ID, int Sprite, int X, int Y, int Rotation, String Height, Room mRoom) {
      EventResponse Message = new EventResponse();
      Message.Initialize(HeaderLibrary.SendFloorItemToRoomEvent);
      Message.addInt(Integer.valueOf(ID));
      Message.addInt(Integer.valueOf(Sprite));
      Message.addInt(Integer.valueOf(X));
      Message.addInt(Integer.valueOf(Y));
      Message.addInt(Integer.valueOf(Rotation));
      Message.addString(Height);
      Message.addInt(Integer.valueOf(0));
      Message.addInt(Integer.valueOf(0));
      Message.addString("0");
      Message.addInt(Integer.valueOf(-1));
      Message.addInt(Integer.valueOf(1));
      Message.addInt(Integer.valueOf(mRoom.Owner));
      Message.addString(mRoom.OwnerByName);
      return Message;
   }
}
