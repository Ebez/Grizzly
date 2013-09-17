package net.cobem.grizzly.communication.composers.messenger;

import java.util.Iterator;
import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.communication.EventResponse;
import net.cobem.grizzly.communication.HeaderLibrary;
import net.cobem.grizzly.habbohotel.users.User;
import net.cobem.grizzly.habbohotel.users.messenger.MessengerHandler;

public class SendFriendListComposer {

   public static EventResponse compose(int Rank, MessengerHandler Handler) {
      EventResponse Message = new EventResponse();
      Message.Initialize(HeaderLibrary.InitializeMessengerEvent);
      Message.addInt(Integer.valueOf(1100));
      Message.addInt(Integer.valueOf(300));
      Message.addInt(Integer.valueOf(800));
      Message.addInt(Integer.valueOf(1100));
      Message.addInt(Integer.valueOf(0));
      Message.addInt(Integer.valueOf(Handler.GrabFriends().size()));
      Iterator var4 = Handler.GrabFriends().values().iterator();

      while(var4.hasNext()) {
         User mUser = (User)var4.next();
         Message.addInt(Integer.valueOf(mUser.getID()));
         Message.addString(mUser.getUsername());
         Message.addInt(Integer.valueOf(1));
         Message.addBool(Boolean.valueOf(Grizzly.getHabboHotel().getSessions().getByID(mUser.getID()) != null));
         Message.addBool(Boolean.valueOf(Grizzly.getHabboHotel().getSessions().getByID(mUser.getID()) != null && Grizzly.getHabboHotel().getSessions().getByID(mUser.getID()).getActor().CurrentRoom != null));
         Message.addString(mUser.getLook());
         Message.addInt(Integer.valueOf(0));
         Message.addString(mUser.getMotto());
         Message.addString("");
         Message.addString("");
         Message.addBool(Boolean.valueOf(true));
         Message.addBool(Boolean.valueOf(true));
         Message.addBool(Boolean.valueOf(false));
         Message.addInt(Integer.valueOf(588));
      }

      return Message;
   }
}
