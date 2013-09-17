package net.cobem.grizzly.communication.events.catalog;

import net.cobem.grizzly.communication.EventRequest;
import net.cobem.grizzly.communication.GameEvent;
import net.cobem.grizzly.communication.composers.catalog.SendCatalogPageComposer;
import net.cobem.grizzly.habbohotel.sessions.Session;

public class InitializeCatalogPageEvent implements GameEvent {

   public void compose(Session Session, EventRequest Request) {
      int PageID = Request.readInt().intValue();
      Session.sendResponse(SendCatalogPageComposer.compose(PageID));
   }
}
