package net.cobem.grizzly.habbohotel.roleplay;

import java.security.SecureRandom;
import java.sql.ResultSet;
import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.communication.composers.rooms.SendRoomShoutComposer;
import net.cobem.grizzly.communication.composers.rooms.UpdateRoomUserInformationComposer;
import net.cobem.grizzly.habbohotel.roleplay.LeavingMonitor;
import net.cobem.grizzly.habbohotel.roleplay.TaxiMonitor;
import net.cobem.grizzly.habbohotel.roleplay.WorkoutMonitor;
import net.cobem.grizzly.habbohotel.sessions.Session;
import net.cobem.grizzly.storage.DatabaseResult;

public class RoleplayObject {

   private Session Session;
   private int TaxiDestination = 0;
   private TaxiMonitor TaxiMonitor;
   private LeavingMonitor LeavingMonitor;
   private WorkoutMonitor WorkoutMonitor;
   private int Strength = 0;
   private int Health = 0;
   private String Head = "";
   private String Look = "";
   private boolean Leaving;
   private boolean WorkingOut;


   public RoleplayObject(Session mSession) {
      this.Session = mSession;
      this.Strength = 0;
      this.Health = 0;
      this.TaxiMonitor = new TaxiMonitor(this);
      this.LeavingMonitor = new LeavingMonitor(this.Session.getHabbo().getID());
      this.WorkoutMonitor = new WorkoutMonitor(this.Session);
      this.fillClass();
      this.getHead();
   }

   public void callTaxi(int ID) {
      this.TaxiDestination = ID;
      if(this.Session.getHabbo().getRank() >= 5) {
         this.TaxiMonitor.setWaitingTime(1);
      } else if(this.Session.getHabbo().getRank() >= 2) {
         this.TaxiMonitor.setWaitingTime((new SecureRandom()).nextInt(10));
      } else {
         this.TaxiMonitor.setWaitingTime((new SecureRandom()).nextInt(30));
      }

   }

   public void runTaxi() {
      this.Session.travel(this.TaxiDestination);
   }

   private boolean fillClass() {
      DatabaseResult GetRoleplay = Grizzly.getStorage().query("SELECT * FROM server_users_roleplay WHERE id = \'" + this.Session.getHabbo().getID() + "\'");
      if(GetRoleplay.rowCount() == 0) {
         Grizzly.getStorage().execute("INSERT INTO server_users_roleplay (id) VALUES (\'" + this.Session.getHabbo().getID() + "\')");
         this.Strength = 5;
         this.Health = 100;
      } else {
         ResultSet Values = Grizzly.getStorage().query("SELECT * FROM server_users_roleplay WHERE id = \'" + this.Session.getHabbo().getID() + "\'").getRow();

         try {
            this.setStrength(Values.getInt("strength"));
            this.setHealth(Values.getInt("health"));
         } catch (Exception var4) {
            var4.printStackTrace();
         }
      }

      return true;
   }

   private void getHead() {
      this.Head = "";
      String[] var4;
      int var3 = (var4 = this.Session.getHabbo().getLook().split("\\.")).length;

      for(int var2 = 0; var2 < var3; ++var2) {
         String Node = var4[var2];
         if(Node.contains("hr-") || Node.contains("hd-")) {
            if(this.Head.equals("")) {
               this.Head = Node;
            }

            this.Head = this.Head + "." + Node + ".";
         }
      }

   }

   public void setBody(String Uniform) {
      this.Look = this.Head + Uniform;
      this.updateInformation();
   }

   public void updateInformation() {
      this.Session.getActor().CurrentRoom.sendMessage(UpdateRoomUserInformationComposer.compose(this.Session.getHabbo().getID(), this.Look, this.Session.getHabbo().getGender().toString(), this.Session.getHabbo().getMotto()));
      this.Session.sendResponse(UpdateRoomUserInformationComposer.compose(-1, this.Look, this.Session.getHabbo().getGender().toString(), this.Session.getHabbo().getMotto()));
   }

   public int getStrength() {
      return this.Strength;
   }

   public void setStrength(int Strength) {
      this.Strength = Strength;
   }

   public void upgradeStrength(int Interval) {
      this.Strength += Interval;
   }

   public int getHealth() {
      return this.Health;
   }

   public void setHealth(int Health) {
      this.Health = Health;
   }

   public void decreaseHealth(int Interval) {
      this.Health -= Interval;
   }

   public boolean isLeaving() {
      return this.Leaving;
   }

   public void setLeaving(boolean Leaving) {
      this.Leaving = Leaving;
   }

   public boolean isWorkingOut() {
      return this.WorkingOut;
   }

   public void setWorkingOut(boolean WorkingOut) {
      this.WorkingOut = WorkingOut;
   }

   public void startLeaveHotel() {
      this.Session.getActor().CurrentRoom.sendMessage(SendRoomShoutComposer.compose(this.Session.getHabbo().getID(), "--- Signing off in 15 seconds. --", 0, 0));
      this.setLeaving(true);
      this.LeavingMonitor.startLeave(this.Session.getActor().CurrentRoom);
   }

   public void startWorkingOut() {
      this.Session.getActor().CurrentRoom.sendMessage(SendRoomShoutComposer.compose(this.Session.getHabbo().getID(), "Starts to workout", 0, 11));
      this.setWorkingOut(true);
      this.WorkoutMonitor.start();
   }

   public void stopWorkingOut() {
      this.Session.getActor().CurrentRoom.sendMessage(SendRoomShoutComposer.compose(this.Session.getHabbo().getID(), "Stops working out", 0, 11));
      this.setWorkingOut(false);
      this.WorkoutMonitor.stop();
   }

   public WorkoutMonitor getWorkoutMonitor() {
      return this.WorkoutMonitor;
   }
}
