package net.cobem.grizzly.habbohotel.rooms.items.interactions.misc;

import net.cobem.grizzly.habbohotel.effects.EffectType;
import net.cobem.grizzly.habbohotel.rooms.items.FloorItem;
import net.cobem.grizzly.habbohotel.rooms.items.interactions.Interaction;
import net.cobem.grizzly.habbohotel.sessions.Session;
import net.cobem.grizzly.habbohotel.users.GenderType;

public class RollerSkateInteraction implements Interaction {

   public void onPlace(Session Session, Object Item) {}

   public void onRemove(Session Session, Object Item) {}

   public void onTrigger(Session Session, Object Item, int Request) {
      FloorItem RoomItem = (FloorItem)Item;
      if(Session.getHabbo().getGender() == GenderType.M) {
         Session.effect(EffectType.BLUE_MALE_ROLLER_SKATES);
      } else {
         Session.effect(EffectType.PINK_FEMALE_ROLLER_SKATES);
      }

   }

   public void onCycle(Object Item) {}
}
