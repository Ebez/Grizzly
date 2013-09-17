package net.cobem.grizzly.communication.composers.rooms;

import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.communication.EventResponse;
import net.cobem.grizzly.communication.HeaderLibrary;

public class SendRoomShoutComposer {

   public static EventResponse compose(int ID, String Str, int Smile, int Color) {
      Grizzly.write(Integer.valueOf(Color));
      EventResponse Message = new EventResponse();
      Message.Initialize(HeaderLibrary.SendRoomShoutEvent);
      Message.addInt(Integer.valueOf(ID));
      Message.addString(Str);
      Message.addInt(Integer.valueOf(Smile));
      Message.addInt(Integer.valueOf(Color));
      Message.addInt(Integer.valueOf(0));
      Message.addInt(Integer.valueOf(-1));
      return Message;
   }
}
