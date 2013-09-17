package net.cobem.grizzly.habbohotel.wired.triggers;

import net.cobem.grizzly.communication.EventRequest;
import net.cobem.grizzly.habbohotel.rooms.items.FloorItem;
import net.cobem.grizzly.habbohotel.sessions.Session;
import net.cobem.grizzly.habbohotel.wired.WiredObject;
import net.cobem.grizzly.utils.UserInputFilter;

public class SayMessage implements WiredObject {

   public void save(Session Session, FloorItem Item, EventRequest Message) {
      Message.readInt();
      Message.readInt();
      String M = Message.readString();
      String Str = UserInputFilter.filterString(M, false);
      Item.ExtraData = Str;
      Item.saveState();
   }
}
