package net.cobem.grizzly.habbohotel.wired.effects;

import java.util.ArrayList;
import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.communication.EventRequest;
import net.cobem.grizzly.habbohotel.rooms.items.FloorItem;
import net.cobem.grizzly.habbohotel.sessions.Session;
import net.cobem.grizzly.habbohotel.wired.WiredObject;

public class MoveAndRotate implements WiredObject {

   public void save(Session Session, FloorItem Item, EventRequest Message) {
      int ItemID = Message.readInt().intValue();
      int Movement = Message.readInt().intValue();
      int Rotation = Message.readInt().intValue();
      Message.readBoolean();
      Message.readBoolean();
      int ItemCount = Message.readInt().intValue();
      Grizzly.write("IC: " + ItemCount);
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

      Item.ExtraData = Movement + ";" + Rotation + "_" + ArrayString;
      Grizzly.write(Item.ExtraData);
      Item.saveState();
   }
}
