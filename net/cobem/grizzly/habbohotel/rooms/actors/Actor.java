package net.cobem.grizzly.habbohotel.rooms.actors;

import gnu.trove.map.hash.THashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.communication.EventResponse;
import net.cobem.grizzly.communication.composers.rooms.SendDrinkStatusComposer;
import net.cobem.grizzly.communication.composers.rooms.SendIdleStatusComposer;
import net.cobem.grizzly.communication.composers.rooms.SendRoomChatComposer;
import net.cobem.grizzly.communication.composers.rooms.SendRoomShoutComposer;
import net.cobem.grizzly.communication.composers.rooms.SendRoomUserStatusComposer;
import net.cobem.grizzly.communication.composers.rooms.SendRoomWhisperComposer;
import net.cobem.grizzly.habbohotel.pathfinding.Position;
import net.cobem.grizzly.habbohotel.rooms.Room;
import net.cobem.grizzly.habbohotel.rooms.items.FloorItem;
import net.cobem.grizzly.habbohotel.sessions.Session;

public class Actor {

   private Session Session;
   public Room CurrentRoom = null;
   public Position CurrentPosition = null;
   public Position GoalPosition = null;
   public Position OverridePosition = null;
   public int Rotation = 0;
   public int SignTimer = 0;
   public int Drink = 0;
   public int DrinkTimer = 0;
   public int GoalRoom = 0;
   public int RollerCycle = 0;
   public int IdleTime = 0;
   public float SitHeight = 0.0F;
   public boolean CycleInteraction;
   public boolean IsMoving;
   public boolean IsRolling;
   public boolean CanWalk = true;
   public boolean Ghost;
   public boolean IsIdle;
   public FloorItem InteractionItem;
   private THashMap<String, Object> Status = new THashMap();


   public Actor(Session mSession) {
      this.Session = mSession;
   }

   public void updateStatus() {
      String RequireStatus = "";

      Entry Stat;
      for(Iterator var3 = this.Status.entrySet().iterator(); var3.hasNext(); RequireStatus = RequireStatus + (String)Stat.getKey() + " " + Stat.getValue() + "/") {
         Stat = (Entry)var3.next();
      }

      if(this.CurrentRoom != null) {
         this.CurrentRoom.sendMessage(SendRoomUserStatusComposer.compose(this.Session.getHabbo().getID(), this.CurrentPosition, this.Rotation, RequireStatus + "//"));
      }

   }

   public EventResponse getStatus() {
      String RequireStatus = "";

      Entry Stat;
      for(Iterator var3 = this.Status.entrySet().iterator(); var3.hasNext(); RequireStatus = RequireStatus + (String)Stat.getKey() + " " + Stat.getValue() + "/") {
         Stat = (Entry)var3.next();
      }

      return SendRoomUserStatusComposer.compose(this.Session.getHabbo().getID(), this.CurrentPosition, this.Rotation, RequireStatus + "//");
   }

   public boolean addStatus(String Key, Object Value, boolean UpdateStatus) {
      if(this.Status.containsKey(Key)) {
         this.Status.remove(Key);
      }

      this.Status.put(Key, Value);
      if(UpdateStatus) {
         this.updateStatus();
      }

      return true;
   }

   public boolean removeStatus(String Key, Boolean UpdateStatus) {
      this.Status.remove(Key);
      if(UpdateStatus.booleanValue()) {
         this.updateStatus();
      }

      return true;
   }

   public boolean hasStatus(String Key) {
      return this.Status.containsKey(Key);
   }

   public boolean clearStatus() {
      this.Status = new THashMap();
      return true;
   }

   public Boolean inRoom() {
      return this.CurrentRoom == null?Boolean.valueOf(false):(this.CurrentRoom.ID == 0?Boolean.valueOf(false):Boolean.valueOf(true));
   }

   public void say(String STR, boolean Shout) {
      if(!Shout) {
         this.CurrentRoom.sendMessage(SendRoomChatComposer.compose(this.Session.getHabbo().getID(), STR, Grizzly.getHabboHotel().parseSmile(STR), 0));
      } else {
         this.CurrentRoom.sendMessage(SendRoomShoutComposer.compose(this.Session.getHabbo().getID(), STR, Grizzly.getHabboHotel().parseSmile(STR), 0));
      }

   }

   public void whisper(String Message, Session Receiver) {
      this.Session.sendResponse(SendRoomWhisperComposer.compose(this.Session.getHabbo().getID(), Message, 0));
      Receiver.sendResponse(SendRoomWhisperComposer.compose(this.Session.getHabbo().getID(), Message, 0));
   }

   public void carry(int DrinkID) {
      this.Drink = DrinkID;
      if(this.Drink != 0) {
         this.DrinkTimer = 120;
      }

      this.CurrentRoom.sendMessage(SendDrinkStatusComposer.compose(this.Session.getHabbo().getID(), DrinkID));
   }

   public void stopMoving(boolean Freeze) {
      this.removeStatus("mv", Boolean.valueOf(true));
      this.Ghost = false;
      this.GoalPosition = null;
      if(Freeze) {
         this.CanWalk = false;
      }

   }

   public void move(Position NewPosition, int Rotation) {
      this.CanWalk = false;
      this.IsMoving = false;
      this.CurrentRoom.getPositions().remove(Integer.valueOf(this.Session.getHabbo().getID()));
      this.CurrentRoom.getMap().UserCollisionMap[this.CurrentPosition.X][this.CurrentPosition.Y] = false;
      this.CurrentPosition = NewPosition;
      this.Rotation = Rotation;
      this.updateStatus();
      this.CanWalk = true;
   }

   public void unidle() {
      if(this.IsIdle) {
         this.IdleTime = 0;
         this.IsIdle = false;
         this.CurrentRoom.sendMessage(SendIdleStatusComposer.compose(this.Session.getHabbo().getID(), this.IsIdle));
      }
   }

   public void idle() {
      if(!this.IsIdle) {
         this.IsIdle = true;
         this.CurrentRoom.sendMessage(SendIdleStatusComposer.compose(this.Session.getHabbo().getID(), this.IsIdle));
      }
   }
}
