package net.cobem.grizzly.communication.composers.user;

import java.util.Iterator;
import java.util.List;
import net.cobem.grizzly.communication.EventResponse;
import net.cobem.grizzly.communication.HeaderLibrary;

public class SendUserTagsComposer {

   public static EventResponse compose(int ID, List<String> Tags) {
      EventResponse Message = new EventResponse();
      Message.Initialize(HeaderLibrary.SendUserTagsEvent);
      Message.addInt(Integer.valueOf(ID));
      Message.addInt(Integer.valueOf(Tags.size()));
      Iterator var4 = Tags.iterator();

      while(var4.hasNext()) {
         String Tag = (String)var4.next();
         Message.addString(Tag);
      }

      return Message;
   }
}
