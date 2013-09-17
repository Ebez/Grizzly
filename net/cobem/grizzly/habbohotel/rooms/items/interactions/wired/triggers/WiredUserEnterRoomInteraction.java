package net.cobem.grizzly.habbohotel.rooms.items.interactions.wired.triggers;

import java.util.ArrayList;
import net.cobem.grizzly.communication.composers.wired.SendWiredTriggerDialogComposer;
import net.cobem.grizzly.habbohotel.rooms.items.FloorItem;
import net.cobem.grizzly.habbohotel.rooms.items.interactions.Interaction;
import net.cobem.grizzly.habbohotel.sessions.Session;

public class WiredUserEnterRoomInteraction implements Interaction {

   public void onPlace(Session Session, Object Item) {}

   public void onRemove(Session Session, Object Item) {}

   public void onTrigger(Session Session, Object Item, int Request) {
      if(Request != 2) {
         FloorItem RoomItem = (FloorItem)Item;
         Session.sendResponse(SendWiredTriggerDialogComposer.compose(0, RoomItem, new ArrayList(), (String)null, 0, 0, 8, 0, 0));
      }

   }

   public void onCycle(Object Item) {}
}
