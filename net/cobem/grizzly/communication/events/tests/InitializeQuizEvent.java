package net.cobem.grizzly.communication.events.tests;

import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.communication.EventRequest;
import net.cobem.grizzly.communication.GameEvent;
import net.cobem.grizzly.communication.composers.misc.SendNotificationComposer;
import net.cobem.grizzly.communication.composers.tests.DisplayTestContainerComposer;
import net.cobem.grizzly.habbohotel.sessions.Session;

public class InitializeQuizEvent implements GameEvent {

   public void compose(Session Session, EventRequest Request) {
      String Type = Request.readString();
      if(Session.getHabbo().hasBadge(Grizzly.getConfig().get("habbo.quiz." + Type.toLowerCase() + ".reward"))) {
         Session.sendResponse(SendNotificationComposer.compose("You already won the badge for this quiz!"));
      } else {
         Session.sendResponse(DisplayTestContainerComposer.compose(Type));
      }
   }
}
