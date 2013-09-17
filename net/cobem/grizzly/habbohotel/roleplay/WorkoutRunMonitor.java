package net.cobem.grizzly.habbohotel.roleplay;

import net.cobem.grizzly.habbohotel.sessions.Session;

public class WorkoutRunMonitor implements Runnable {

   private Thread Thread;
   private Session User;
   public boolean Active = false;


   public WorkoutRunMonitor(Session Session) {
      this.User = Session;
      this.Thread = new Thread(this);
   }

   public void run() {
      while(true) {
         try {
            if(this.Active) {
               this.User.getActor().removeStatus("sit", Boolean.valueOf(false));
               this.User.getActor().removeStatus("lay", Boolean.valueOf(false));
               this.User.getActor().addStatus("mv", this.User.getActor().CurrentPosition.X + "," + this.User.getActor().CurrentPosition.Y + "," + Double.toString(this.User.getActor().CurrentPosition.Z), true);
               Thread.sleep(120L);
               continue;
            }
         } catch (Exception var2) {
            this.stop();
            this.start();
         }

         return;
      }
   }

   public void start() {
      this.Active = true;
      this.Thread = new Thread(this);
      this.Thread.start();
   }

   public void stop() {
      this.Active = false;
      this.Thread.stop();
   }
}
