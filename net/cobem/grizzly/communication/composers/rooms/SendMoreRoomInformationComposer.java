package net.cobem.grizzly.communication.composers.rooms;

import net.cobem.grizzly.communication.EventResponse;
import net.cobem.grizzly.communication.HeaderLibrary;
import net.cobem.grizzly.habbohotel.rooms.Room;

public class SendMoreRoomInformationComposer {

   public static EventResponse compose(Room mRoom) {
      EventResponse Message = new EventResponse();
      Message.Initialize(HeaderLibrary.SendMoreRoomInformationEvent);
      Message.addBool(Boolean.valueOf(true));
      Message.addInt(Integer.valueOf(mRoom.ID));
      Message.addString(mRoom.Title);
      Message.addBool(Boolean.valueOf(true));
      Message.addInt(Integer.valueOf(mRoom.Owner));
      Message.addString(mRoom.OwnerByName);
      Message.addInt(Integer.valueOf(0));
      Message.addInt(Integer.valueOf(mRoom.getParty().size()));
      Message.addInt(Integer.valueOf(25));
      Message.addString(mRoom.Description);
      Message.addInt(Integer.valueOf(0));
      Message.addInt(Integer.valueOf(0));
      Message.addInt(Integer.valueOf(10));
      Message.addInt(Integer.valueOf(0));
      Message.addInt(Integer.valueOf(0));
      Message.addInt(Integer.valueOf(0));
      Message.addInt(Integer.valueOf(0));
      Message.addString("");
      Message.addInt(Integer.valueOf(0));
      Message.addInt(Integer.valueOf(0));
      Message.addInt(Integer.valueOf(0));
      Message.addInt(Integer.valueOf(0));
      Message.addBool(Boolean.valueOf(true));
      Message.addBool(Boolean.valueOf(true));
      Message.addInt(Integer.valueOf(0));
      Message.addInt(Integer.valueOf(0));
      Message.addBool(Boolean.valueOf(false));
      Message.addBool(Boolean.valueOf(false));
      Message.addBool(Boolean.valueOf(false));
      Message.addInt(Integer.valueOf(0));
      Message.addInt(Integer.valueOf(0));
      Message.addInt(Integer.valueOf(0));
      Message.addInt(Integer.valueOf(1));
      Message.addInt(Integer.valueOf(0));
      Message.addInt(Integer.valueOf(0));
      return Message;
   }
}
