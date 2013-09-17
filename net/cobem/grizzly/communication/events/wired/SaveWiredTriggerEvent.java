package net.cobem.grizzly.communication.events.wired;

import net.cobem.grizzly.communication.EventRequest;
import net.cobem.grizzly.communication.GameEvent;
import net.cobem.grizzly.communication.composers.wired.SendWiredDialogFinishedComposer;
import net.cobem.grizzly.habbohotel.rooms.items.FloorItem;
import net.cobem.grizzly.habbohotel.sessions.Session;

public class SaveWiredTriggerEvent implements GameEvent {

   public void compose(Session Session, EventRequest Request) {
      int ItemID = Request.readInt().intValue();
      FloorItem Item = Session.getActor().CurrentRoom.getItemByID(ItemID);
      Session.getActor().CurrentRoom.getWiredUtility().saveWired(Session, Item, Request);
      Session.sendResponse(SendWiredDialogFinishedComposer.compose());
   }
}
