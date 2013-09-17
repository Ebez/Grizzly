package net.cobem.grizzly.communication.events.catalog;

import net.cobem.grizzly.communication.EventRequest;
import net.cobem.grizzly.communication.GameEvent;
import net.cobem.grizzly.communication.composers.catalog.SendCatalogComposer;
import net.cobem.grizzly.communication.composers.catalog.SendOfferConfigurationsComposer;
import net.cobem.grizzly.habbohotel.sessions.Session;

public class InitializeCatalogEvent implements GameEvent {

   public void compose(Session Session, EventRequest Request) {
      Session.sendResponse(SendCatalogComposer.compose(Session.getHabbo().getRank()));
      Session.sendResponse(SendOfferConfigurationsComposer.compose());
   }
}
