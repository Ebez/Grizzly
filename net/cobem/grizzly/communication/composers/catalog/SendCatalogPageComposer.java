package net.cobem.grizzly.communication.composers.catalog;

import java.util.Iterator;
import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.communication.EventResponse;
import net.cobem.grizzly.communication.HeaderLibrary;
import net.cobem.grizzly.habbohotel.catalog.CatalogItem;
import net.cobem.grizzly.habbohotel.catalog.CatalogLayout;
import net.cobem.grizzly.habbohotel.catalog.CatalogPage;
import net.cobem.grizzly.habbohotel.catalog.CatalogParent;

public class SendCatalogPageComposer {

   public static EventResponse compose(int PageID) {
      EventResponse Message = new EventResponse();
      Object TryPage = Grizzly.getHabboHotel().getCatalog().getPageByID(PageID);
      String Item;
      Iterator var5;
      CatalogItem Item1;
      if(TryPage instanceof CatalogPage) {
         CatalogPage Page = (CatalogPage)TryPage;
         if(!Page.Enabled) {
            return new EventResponse();
         }

         Message.Initialize(HeaderLibrary.ShowCatalogPageEvent);
         Message.addInt(Integer.valueOf(Page.ID));
         Message.addString(Page.Layout);
         Message.addInt(Integer.valueOf(Page.Headers.size()));
         var5 = Page.Headers.iterator();

         while(var5.hasNext()) {
            Item = (String)var5.next();
            Message.addString(Item);
         }

         Message.addInt(Integer.valueOf(Page.Texts.size()));
         var5 = Page.Texts.iterator();

         while(var5.hasNext()) {
            Item = (String)var5.next();
            Message.addString(Item);
         }

         if(Page.Layout.equals("roomads")) {
            Message.addInt(Integer.valueOf(1));
            Message.addInt(Integer.valueOf(7384));
            Message.addString("room_ad_plus_badge");
            Message.addInt(Integer.valueOf(0));
            Message.addString("");
            Message.addInt(Integer.valueOf(3840));
            Message.addInt(Integer.valueOf(0));
            Message.addInt(Integer.valueOf(1));
            Message.addString("b");
            Message.addString("RADZZ");
         } else {
            Message.addInt(Integer.valueOf(Grizzly.getHabboHotel().getCatalog().getItems(Page.ID).size()));
            var5 = Grizzly.getHabboHotel().getCatalog().getItems(Page.ID).values().iterator();

            while(var5.hasNext()) {
               Item1 = (CatalogItem)var5.next();
               Message.addInt(Integer.valueOf(Item1.ID));
               Message.addString(Item1.getBase().PublicTitle);
               Message.addInt(Integer.valueOf(Item1.Cost));
               if(Item1.PixelCost > 0) {
                  Message.addInt(Integer.valueOf(Item1.PixelCost));
                  Message.addInt(Integer.valueOf(0));
               } else if(Item1.CurrencyCost > 0) {
                  Message.addInt(Integer.valueOf(Item1.CurrencyCost));
                  Message.addInt(new Integer(Grizzly.getConfig().get("habbo.currency.default.id")));
               } else {
                  Message.addInt(Integer.valueOf(0));
                  Message.addInt(Integer.valueOf(0));
               }

               Message.addBool(Boolean.valueOf(true));
               Message.addInt(Integer.valueOf(1));
               Message.addString(Item1.getBase().Type);
               Message.addInt(Integer.valueOf(Item1.getBase().Sprite));
               Message.addString(Item1.getPresetFlag());
               Message.addInt(Integer.valueOf(Item1.Quantity));
               Message.addBool(Item1.LTD);
               if(Item1.LTD.booleanValue()) {
                  Message.addInt(Integer.valueOf(Item1.LTD_Stock));
                  Message.addInt(Integer.valueOf(Item1.LTD_Stock - Item1.LTD_Purchased));
               }

               Message.addInt(Integer.valueOf(0));
               Message.addBool(Boolean.valueOf(!Item1.Title.contains("cf_") && !Item1.LTD.booleanValue()));
            }
         }

         Message.addInt(Integer.valueOf(-1));
         Message.addBool(Boolean.valueOf(false));
         if(!Grizzly.getHabboHotel().getCatalog().getCache().containsKey(Integer.valueOf(Page.ID)) && !Page.containsLTD()) {
            Grizzly.getHabboHotel().getCatalog().getCache().put(Integer.valueOf(Page.ID), Message);
         }
      } else if(TryPage instanceof CatalogParent) {
         CatalogParent Page1 = (CatalogParent)TryPage;
         if(!Page1.Enabled) {
            return new EventResponse();
         }

         Message.Initialize(HeaderLibrary.ShowCatalogPageEvent);
         Message.addInt(Integer.valueOf(Page1.ID));
         Message.addString(Page1.Layout);
         Message.addInt(Integer.valueOf(Page1.Headers.size()));
         var5 = Page1.Headers.iterator();

         while(var5.hasNext()) {
            Item = (String)var5.next();
            Message.addString(Item);
         }

         Message.addInt(Integer.valueOf(Page1.Texts.size()));
         var5 = Page1.Texts.iterator();

         while(var5.hasNext()) {
            Item = (String)var5.next();
            Message.addString(Item);
         }

         if(Page1.Layout == CatalogLayout.roomads) {
            Message.addInt(Integer.valueOf(1));
            Message.addInt(Integer.valueOf(7384));
            Message.addString("room_ad_plus_badge");
            Message.addInt(Integer.valueOf(70));
            Message.addString("");
            Message.addInt(Integer.valueOf(3840));
            Message.addInt(Integer.valueOf(0));
            Message.addInt(Integer.valueOf(1));
            Message.addString("");
            Message.addString("RADZZ");
         } else {
            Message.addInt(Integer.valueOf(Grizzly.getHabboHotel().getCatalog().getItems(Page1.ID).size()));
            var5 = Grizzly.getHabboHotel().getCatalog().getItems(Page1.ID).values().iterator();

            while(var5.hasNext()) {
               Item1 = (CatalogItem)var5.next();
               Message.addInt(Integer.valueOf(Item1.ID));
               Message.addString(Item1.getBase().PublicTitle);
               Message.addInt(Integer.valueOf(Item1.Cost));
               if(Item1.PixelCost > 0) {
                  Message.addInt(Integer.valueOf(Item1.PixelCost));
                  Message.addInt(Integer.valueOf(0));
               } else if(Item1.CurrencyCost > 0) {
                  Message.addInt(Integer.valueOf(Item1.CurrencyCost));
                  Message.addInt(new Integer(Grizzly.getConfig().get("habbo.currency.default.id")));
               } else {
                  Message.addInt(Integer.valueOf(0));
                  Message.addInt(Integer.valueOf(0));
               }

               Message.addBool(Boolean.valueOf(true));
               Message.addInt(Integer.valueOf(1));
               Message.addString(Item1.getBase().Type);
               Message.addInt(Integer.valueOf(Item1.getBase().Sprite));
               Message.addString(Item1.getPresetFlag());
               Message.addInt(Integer.valueOf(Item1.Quantity));
               Message.addBool(Item1.LTD);
               if(Item1.LTD.booleanValue()) {
                  Message.addInt(Integer.valueOf(Item1.LTD_Stock));
                  Message.addInt(Integer.valueOf(Item1.LTD_Stock - Item1.LTD_Purchased));
               }

               Message.addInt(Integer.valueOf(0));
               Message.addBool(Boolean.valueOf(!Item1.Title.contains("cf_") && !Item1.LTD.booleanValue()));
            }
         }

         Message.addInt(Integer.valueOf(-1));
         Message.addBool(Boolean.valueOf(false));
         if(!Grizzly.getHabboHotel().getCatalog().getCache().containsKey(Integer.valueOf(Page1.ID)) && !Page1.containsLTD()) {
            Grizzly.getHabboHotel().getCatalog().getCache().put(Integer.valueOf(Page1.ID), Message);
         }
      }

      return Message;
   }
}
