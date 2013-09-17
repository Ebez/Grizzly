package net.cobem.grizzly.habbohotel.rooms;

import java.util.ArrayList;
import java.util.Iterator;
import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.habbohotel.rooms.Room;

public class RoomMonitor implements Runnable {

   private boolean Running;
   private Thread RoomZ = new Thread(this);


   public RoomMonitor() {
      this.RoomZ.start();
      this.Running = true;
   }

   public void run() {
      while(this.Running) {
         ArrayList EmptyRooms = new ArrayList();
         Iterator var3 = Grizzly.getHabboHotel().getRooms().getPopulated().values().iterator();

         Room mRoom;
         while(var3.hasNext()) {
            mRoom = (Room)var3.next();
            if(mRoom.getParty().size() <= 0) {
               EmptyRooms.add(mRoom);
            } else {
               mRoom.process();
            }
         }

         while(EmptyRooms.iterator().hasNext()) {
            mRoom = (Room)EmptyRooms.iterator().next();
            mRoom.removeAllRobots();
            Grizzly.getHabboHotel().getRooms().getPopulated().values().remove(mRoom);
         }

         try {
            Thread.sleep(500L);
         } catch (InterruptedException var4) {
            ;
         }
      }

   }

   public void Stop() {
      this.Running = false;
   }
}
