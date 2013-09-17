package net.cobem.grizzly.communication.composers.tests;

import java.util.Iterator;
import java.util.List;
import net.cobem.grizzly.communication.EventResponse;
import net.cobem.grizzly.communication.HeaderLibrary;

public class DisplayTestResultsComposer {

   public static EventResponse compose(String Type, List<Integer> WrongAnswers) {
      EventResponse Message = new EventResponse();
      Message.Initialize(HeaderLibrary.DisplayTestResultsEvent);
      Message.addString(Type);
      Message.addInt(Integer.valueOf(WrongAnswers.size()));
      Iterator var4 = WrongAnswers.iterator();

      while(var4.hasNext()) {
         Integer Answer = (Integer)var4.next();
         Message.addInt(Answer);
      }

      return Message;
   }
}
