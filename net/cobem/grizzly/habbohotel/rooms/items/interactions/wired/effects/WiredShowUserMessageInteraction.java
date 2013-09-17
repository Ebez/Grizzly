package net.cobem.grizzly.habbohotel.rooms.items.interactions.wired.effects;

import java.util.ArrayList;
import net.cobem.grizzly.communication.composers.misc.SendNotificationComposer;
import net.cobem.grizzly.communication.composers.wired.SendWiredEffectDialogComposer;
import net.cobem.grizzly.habbohotel.rooms.items.FloorItem;
import net.cobem.grizzly.habbohotel.rooms.items.interactions.Interaction;
import net.cobem.grizzly.habbohotel.sessions.Session;

public class WiredShowUserMessageInteraction implements Interaction {

   public void onPlace(Session Session, Object Item) {}

   public void onRemove(Session Session, Object Item) {}

   public void onTrigger(Session Session, Object Item, int Request) {
      FloorItem RoomItem;
      if(Request == 2) {
         RoomItem = (FloorItem)Item;
         if(!RoomItem.ExtraData.equals("") || RoomItem.ExtraData.length() >= 2) {
            Session.sendResponse(SendNotificationComposer.compose(RoomItem.ExtraData));
         }
      } else {
         RoomItem = (FloorItem)Item;
         Session.sendResponse(SendWiredEffectDialogComposer.compose(0, RoomItem, new ArrayList(), RoomItem.ExtraData, 0, 0, 7, 0, 0));
      }

   }

   public void onCycle(Object Item) {}
}
