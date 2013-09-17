package net.cobem.grizzly.communication.composers.rooms;

import java.util.Iterator;
import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.communication.EventResponse;
import net.cobem.grizzly.communication.HeaderLibrary;
import net.cobem.grizzly.habbohotel.rooms.Room;
import net.cobem.grizzly.habbohotel.users.User;
import net.cobem.grizzly.habbohotel.users.UserDAO;

public class SendRoomUserRightsComposer {

   public static EventResponse compose(Room mRoom) {
      EventResponse Message = new EventResponse();
      Message.Initialize(HeaderLibrary.SendRoomUserRightsEvent);
      Message.addInt(Integer.valueOf(mRoom.ID));
      Message.addInt(Integer.valueOf(mRoom.getRightHolders().size()));
      Grizzly.write(Integer.valueOf(mRoom.getRightHolders().size()));
      Iterator var3 = mRoom.getRightHolders().iterator();

      while(var3.hasNext()) {
         int UserID = ((Integer)var3.next()).intValue();
         User User = UserDAO.generateUser(UserID);
         Grizzly.write(User.getUsername());
         Message.addInt(Integer.valueOf(User.getID()));
         Message.addString(User.getUsername());
      }

      return Message;
   }
}
