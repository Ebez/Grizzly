package net.cobem.grizzly.communication.composers.inventory;

import java.util.Iterator;
import net.cobem.grizzly.communication.EventResponse;
import net.cobem.grizzly.communication.HeaderLibrary;
import net.cobem.grizzly.habbohotel.users.items.InventoryItem;
import net.cobem.grizzly.habbohotel.users.items.ItemHandler;

public class SendInventoryWallsComposer {

   public static EventResponse compose(ItemHandler Handler) {
      EventResponse Message = new EventResponse();
      Message.Initialize(HeaderLibrary.SendUserInventoryEvent);
      Message.addString("I");
      Message.addInt(Integer.valueOf(1));
      Message.addInt(Integer.valueOf(1));
      Message.addInt(Integer.valueOf(Handler.getWalls().size()));
      Iterator var3 = Handler.getWalls().values().iterator();

      while(var3.hasNext()) {
         InventoryItem Item = (InventoryItem)var3.next();
         Message.addInt(Integer.valueOf(Item.ID));
         Message.addString(Item.getBase().Type.toUpperCase());
         Message.addInt(Integer.valueOf(Item.ID));
         Message.addInt(Integer.valueOf(Item.getBase().Sprite));
         if(Item.getBase().ItemTitle.contains("wallpaper")) {
            Message.addInt(Integer.valueOf(2));
         } else if(Item.getBase().ItemTitle.contains("a2")) {
            Message.addInt(Integer.valueOf(3));
         } else if(Item.getBase().ItemTitle.contains("landscape")) {
            Message.addInt(Integer.valueOf(4));
         } else {
            Message.addInt(Integer.valueOf(1));
         }

         Message.addInt(Integer.valueOf(0));
         if((Item.getBase().ItemTitle.contains("floor") || Item.getBase().ItemTitle.contains("wall")) && Item.getBase().ItemTitle.split("_").length >= 3) {
            Message.addString(Item.getBase().ItemTitle.split("_")[2]);
         } else {
            Message.addString("");
         }

         Message.addBool(Item.getBase().Recyclable);
         Message.addBool(Item.getBase().Tradeable);
         Message.addBool(Boolean.valueOf(true));
         Message.addBool(Item.getBase().Sellable);
         Message.addInt(Integer.valueOf(-1));
      }

      return Message;
   }
}
