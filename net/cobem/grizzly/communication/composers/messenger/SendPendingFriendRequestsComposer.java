package net.cobem.grizzly.communication.composers.messenger;

import java.util.Iterator;
import net.cobem.grizzly.communication.EventResponse;
import net.cobem.grizzly.communication.HeaderLibrary;
import net.cobem.grizzly.habbohotel.users.User;
import net.cobem.grizzly.habbohotel.users.messenger.MessengerHandler;

public class SendPendingFriendRequestsComposer {

   public static EventResponse compose(MessengerHandler Handler) {
      EventResponse Message = new EventResponse();
      Message.Initialize(HeaderLibrary.InitializePendingFriendRequestsEvent);
      Message.addInt(Integer.valueOf(Handler.GrabRequests().size()));
      Message.addInt(Integer.valueOf(Handler.GrabRequests().size()));
      Iterator var3 = Handler.GrabRequests().values().iterator();

      while(var3.hasNext()) {
         User mUser = (User)var3.next();
         Message.addInt(Integer.valueOf(mUser.getID()));
         Message.addString(mUser.getUsername());
         Message.addString(mUser.getLook());
      }

      return Message;
   }
}
