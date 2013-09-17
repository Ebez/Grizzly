package net.cobem.grizzly.habbohotel.rooms.items.interactions.misc;

import net.cobem.grizzly.habbohotel.effects.EffectType;
import net.cobem.grizzly.habbohotel.rooms.items.FloorItem;
import net.cobem.grizzly.habbohotel.rooms.items.interactions.Interaction;
import net.cobem.grizzly.habbohotel.sessions.Session;

public class WaterInteraction implements Interaction {

   public void onPlace(Session Session, Object Item) {}

   public void onRemove(Session Session, Object Item) {}

   public void onTrigger(Session Session, Object Item, int Request) {
      FloorItem RoomItem = (FloorItem)Item;
      String var5;
      switch((var5 = RoomItem.getBase().ItemTitle).hashCode()) {
      case 1067087135:
         if(var5.equals("bw_water_1")) {
            Session.effect(EffectType.SWIMMING_IN_WATER);
         }

         return;
      case 1067087136:
         if(!var5.equals("bw_water_2")) {
            return;
         }
         break;
      case 1352548566:
         if(!var5.equals("hween10_pond")) {
            return;
         }
         break;
      default:
         return;
      }

      Session.effect(EffectType.SWIMMING_IN_WATER2);
   }

   public void onCycle(Object Item) {}
}
