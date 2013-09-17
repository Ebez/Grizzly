package net.cobem.grizzly.habbohotel.users.messenger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.communication.EventResponse;
import net.cobem.grizzly.communication.HeaderLibrary;
import net.cobem.grizzly.habbohotel.sessions.Session;
import net.cobem.grizzly.habbohotel.users.User;
import net.cobem.grizzly.habbohotel.users.UserDAO;
import net.cobem.grizzly.storage.DatabaseResult;
import org.jboss.netty.channel.group.DefaultChannelGroup;

public class MessengerHandler {

   private int ID;
   private Map<Integer, User> Friends;
   private Map<Integer, User> FriendRequests;


   public MessengerHandler(int ID) {
      this.ID = ID;
      this.Friends = new HashMap();
      this.FriendRequests = new HashMap();

      try {
         this.LoadFriends();
         this.LoadFriendRequests();
      } catch (SQLException var3) {
         ;
      }

   }

   private void LoadFriends() throws SQLException {
      DatabaseResult GrabFriends = Grizzly.getStorage().query("SELECT * FROM server_friendships WHERE user_id = \'" + this.ID + "\'");
      ResultSet AsSecondFriend;
      if(GrabFriends.getTable() != null) {
         AsSecondFriend = GrabFriends.getTable();

         while(AsSecondFriend.next()) {
            this.Friends.put(new Integer(AsSecondFriend.getInt("id")), UserDAO.generateUser(AsSecondFriend.getInt("friend_id")));
         }
      }

      GrabFriends = Grizzly.getStorage().query("SELECT * FROM server_friendships WHERE friend_id = \'" + this.ID + "\'");
      if(GrabFriends.getTable() != null) {
         AsSecondFriend = GrabFriends.getTable();

         while(AsSecondFriend.next()) {
            this.Friends.put(new Integer(AsSecondFriend.getInt("id")), UserDAO.generateUser(AsSecondFriend.getInt("user_id")));
         }
      }

   }

   private void LoadFriendRequests() throws SQLException {
      DatabaseResult GrabRequests = Grizzly.getStorage().query("SELECT * FROM server_friendships_pending WHERE reciever_id = \'" + this.ID + "\'");
      if(GrabRequests.getTable() != null) {
         ResultSet Pending = GrabRequests.getTable();

         while(Pending.next()) {
            this.FriendRequests.put(new Integer(Pending.getInt("id")), UserDAO.generateUser(Pending.getInt("sender_id")));
         }
      }

   }

   public Map<Integer, User> GrabFriends() {
      return this.Friends;
   }

   public Map<Integer, User> GrabRequests() {
      return this.FriendRequests;
   }

   public boolean IsFriend(int ID) {
      return this.Friends.containsValue(UserDAO.generateUser(ID));
   }

   public void UpdateStatus(boolean Online) {
      EventResponse Message = new EventResponse();
      Session Session = Grizzly.getHabboHotel().getSessions().getByID(this.ID);
      Message.Initialize(HeaderLibrary.UpdateFriendStateEvent);
      Message.addInt(Integer.valueOf(0));
      Message.addInt(Integer.valueOf(1));
      Message.addInt(Integer.valueOf(0));
      Message.addInt(Integer.valueOf(Session.getHabbo().getID()));
      Message.addString(Session.getHabbo().getUsername());
      Message.addInt(Integer.valueOf(1));
      Message.addBool(Boolean.valueOf(Online));
      Message.addBool(Boolean.valueOf(false));
      Message.addString(Session.getHabbo().getLook());
      Message.addInt(Integer.valueOf(0));
      Message.addString(Session.getHabbo().getMotto());
      Message.addInt(Integer.valueOf(0));
      Message.addInt(Integer.valueOf(0));
      Message.addInt(Integer.valueOf(0));
      DefaultChannelGroup Friends = new DefaultChannelGroup();
      Iterator var6 = this.GrabFriends().values().iterator();

      while(var6.hasNext()) {
         User mUser = (User)var6.next();
         Session mSession = Grizzly.getHabboHotel().getSessions().getByID(mUser.getID());
         if(mSession != null && mSession.getHabbo().getID() != this.ID) {
            Friends.add(mSession.getChannel());
         }
      }

      Friends.write(Message);
   }
}
