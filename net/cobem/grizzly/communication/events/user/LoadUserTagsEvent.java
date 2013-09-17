package net.cobem.grizzly.communication.events.user;

import java.util.ArrayList;
import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.communication.EventRequest;
import net.cobem.grizzly.communication.GameEvent;
import net.cobem.grizzly.communication.composers.user.SendUserTagsComposer;
import net.cobem.grizzly.habbohotel.sessions.Session;

public class LoadUserTagsEvent implements GameEvent {

   public void compose(Session Session, EventRequest Request) {
      if(Grizzly.roleplayEnabled()) {
         ArrayList Stats = new ArrayList();
         Stats.add("Health: " + Session.getRoleplay().getHealth());
         Stats.add("Strength: " + Session.getRoleplay().getStrength());
         if(Session.getRoleplay().isWorkingOut()) {
            Stats.add("Gym Time: " + Session.getRoleplay().getWorkoutMonitor().getWaitTime());
         }

         Session.sendResponse(SendUserTagsComposer.compose(Session.getHabbo().getID(), Stats));
      }

   }
}
