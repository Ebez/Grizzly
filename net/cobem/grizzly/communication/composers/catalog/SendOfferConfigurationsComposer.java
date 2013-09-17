package net.cobem.grizzly.communication.composers.catalog;

import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.communication.EventResponse;
import net.cobem.grizzly.communication.HeaderLibrary;

public class SendOfferConfigurationsComposer {

   public static EventResponse compose() {
      EventResponse Message = new EventResponse();
      Message.Initialize(HeaderLibrary.SendOffersConfigurationEvent);
      Message.addInt(new Integer(Grizzly.getConfig().get("habbo.catalog.max.amount.offerable")));
      Message.addInt(new Integer(Grizzly.getConfig().get("habbo.catalog.offers.every.x")));
      Message.addInt(new Integer(Grizzly.getConfig().get("habbo.catalog.increment.per.offer")));
      Message.addInt(new Integer(Grizzly.getConfig().get("habbo.catalog.offer.incrementor")));
      Message.addInt(Integer.valueOf(Grizzly.getConfig().get("habbo.catalog.offer.exceptions").split(",").length));
      String[] var4;
      int var3 = (var4 = Grizzly.getConfig().get("habbo.catalog.offer.exceptions").split(",")).length;

      for(int var2 = 0; var2 < var3; ++var2) {
         String Exception = var4[var2];
         Message.addInt(new Integer(Exception));
      }

      return Message;
   }
}
