package net.cobem.grizzly.habbohotel.rooms.items.interactions.roleplay;

import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.habbohotel.rooms.items.interactions.Interaction;
import net.cobem.grizzly.habbohotel.sessions.Session;

public class WorkoutMachineInteraction implements Interaction {

   public void onPlace(Session Session, Object Item) {}

   public void onRemove(Session Session, Object Item) {}

   public void onTrigger(Session Session, Object Item, int Request) {
      if(Grizzly.roleplayEnabled() && !Session.getRoleplay().isWorkingOut()) {
         Session.getRoleplay().startWorkingOut();
      }

   }

   public void onCycle(Object Item) {}
}
