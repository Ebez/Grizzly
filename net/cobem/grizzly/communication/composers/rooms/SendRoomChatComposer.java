package net.cobem.grizzly.communication.composers.rooms;

import net.cobem.grizzly.communication.EventResponse;
import net.cobem.grizzly.communication.HeaderLibrary;

public class SendRoomChatComposer {

   public static EventResponse compose(int ID, String Str, int Smile, int Color) {
      EventResponse Message = new EventResponse();
      Message.Initialize(HeaderLibrary.SendRoomChatEvent);
      Message.addInt(Integer.valueOf(ID));
      Message.addString(Str);
      Message.addInt(Integer.valueOf(Smile));
      Message.addInt(Integer.valueOf(Color));
      Message.addInt(Integer.valueOf(0));
      Message.addInt(Integer.valueOf(-1));
      return Message;
   }
}
