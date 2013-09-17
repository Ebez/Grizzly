package net.cobem.grizzly.communication.composers.user;

import net.cobem.grizzly.communication.EventResponse;
import net.cobem.grizzly.communication.HeaderLibrary;

public class SendClubComposer {

   public static EventResponse compose(int Days) {
      EventResponse Message = new EventResponse();
      Message.Initialize(HeaderLibrary.InitializeClubEvent);
      Message.addString("club_habbo");
      Message.addInt(Integer.valueOf(Days));
      Message.addInt(Integer.valueOf(3));
      Message.addInt(Integer.valueOf(0));
      Message.addInt(Integer.valueOf(1));
      Message.addBool(Boolean.valueOf(true));
      Message.addBool(Boolean.valueOf(true));
      Message.addInt(Integer.valueOf(0));
      Message.addInt(Integer.valueOf(0));
      Message.addInt(Integer.valueOf(28022));
      return Message;
   }
}
