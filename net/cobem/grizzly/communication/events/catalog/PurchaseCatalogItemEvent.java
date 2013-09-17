package net.cobem.grizzly.communication.events.catalog;

import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.communication.EventRequest;
import net.cobem.grizzly.communication.GameEvent;
import net.cobem.grizzly.communication.composers.catalog.PurchaseCatalogItemComposer;
import net.cobem.grizzly.communication.composers.inventory.ShowNewInventoryCountComposer;
import net.cobem.grizzly.communication.composers.inventory.UpdateUserInventoryComposer;
import net.cobem.grizzly.communication.composers.misc.SendFrankNotificationComposer;
import net.cobem.grizzly.communication.composers.user.SendCreditsComposer;
import net.cobem.grizzly.communication.composers.user.SendCurrenciesComposer;
import net.cobem.grizzly.habbohotel.catalog.CatalogItem;
import net.cobem.grizzly.habbohotel.sessions.Session;

public class PurchaseCatalogItemEvent implements GameEvent {

   public void compose(Session Session, EventRequest Request) {
      int Page = Request.readInt().intValue();
      int Item = Request.readInt().intValue();
      String Extra = Request.readString();
      int RealAmount = Request.readInt().intValue();
      int Bonus = 0;
      boolean ErrorExists = false;
      if(RealAmount % (new Integer(Grizzly.getConfig().get("habbo.catalog.offers.every.x"))).intValue() == 0) {
         Bonus = RealAmount / (new Integer(Grizzly.getConfig().get("habbo.catalog.offers.every.x"))).intValue();
      }

      Grizzly.write("Gets Bonus:" + Bonus);
      CatalogItem mItem = Grizzly.getHabboHotel().getCatalog().getItemByID(Item);
      if(Session.getHabbo().getCredits() < mItem.Cost * RealAmount) {
         Session.sendResponse(SendFrankNotificationComposer.compose("No tienes Suficientes Creditos!"));
         ErrorExists = true;
      }

      if(Session.getHabbo().getPixels() < mItem.PixelCost * RealAmount) {
         Session.sendResponse(SendFrankNotificationComposer.compose("No tienes suficientes pixeles!"));
         ErrorExists = true;
      }

      if(mItem.CurrencyCost * RealAmount > Session.getHabbo().getCurrency()) {
         Session.sendResponse(SendFrankNotificationComposer.compose("You don\'t have enough exchange!"));
         ErrorExists = true;
      }

      Session.sendResponse(PurchaseCatalogItemComposer.compose(Page, Item, Extra, RealAmount, Session, ErrorExists, Bonus));
      if(!ErrorExists) {
         Session.sendResponse(ShowNewInventoryCountComposer.compose(mItem.Quantity * RealAmount, Session.getHabbo().getItems(), mItem.getBase(), Extra));
         Session.sendResponse(UpdateUserInventoryComposer.compose());
         Session.sendResponse(SendCreditsComposer.compose(Session.getHabbo().getCredits()));
         Session.sendResponse(SendCurrenciesComposer.compose(Session.getHabbo().getPixels(), Session.getHabbo().getCurrency()));
      }

   }
}
