package net.cobem.grizzly.habbohotel.sessions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.communication.EventResponse;
import net.cobem.grizzly.habbohotel.sessions.Session;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.group.DefaultChannelGroup;

public class SessionHandler {

   private Map<Channel, Session> SessionList = new HashMap();


   public Session create(Channel Channel) {
      Session mSession = new Session(Channel, this.SessionList.size() + 1);
      Channel.setAttachment(mSession);
      this.SessionList.put(Channel, mSession);
      if(Grizzly.getConfig().get("grizzly.connection.log").equals("1")) {
         Grizzly.write("Started communication with " + mSession.ip() + " [" + mSession.id() + "]");
      }

      return mSession;
   }

   public void remove(Channel mChannel) {
      this.SessionList.remove(mChannel);
   }

   public void kill(Session mSession) {
      try {
         mSession.getHabbo().refreshMessenger(false);
      } catch (Exception var3) {
         System.gc();
         return;
      }

      if(mSession.getActor() != null) {
         mSession.getActor().GoalRoom = 0;
         mSession.leaveRoom(true);
      }

      this.SessionList.remove(mSession.getChannel());
      Grizzly.getStorage().execute("UPDATE server_users SET online = \'0\' WHERE id = \'" + mSession.getHabbo().getID() + "\'");
      Grizzly.write("Killed communication with " + mSession.ip() + " [" + mSession.id() + "]");
      mSession.getChannel().disconnect();
      System.gc();
   }

   public Session get(Channel Channel) {
      if(this.SessionList.containsKey(Channel)) {
         return (Session)this.SessionList.get(Channel);
      } else {
         Grizzly.write("For some reason Grizzly tried to retrieve an empty session!!");
         return null;
      }
   }

   public Session getByID(int ID) {
      this.cleanSessions();
      Iterator var3 = this.SessionList.values().iterator();

      while(var3.hasNext()) {
         Session mSession = (Session)var3.next();

         try {
            if(mSession.getHabbo().getID() == ID) {
               return mSession;
            }
         } catch (NullPointerException var5) {
            ;
         }
      }

      return null;
   }

   public Session getByName(String Name) {
      this.cleanSessions();
      Iterator var3 = this.SessionList.values().iterator();

      while(var3.hasNext()) {
         Session mSession = (Session)var3.next();
         if(mSession.getHabbo().getUsername().equalsIgnoreCase(Name)) {
            return mSession;
         }
      }

      return null;
   }

   private boolean cleanSessions() {
      ArrayList RemoveList = new ArrayList();
      Iterator var3 = this.SessionList.values().iterator();

      Session Removed;
      while(var3.hasNext()) {
         Removed = (Session)var3.next();
         if(!Removed.getChannel().isOpen() || !Removed.getChannel().isWritable()) {
            RemoveList.add(Removed);
         }
      }

      var3 = RemoveList.iterator();

      while(var3.hasNext()) {
         Removed = (Session)var3.next();
         this.SessionList.remove(Removed);
      }

      return true;
   }

   public Map<Channel, Session> getSessions() {
      return this.SessionList;
   }

   public void sendHotelMessage(EventResponse Message) {
      DefaultChannelGroup Group = new DefaultChannelGroup();
      Group.addAll(this.SessionList.keySet());
      Group.write(Message);
   }
}
