package net.cobem.grizzly.communication.composers.catalog;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.communication.EventResponse;
import net.cobem.grizzly.communication.HeaderLibrary;
import net.cobem.grizzly.habbohotel.catalog.CatalogPage;
import net.cobem.grizzly.habbohotel.catalog.CatalogParent;

public class SendCatalogComposer {

   public static EventResponse compose(int Rank) {
      EventResponse Message = new EventResponse();
      LinkedHashMap Primary = Grizzly.getHabboHotel().getCatalog().getPrimary();
      int Size = 0;
      Iterator var5 = Primary.values().iterator();

      CatalogParent Parent;
      while(var5.hasNext()) {
         Parent = (CatalogParent)var5.next();
         if(Parent.Rank <= Rank) {
            ++Size;
         }
      }

      Message.Initialize(HeaderLibrary.ShowCatalogIndexEvent);
      Message.addBool(Boolean.valueOf(true));
      Message.addInt(Integer.valueOf(0));
      Message.addInt(Integer.valueOf(0));
      Message.addInt(Integer.valueOf(-1));
      Message.addString("root");
      Message.addBool(Boolean.valueOf(false));
      Message.addBool(Boolean.valueOf(false));
      Message.addInt(Integer.valueOf(Size));
      var5 = Primary.values().iterator();

      while(var5.hasNext()) {
         Parent = (CatalogParent)var5.next();
         if(Parent.Rank <= Rank) {
            Map Secondary = Grizzly.getHabboHotel().getCatalog().getSecondary(Parent.ID);
            Message.addBool(Boolean.valueOf(true));
            Message.addInt(Integer.valueOf(Parent.IconColor));
            Message.addInt(Integer.valueOf(Parent.IconImage));
            Message.addInt(Integer.valueOf(Parent.ID));
            Message.addString(Parent.Title.toLowerCase().replace(" ", "_"));
            Message.addString(Parent.Title);
            Message.addInt(Integer.valueOf(Secondary.size()));
            Iterator var8 = Secondary.values().iterator();

            while(var8.hasNext()) {
               CatalogPage Kid = (CatalogPage)var8.next();
               Message.addBool(Boolean.valueOf(true));
               Message.addInt(Integer.valueOf(Kid.IconColor));
               Message.addInt(Integer.valueOf(Kid.IconImage));
               Message.addInt(Integer.valueOf(Kid.ID));
               Message.addString(Kid.Title.toLowerCase().replace(" ", "_"));
               Message.addString(Kid.Title);
               Message.addInt(Integer.valueOf(0));
            }
         }
      }

      Message.addBool(Boolean.valueOf(true));
      return Message;
   }
}
