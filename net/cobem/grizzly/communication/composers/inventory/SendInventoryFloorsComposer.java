package net.cobem.grizzly.communication.composers.inventory;

import java.util.Iterator;
import net.cobem.grizzly.communication.EventResponse;
import net.cobem.grizzly.communication.HeaderLibrary;
import net.cobem.grizzly.habbohotel.users.items.InventoryItem;
import net.cobem.grizzly.habbohotel.users.items.ItemHandler;

public class SendInventoryFloorsComposer {

   public static EventResponse compose(ItemHandler Handler) {
      EventResponse Message = new EventResponse();
      Message.Initialize(HeaderLibrary.SendUserInventoryEvent);
      Message.addString("S");
      Message.addInt(Integer.valueOf(1));
      Message.addInt(Integer.valueOf(1));
      Message.addInt(Integer.valueOf(Handler.getFloors().size()));
      Iterator var3 = Handler.getFloors().values().iterator();

      while(var3.hasNext()) {
         InventoryItem Item = (InventoryItem)var3.next();
         Message.addInt(Integer.valueOf(Item.ID));
         Message.addString(Item.getBase().Type.toUpperCase());
         Message.addInt(Integer.valueOf(Item.ID));
         Message.addInt(Integer.valueOf(Item.getBase().Sprite));
         Message.addInt(Integer.valueOf(1));
         Message.addInt(Integer.valueOf(0));
         Message.addString("");
         Message.addBool(Item.getBase().Recyclable);
         Message.addBool(Item.getBase().Tradeable);
         Message.addBool(Boolean.valueOf(true));
         Message.addBool(Item.getBase().Sellable);
         Message.addInt(Integer.valueOf(-1));
         Message.addString("");
         Message.addInt(Integer.valueOf(3341));
      }

      return Message;
   }
}
