package net.cobem.grizzly.communication.composers.rooms;

import net.cobem.grizzly.communication.EventResponse;
import net.cobem.grizzly.communication.HeaderLibrary;

public class SendDrinkStatusComposer {

   public static EventResponse compose(int User, int Drink) {
      EventResponse Message = new EventResponse();
      Message.Initialize(HeaderLibrary.SendDrinkStatusEvent);
      Message.addInt(Integer.valueOf(User));
      Message.addInt(Integer.valueOf(Drink));
      return Message;
   }
}
