package net.cobem.grizzly.communication.composers.wired;

import java.util.Iterator;
import java.util.List;
import net.cobem.grizzly.communication.EventResponse;
import net.cobem.grizzly.communication.HeaderLibrary;
import net.cobem.grizzly.habbohotel.rooms.items.FloorItem;

public class SendWiredTriggerDialogComposer {

   public static EventResponse compose(int First, FloorItem RoomItem, List<FloorItem> LinkedItems, String Data, int Second, int Third, int Fourth, int Fifth, int Sixth) {
      EventResponse Message = new EventResponse();
      Message.Initialize(HeaderLibrary.SendWiredTriggerDialogEvent);
      Message.addBool(Boolean.valueOf(false));
      Message.addInt(Integer.valueOf(First));
      Message.addInt(Integer.valueOf(LinkedItems.size()));
      Iterator var11 = LinkedItems.iterator();

      while(var11.hasNext()) {
         FloorItem Item = (FloorItem)var11.next();
         Message.addInt(Integer.valueOf(Item.ID));
      }

      Message.addInt(Integer.valueOf(RoomItem.getBase().Sprite));
      Message.addInt(Integer.valueOf(RoomItem.ID));
      Message.addString(Data);
      Message.addInt(Integer.valueOf(Second));
      Message.addInt(Integer.valueOf(Third));
      Message.addInt(Integer.valueOf(Fourth));
      Message.addInt(Integer.valueOf(Fifth));
      Message.addInt(Integer.valueOf(Sixth));
      Message.addInt(Integer.valueOf(0));
      return Message;
   }
}
