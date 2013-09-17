package net.cobem.grizzly.habbohotel.roleplay;

import net.cobem.grizzly.communication.composers.rooms.SendRoomWhisperComposer;
import net.cobem.grizzly.habbohotel.effects.EffectType;
import net.cobem.grizzly.habbohotel.roleplay.WorkoutRunMonitor;
import net.cobem.grizzly.habbohotel.sessions.Session;

public class WorkoutMonitor implements Runnable {

   private Thread Thread;
   private int WaitTime = 0;
   private Session User;
   private WorkoutRunMonitor RunMonitor;


   public WorkoutMonitor(Session Session) {
      this.User = Session;
      this.RunMonitor = new WorkoutRunMonitor(Session);
   }

   public void run() {
      while(this.WaitTime > -1) {
         if(this.WaitTime == 0) {
            this.User.getRoleplay().upgradeStrength(1);
            this.User.effect(EffectType.PULSATING_GLOW);
            this.User.sendResponse(SendRoomWhisperComposer.compose(this.User.getHabbo().getID(), "You\'ve leveled up to level " + this.User.getRoleplay().getStrength(), 0));
            this.WaitTime = this.User.getRoleplay().getStrength() * 2 - 1;
         } else {
            this.User.sendResponse(SendRoomWhisperComposer.compose(this.User.getHabbo().getID(), "You have " + this.WaitTime + " more minutes till you reach level " + (this.User.getRoleplay().getStrength() + 1), 0));
            this.User.effect(0);
            --this.WaitTime;
         }

         try {
            Thread.sleep(60000L);
         } catch (InterruptedException var2) {
            ;
         }
      }

   }

   public void start() {
      if(this.WaitTime == 0) {
         this.WaitTime = 2;
      }

      this.Thread = new Thread(this);
      this.Thread.start();
      this.RunMonitor.start();
   }

   public void stop() {
      this.Thread.stop();
      this.RunMonitor.stop();
   }

   public int getWaitTime() {
      return this.WaitTime + 1;
   }
}
