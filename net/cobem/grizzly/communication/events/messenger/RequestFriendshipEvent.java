package net.cobem.grizzly.communication.events.messenger;

import java.util.Arrays;
import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.communication.EventRequest;
import net.cobem.grizzly.communication.GameEvent;
import net.cobem.grizzly.communication.composers.messenger.SendFriendRequestComposer;
import net.cobem.grizzly.habbohotel.sessions.Session;
import net.cobem.grizzly.storage.DatabaseResult;

public class RequestFriendshipEvent implements GameEvent {

   public void compose(Session Session, EventRequest Request) {
      String Name = Request.readString();
      if(!Session.getHabbo().getUsername().equals(Name)) {
         Session Reciever = Grizzly.getHabboHotel().getSessions().getByName(Name);
         DatabaseResult RequestCheck;
         if(Reciever == null) {
            RequestCheck = Grizzly.getStorage().preparedQuery("SELECT id FROM server_users WHERE username = ?", Arrays.asList(new String[]{Name}));
            int UserID = RequestCheck.getInt().intValue();
            if(UserID == 0) {
               return;
            }

            DatabaseResult RequestCheck1 = Grizzly.getStorage().preparedQuery("SELECT id FROM server_friendships_pending WHERE sender_id = ? AND reciever_id = ?", Arrays.asList(new String[]{String.valueOf(Session.getHabbo().getID()), String.valueOf(UserID)}));
            if(RequestCheck1.getInt().intValue() != 0) {
               return;
            }

            Grizzly.getStorage().preparedQuery("INSERT INTO server_friendships_pending (sender_id, reciever_id) VALUES (?, ?)", Arrays.asList(new String[]{String.valueOf(Session.getHabbo().getID()), String.valueOf(UserID)}));
         } else {
            RequestCheck = Grizzly.getStorage().preparedQuery("SELECT id FROM server_friendships_pending WHERE sender_id = ? AND reciever_id = ?", Arrays.asList(new String[]{String.valueOf(Session.getHabbo().getID()), String.valueOf(Reciever.getHabbo().getID())}));
            if(RequestCheck.rowCount() != 0) {
               return;
            }

            Reciever.getHabbo().getMessenger().GrabRequests().put(Integer.valueOf(Session.getHabbo().getID()), Session.getHabbo());
            Reciever.sendResponse(SendFriendRequestComposer.compose(Session.getHabbo().getID(), Session.getHabbo().getUsername(), Session.getHabbo().getLook()));
            Grizzly.getStorage().execute("INSERT INTO server_friendships_pending (sender_id, reciever_id) VALUES ( \'" + Session.getHabbo().getID() + "\', " + "\'" + Reciever.getHabbo().getID() + "\')");
         }

      }
   }
}
