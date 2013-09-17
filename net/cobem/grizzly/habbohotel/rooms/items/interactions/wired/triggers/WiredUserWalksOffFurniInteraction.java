package net.cobem.grizzly.habbohotel.rooms.items.interactions.wired.triggers;

import java.util.ArrayList;
import net.cobem.grizzly.communication.composers.wired.SendWiredEffectDialogComposer;
import net.cobem.grizzly.habbohotel.rooms.items.FloorItem;
import net.cobem.grizzly.habbohotel.rooms.items.interactions.Interaction;
import net.cobem.grizzly.habbohotel.sessions.Session;

public class WiredUserWalksOffFurniInteraction implements Interaction {

   public void onPlace(Session Session, Object Item) {}

   public void onRemove(Session Session, Object Item) {}

   public void onTrigger(Session Session, Object Item, int Request) {
      if(Request != 2) {
         FloorItem RoomItem = (FloorItem)Item;
         ArrayList Furnis = new ArrayList();
         if(RoomItem.ExtraData.split(";").length > 1) {
            String[] var9;
            int var8 = (var9 = RoomItem.ExtraData.split(";")).length;

            for(int var7 = 0; var7 < var8; ++var7) {
               String Furni = var9[var7];
               Furnis.add(Session.getActor().CurrentRoom.getItemByID((new Integer(Furni)).intValue()));
            }
         }

         Session.sendResponse(SendWiredEffectDialogComposer.compose(5, RoomItem, Furnis, (String)null, 0, 8, 0, 0, 0));
      }

   }

   public void onCycle(Object Item) {}
}
