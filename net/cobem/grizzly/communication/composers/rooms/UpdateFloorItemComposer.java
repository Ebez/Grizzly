package net.cobem.grizzly.communication.composers.rooms;

import net.cobem.grizzly.communication.EventResponse;
import net.cobem.grizzly.communication.HeaderLibrary;
import net.cobem.grizzly.habbohotel.rooms.items.FloorItem;

public class UpdateFloorItemComposer {

   public static EventResponse compose(int ID, FloorItem Item, int Owner) {
      EventResponse Message = new EventResponse();
      Message.Initialize(HeaderLibrary.UpdateFloorItemEvent);
      Message.addInt(Integer.valueOf(ID));
      Message.addInt(Integer.valueOf(Item.getBase().Sprite));
      Message.addInt(Integer.valueOf(Item.X));
      Message.addInt(Integer.valueOf(Item.Y));
      Message.addInt(Integer.valueOf(Item.Rotation));
      Message.addString(Item.Height.toString());
      Message.addInt(Integer.valueOf(0));
      Message.addInt(Integer.valueOf(0));
      Message.addString("0");
      Message.addInt(Integer.valueOf(-1));
      Message.addInt(Integer.valueOf(Item.getBase().Interaction.equals("default")?1:0));
      Message.addInt(Integer.valueOf(Owner));
      return Message;
   }
}
