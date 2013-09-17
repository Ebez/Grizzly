package net.cobem.grizzly.habbohotel.rooms.items.interactions.wired.effects;

import java.util.ArrayList;
import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.communication.composers.wired.SendWiredEffectDialogComposer;
import net.cobem.grizzly.habbohotel.pathfinding.Position;
import net.cobem.grizzly.habbohotel.rooms.items.FloorItem;
import net.cobem.grizzly.habbohotel.rooms.items.interactions.Interaction;
import net.cobem.grizzly.habbohotel.sessions.Session;

public class WiredTransportUserInteraction implements Interaction {

   public void onPlace(Session Session, Object Item) {}

   public void onRemove(Session Session, Object Item) {}

   public void onTrigger(Session Session, Object Item, int Request) {
      FloorItem RoomItem;
      if(Request == 2) {
         RoomItem = (FloorItem)Item;
         if(!RoomItem.ExtraData.equals("") || RoomItem.ExtraData.length() >= 2) {
            String[] Furnis = RoomItem.ExtraData.split(";");
            if(Furnis.length == 0) {
               return;
            }

            FloorItem Furni = Session.getActor().CurrentRoom.getItemByID((new Integer(Furnis[Grizzly.rand(0, Furnis.length - 1)])).intValue());
            if(Furni == null) {
               return;
            }

            Session.getActor().move(new Position(Furni.X, Furni.Y, (double)Furni.Height.floatValue()), Session.getActor().Rotation);
         }
      } else {
         RoomItem = (FloorItem)Item;
         ArrayList var10 = new ArrayList();
         if(RoomItem.ExtraData.split(";").length > 1) {
            String[] var9;
            int var8 = (var9 = RoomItem.ExtraData.split(";")).length;

            for(int var7 = 0; var7 < var8; ++var7) {
               String var11 = var9[var7];
               var10.add(Session.getActor().CurrentRoom.getItemByID((new Integer(var11)).intValue()));
            }
         }

         Session.sendResponse(SendWiredEffectDialogComposer.compose(5, RoomItem, var10, RoomItem.ExtraData, 0, 8, 0, 0, 0));
      }

   }

   public void onCycle(Object Item) {}
}
