package net.cobem.grizzly.communication.composers.inventory;

import net.cobem.grizzly.communication.EventResponse;
import net.cobem.grizzly.communication.HeaderLibrary;
import net.cobem.grizzly.habbohotel.furni.Furniture;
import net.cobem.grizzly.habbohotel.users.items.ItemHandler;

public class ShowNewInventoryCountComposer {

   public static EventResponse compose(int Amount, ItemHandler Handler, Furniture Base, String Extra) {
      EventResponse Message = new EventResponse();
      Message.Initialize(HeaderLibrary.ShowNewInventoryCountEvent);
      Message.addInt(Integer.valueOf(1));
      Message.addInt(Integer.valueOf(Base.Type.equals("s")?1:2));
      Message.addInt(Integer.valueOf(Amount));

      for(int i = 0; i != Amount; ++i) {
         Message.addInt(Integer.valueOf(Handler.add(Base, true, Extra)));
      }

      return Message;
   }
}
