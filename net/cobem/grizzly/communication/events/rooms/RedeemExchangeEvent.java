package net.cobem.grizzly.communication.events.rooms;

import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.communication.EventRequest;
import net.cobem.grizzly.communication.GameEvent;
import net.cobem.grizzly.communication.composers.rooms.RemoveFloorItemComposer;
import net.cobem.grizzly.communication.composers.user.SendCreditsComposer;
import net.cobem.grizzly.habbohotel.rooms.items.FloorItem;
import net.cobem.grizzly.habbohotel.sessions.Session;

public class RedeemExchangeEvent implements GameEvent {

   public void compose(Session Session, EventRequest Request) {
      int ID = Request.readInt().intValue();
      FloorItem Item = Session.getActor().CurrentRoom.getItemByID(ID);
      if(Item.getBase().ItemTitle.startsWith("CF_") || Item.getBase().ItemTitle.startsWith("CFC_")) {
         int Value = (new Integer(Item.getBase().ItemTitle.split("_")[1])).intValue();
         Session.getHabbo().setCredits(Session.getHabbo().getCredits() + Value);
         Session.getHabbo().append();
         Session.sendResponse(SendCreditsComposer.compose(Session.getHabbo().getCredits()));
         Session.getActor().CurrentRoom.sendMessage(RemoveFloorItemComposer.compose(ID, Session.getHabbo().getID()));
         Grizzly.getStorage().execute("DELETE FROM server_items WHERE id = \'" + ID + "\'");
         Session.getActor().CurrentRoom.getFloors().remove(Integer.valueOf(ID));
         Session.getActor().CurrentRoom.getWiredUtility().removeFurni(Item);
      }

      Session.getActor().CurrentRoom.getMap().ReGenerateCollisionMap();
   }
}
