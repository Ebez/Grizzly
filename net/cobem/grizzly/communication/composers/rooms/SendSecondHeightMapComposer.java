package net.cobem.grizzly.communication.composers.rooms;

import net.cobem.grizzly.communication.EventResponse;
import net.cobem.grizzly.communication.HeaderLibrary;

public class SendSecondHeightMapComposer {

   public static EventResponse compose(String HeightMap) {
      EventResponse Message = new EventResponse();
      Message.Initialize(HeaderLibrary.InitializeSecondHeightMapEvent);
      Message.addString(HeightMap);
      return Message;
   }
}
