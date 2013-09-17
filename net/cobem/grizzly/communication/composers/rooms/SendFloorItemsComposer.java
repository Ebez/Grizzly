package net.cobem.grizzly.communication.composers.rooms;

import gnu.trove.map.hash.THashMap;
import java.util.Iterator;
import net.cobem.grizzly.communication.EventResponse;
import net.cobem.grizzly.communication.HeaderLibrary;
import net.cobem.grizzly.habbohotel.rooms.items.FloorItem;

public class SendFloorItemsComposer {

   public static EventResponse compose(int OwnerID, String OwnerName, THashMap<Integer, FloorItem> Items) {
      EventResponse Message = new EventResponse();
      Message.Initialize(HeaderLibrary.InitializeFloorItemsEvent);
      Message.addInt(Integer.valueOf(1));
      Message.addInt(Integer.valueOf(OwnerID));
      Message.addString(OwnerName);
      Message.addInt(Integer.valueOf(Items.size()));
      Iterator var5 = Items.values().iterator();

      while(var5.hasNext()) {
         FloorItem Item = (FloorItem)var5.next();
         Message.addBody(Item);
         Message.addInt(Integer.valueOf(OwnerID));
      }

      return Message;
   }
}
