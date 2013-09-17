package net.cobem.grizzly.habbohotel.rooms.items.interactions.wired.effects;

import java.util.ArrayList;
import net.cobem.grizzly.communication.composers.wired.SendWiredEffectDialogComposer;
import net.cobem.grizzly.habbohotel.rooms.items.FloorItem;
import net.cobem.grizzly.habbohotel.rooms.items.interactions.Interaction;
import net.cobem.grizzly.habbohotel.sessions.Session;

public class WiredChangeFurniStateInteraction implements Interaction {

   public void onPlace(Session Session, Object Item) {}

   public void onRemove(Session Session, Object Item) {}

   public void onTrigger(Session Session, Object Item, int Request) {
      FloorItem RoomItem;
      String Furni;
      int var7;
      int var8;
      String[] var9;
      if(Request == 2) {
         RoomItem = (FloorItem)Item;
         if(!RoomItem.ExtraData.equals("") || RoomItem.ExtraData.length() >= 2) {
            String[] Furnis = RoomItem.ExtraData.split(";");
            if(Furnis.length == 0) {
               return;
            }

            var9 = Furnis;
            var8 = Furnis.length;

            for(var7 = 0; var7 < var8; ++var7) {
               Furni = var9[var7];
               FloorItem TargetItem = Session.getActor().CurrentRoom.getItemByID((new Integer(Furni)).intValue());
               TargetItem.changeState();
               TargetItem.serializeUpdate(Session.getActor().CurrentRoom);
               TargetItem.saveState();
            }
         }
      } else {
         RoomItem = (FloorItem)Item;
         ArrayList var11 = new ArrayList();
         if(RoomItem.ExtraData.split(";").length > 1) {
            var8 = (var9 = RoomItem.ExtraData.split(";")).length;

            for(var7 = 0; var7 < var8; ++var7) {
               Furni = var9[var7];
               var11.add(Session.getActor().CurrentRoom.getItemByID((new Integer(Furni)).intValue()));
            }
         }

         Session.sendResponse(SendWiredEffectDialogComposer.compose(5, RoomItem, var11, RoomItem.ExtraData, 0, 8, 0, 0, 0));
      }

   }

   public void onCycle(Object Item) {}
}
