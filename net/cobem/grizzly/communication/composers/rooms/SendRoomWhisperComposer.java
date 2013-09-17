package net.cobem.grizzly.communication.composers.rooms;

import net.cobem.grizzly.communication.EventResponse;
import net.cobem.grizzly.communication.HeaderLibrary;

public class SendRoomWhisperComposer {

   public static EventResponse compose(int User, String Str, int Smile) {
      EventResponse Message = new EventResponse();
      Message.Initialize(HeaderLibrary.SendRoomWhisperEvent);
      Message.addInt(Integer.valueOf(User));
      Message.addString(Str);
      Message.addInt(Integer.valueOf(Smile));
      Message.addInt(Integer.valueOf(0));
      Message.addInt(Integer.valueOf(0));
      Message.addInt(Integer.valueOf(-1));
      return Message;
   }
}
