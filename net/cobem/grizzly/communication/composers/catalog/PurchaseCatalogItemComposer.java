package net.cobem.grizzly.communication.composers.catalog;

import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.communication.EventResponse;
import net.cobem.grizzly.communication.HeaderLibrary;
import net.cobem.grizzly.habbohotel.catalog.CatalogItem;
import net.cobem.grizzly.habbohotel.sessions.Session;

public class PurchaseCatalogItemComposer {

   public static EventResponse compose(int Page, int Item, String Extra, int RealAmount, Session Session, boolean ErrorExists, int Bonus) {
      EventResponse Message = new EventResponse();
      CatalogItem mItem = Grizzly.getHabboHotel().getCatalog().getItemByID(Item);
      if(mItem.Page == Page && !mItem.getBase().Interaction.equals("pet") && (!mItem.LTD.booleanValue() || mItem.LTD_Purchased < mItem.LTD_Stock)) {
         if(!ErrorExists) {
            Session.getHabbo().setCredits(Session.getHabbo().getCredits() - (mItem.Cost * RealAmount - mItem.Cost * Bonus));
            if(mItem.PixelCost > 0) {
               Session.getHabbo().setPixels(Session.getHabbo().getPixels() - mItem.PixelCost * RealAmount);
            }

            if(mItem.CurrencyCost > 0) {
               Session.getHabbo().setCurrency(Session.getHabbo().getCurrency() - mItem.CurrencyCost * RealAmount);
            }

            Session.getHabbo().append();
            if(mItem.LTD.booleanValue()) {
               Grizzly.getStorage().execute("UPDATE server_store_items SET ltd_purchased = ltd_purchased + 1 WHERE id = \'" + mItem.ID + "\'");
            }
         }

         Message.Initialize(HeaderLibrary.CatalogPurchaseItemEvent);
         Message.addInt(Integer.valueOf(mItem.ID));
         Message.addString(mItem.getBase().PublicTitle);
         Message.addInt(Integer.valueOf(mItem.Cost));
         Message.addInt(Integer.valueOf(0));
         Message.addInt(Integer.valueOf(0));
         Message.addBool(Boolean.valueOf(false));
         Message.addInt(Integer.valueOf(0));
         Message.addInt(Integer.valueOf(0));
         Message.addBool(Boolean.valueOf(false));
         return Message;
      } else {
         return null;
      }
   }
}
