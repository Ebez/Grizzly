package net.cobem.grizzly.communication.composers.user;

import net.cobem.grizzly.communication.EventResponse;
import net.cobem.grizzly.communication.HeaderLibrary;

public class SendClubRankComposer {

   public static EventResponse compose(int Rank) {
      EventResponse Message = new EventResponse();
      Message.Initialize(HeaderLibrary.InitializeClubRankEvent);
      Message.addInt(Integer.valueOf(2));
      Message.addInt(Integer.valueOf(Rank));
      return Message;
   }
}
