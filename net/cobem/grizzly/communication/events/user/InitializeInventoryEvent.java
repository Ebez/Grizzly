package net.cobem.grizzly.communication.events.user;

import net.cobem.grizzly.communication.EventRequest;
import net.cobem.grizzly.communication.GameEvent;
import net.cobem.grizzly.communication.composers.inventory.SendInventoryFloorsComposer;
import net.cobem.grizzly.communication.composers.inventory.SendInventoryWallsComposer;
import net.cobem.grizzly.communication.composers.user.SendBadgesComposer;
import net.cobem.grizzly.habbohotel.sessions.Session;

public class InitializeInventoryEvent implements GameEvent {

   public void compose(Session Session, EventRequest Request) {
      Session.sendResponse(SendInventoryFloorsComposer.compose(Session.getHabbo().getItems()));
      Session.sendResponse(SendInventoryWallsComposer.compose(Session.getHabbo().getItems()));
      Session.sendResponse(SendBadgesComposer.compose(Session.getHabbo().getBadges(), Session.getHabbo().getEquippedBadges()));
   }
}
