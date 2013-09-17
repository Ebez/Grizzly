package net.cobem.grizzly.communication.composers.user;

import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.communication.EventResponse;
import net.cobem.grizzly.communication.HeaderLibrary;

public class SendCurrenciesComposer {

   public static EventResponse compose(int Pixels, int Currency) {
      EventResponse Message = new EventResponse();
      int Count = 0;
      Message.Initialize(HeaderLibrary.InitializeCurrenciesEvent);
      if(Grizzly.getConfig().get("habbo.currency.pixels.enabled").equals("1")) {
         ++Count;
      }

      if(Grizzly.getConfig().get("habbo.currency.default.enabled").equals("1")) {
         ++Count;
      }

      Message.addInt(Integer.valueOf(Count));
      if(Grizzly.getConfig().get("habbo.currency.pixels.enabled").equals("1")) {
         Message.addInt(Integer.valueOf(0));
         Message.addInt(Integer.valueOf(Pixels));
      }

      if(Grizzly.getConfig().get("habbo.currency.default.enabled").equals("1")) {
         Message.addInt(new Integer(Grizzly.getConfig().get("habbo.currency.default.id")));
         Message.addInt(Integer.valueOf(Currency));
      }

      return Message;
   }
}
