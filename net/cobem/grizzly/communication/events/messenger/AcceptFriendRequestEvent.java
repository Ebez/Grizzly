package net.cobem.grizzly.communication.events.messenger;

import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.communication.EventRequest;
import net.cobem.grizzly.communication.GameEvent;
import net.cobem.grizzly.habbohotel.sessions.Session;
import net.cobem.grizzly.habbohotel.users.User;
import net.cobem.grizzly.habbohotel.users.UserDAO;

public class AcceptFriendRequestEvent implements GameEvent {

   public void compose(Session Session, EventRequest Request) {
      int Count = Request.readInt().intValue();

      for(int i = 0; i < Count; ++i) {
         User mUser = UserDAO.generateUser(Request.readInt().intValue());
         Grizzly.getStorage().execute("INSERT INTO server_friendships (user_id, friend_id) VALUES (\'" + Session.getHabbo().getID() + "\', \'" + mUser.getID() + "\')");
         Grizzly.getStorage().execute("DELETE FROM server_friendships_pending WHERE sender_id = \'" + mUser.getID() + "\' AND reciever_id = \'" + Session.getHabbo().getID() + "\'");
         Grizzly.getHabboHotel().getStream().addFriend(Session, mUser.getID());
         if(Grizzly.getHabboHotel().getSessions().getByID(mUser.getID()) != null) {
            try {
               mUser.refreshMessenger(true);
               Session.getHabbo().refreshMessenger(true);
            } catch (Exception var7) {
               ;
            }
         }
      }

   }
}
