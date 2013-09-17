package net.cobem.grizzly.habbohotel.wired.effects;

import java.util.ArrayList;
import net.cobem.grizzly.communication.EventRequest;
import net.cobem.grizzly.habbohotel.rooms.items.FloorItem;
import net.cobem.grizzly.habbohotel.sessions.Session;
import net.cobem.grizzly.habbohotel.wired.WiredObject;

public class ChangeState implements WiredObject {

   public void save(Session Session, FloorItem Item, EventRequest Message) {
      int ItemID = Message.readInt().intValue();
      Message.readString();
      int ItemCount = Message.readInt().intValue();
      ArrayList Items = new ArrayList();
      String ArrayString = "";

      for(int i = 0; i < ItemCount; ++i) {
         int ID = Message.readInt().intValue();
         FloorItem mItem = Session.getActor().CurrentRoom.getItemByID(ID);
         if(mItem != null) {
            Items.add(mItem);
            ArrayString = ArrayString + mItem.ID + ";";
         }
      }

      Item.ExtraData = ArrayString;
      Item.saveState();
   }
}
