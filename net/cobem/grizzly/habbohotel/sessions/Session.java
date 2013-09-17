package net.cobem.grizzly.habbohotel.sessions;

import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.communication.EventResponse;
import net.cobem.grizzly.communication.HeaderLibrary;
import net.cobem.grizzly.communication.composers.rooms.UpdateRoomUserInformationComposer;
import net.cobem.grizzly.communication.composers.user.SendToHomeRoomComposer;
import net.cobem.grizzly.habbohotel.roleplay.RoleplayObject;
import net.cobem.grizzly.habbohotel.rooms.actors.Actor;
import net.cobem.grizzly.habbohotel.users.User;
import org.jboss.netty.channel.Channel;

public class Session {

   private int ID;
   private Channel Channel;
   private User Habbo;
   private Actor Actor;
   private EventResponse Response;
   public boolean GlobalEffect;
   public int OverideID = 0;
   public int CurrentEffect = 0;
   private RoleplayObject RoleplayObject;
   public String MachineID;


   public Session(Channel Channel, int ID) {
      this.Channel = Channel;
      this.ID = ID;
      this.Response = new EventResponse();
   }

   public int id() {
      return this.ID;
   }

   public Channel getChannel() {
      return this.Channel;
   }

   public String ip() {
      return this.Channel == null?"NULL":this.Channel.getRemoteAddress().toString().split(":")[0].substring(1);
   }

   public User getHabbo() {
      return this.Habbo;
   }

   public Actor getActor() {
      return this.Actor;
   }

   public EventResponse getResponse() {
      return this.Response;
   }

   public void sendResponse(EventResponse Message) {
      if(Grizzly.getConfig().get("net.grizzly.packetlog").equals("1")) {
         Grizzly.write("[" + Message.header() + "] " + Message.body() + " sent by " + this.ip());
         Grizzly.write("[" + Message.header() + "] " + Message.SimpleResponse);
      }

      this.Channel.write(Message);
   }

   public void sendResponse() {
      if(Grizzly.getConfig().get("net.grizzly.packetlog").equals("1")) {
         Grizzly.write("[" + this.Response.header() + "] " + this.Response.body() + " sent by " + this.ip());
         Grizzly.write("[" + this.Response.header() + "] " + this.Response.SimpleResponse);
      }

      this.Channel.write(this.Response);
   }

   public EventResponse returnResponse() {
      return this.Response;
   }

   public void leaveRoom(boolean LeavingHotel) {
      if(this.getActor().inRoom().booleanValue()) {
         this.getActor().CurrentRoom.removeUser(this, LeavingHotel);
      }
   }

   public void travel(int ID) {
      this.OverideID = ID;
      this.sendResponse(SendToHomeRoomComposer.compose(ID));
   }

   public void effect(int EffectID) {
      this.CurrentEffect = EffectID;
      EventResponse Message = new EventResponse();
      Message.Initialize(HeaderLibrary.SendRoomEffectEvent);
      Message.addInt(Integer.valueOf(this.getHabbo().getID()));
      Message.addInt(Integer.valueOf(this.CurrentEffect));
      Message.addInt(Integer.valueOf(0));
      this.getActor().CurrentRoom.sendMessage(Message);
   }

   public void updateInformation() {
      this.getActor().CurrentRoom.sendMessage(UpdateRoomUserInformationComposer.compose(this.getHabbo().getID(), this.getHabbo().getLook(), this.getHabbo().getGender().toString(), this.getHabbo().getMotto()));
      this.sendResponse(UpdateRoomUserInformationComposer.compose(-1, this.getHabbo().getLook(), this.getHabbo().getGender().toString(), this.getHabbo().getMotto()));
   }

   public RoleplayObject getRoleplay() {
      return this.RoleplayObject;
   }

   public void tryLogin(String Ticket) {
      this.Habbo = new User(Ticket);
      this.Actor = new Actor(this);
      if(Grizzly.roleplayEnabled()) {
         this.RoleplayObject = new RoleplayObject(this);
      } else {
         this.RoleplayObject = null;
      }

   }
}
