package net.cobem.grizzly.habbohotel.rooms.items;

import java.util.Iterator;
import net.cobem.grizzly.habbohotel.rooms.Room;
import net.cobem.grizzly.habbohotel.rooms.items.FloorItem;
import net.cobem.grizzly.habbohotel.rooms.items.WallItem;

public class RoomItemCycle {

   private Room Room;


   public RoomItemCycle(Room Room) {
      this.Room = Room;
   }

   public void cycle() {
      Iterator var2 = this.Room.getFloors().values().iterator();

      while(var2.hasNext()) {
         FloorItem Item = (FloorItem)var2.next();
         Item.cycle();
      }

      var2 = this.Room.getWalls().values().iterator();

      while(var2.hasNext()) {
         WallItem Item1 = (WallItem)var2.next();
         Item1.cycle();
      }

   }
}
