package net.cobem.grizzly.habbohotel.roleplay;

import net.cobem.grizzly.communication.composers.rooms.LeaveRoomComposer;
import net.cobem.grizzly.habbohotel.rooms.Room;

public class LeavingMonitor implements Runnable {

   private Thread Thread;
   private int WaitTime = 0;
   private int UserID = 0;
   private Room Room;


   public LeavingMonitor(int UserID) {
      this.UserID = UserID;
   }

   public void run() {
      while(this.WaitTime > 0) {
         --this.WaitTime;

         try {
            Thread.sleep(1000L);
         } catch (InterruptedException var2) {
            ;
         }
      }

      this.Room.sendMessage(LeaveRoomComposer.compose(this.UserID));
   }

   public void startLeave(Room Room) {
      this.Room = Room;
      this.WaitTime = 15;
      this.Thread = new Thread(this);
      this.Thread.start();
   }
}
