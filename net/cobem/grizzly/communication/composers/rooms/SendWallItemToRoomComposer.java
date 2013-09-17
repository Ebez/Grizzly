package net.cobem.grizzly.communication.composers.rooms;

import net.cobem.grizzly.communication.EventResponse;
import net.cobem.grizzly.communication.HeaderLibrary;
import net.cobem.grizzly.habbohotel.rooms.Room;

public class SendWallItemToRoomComposer {

   public static EventResponse compose(int ID, int Sprite, String Position, Room mRoom) {
      EventResponse Message = new EventResponse();
      Message.Initialize(HeaderLibrary.SendWallItemToRoomEvent);
      Message.addString(Integer.valueOf(ID));
      Message.addInt(Integer.valueOf(Sprite));
      Message.addString(Position);
      Message.addString("");
      Message.addInt(Integer.valueOf(1));
      Message.addInt(Integer.valueOf(mRoom.Owner));
      Message.addString(mRoom.OwnerByName);
      return Message;
   }
}
