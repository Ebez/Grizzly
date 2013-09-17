package net.cobem.grizzly.habbohotel.roleplay;

import net.cobem.grizzly.habbohotel.roleplay.RoleplayObject;

public class TaxiMonitor implements Runnable {

   private Thread Taxi = new Thread(this);
   private boolean Active;
   private int WaitTime = 0;
   private RoleplayObject Interactor;


   public TaxiMonitor(RoleplayObject Obj) {
      this.Taxi.start();
      this.Active = true;
      this.Interactor = Obj;
   }

   public void run() {
      while(this.Active) {
         if(this.WaitTime != 0) {
            --this.WaitTime;
         }

         if(this.WaitTime == 1) {
            this.Interactor.runTaxi();
         }

         try {
            Thread.sleep(1000L);
         } catch (InterruptedException var2) {
            ;
         }
      }

   }

   public void setWaitingTime(int Time) {
      this.WaitTime = Time;
   }
}
