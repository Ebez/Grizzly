package net.cobem.grizzly.communication.composers.rooms;

import net.cobem.grizzly.communication.EventResponse;
import net.cobem.grizzly.habbohotel.users.User;

public class SendRoomPowerComposer {

   public static EventResponse compose(int RoomID, User User) {
      EventResponse Message = new EventResponse();
      Message.Initialize(3945);
      Message.addInt(Integer.valueOf(RoomID));
      Message.addInt(Integer.valueOf(User.getID()));
      Message.addString(User.getUsername());
      return Message;
   }
}
