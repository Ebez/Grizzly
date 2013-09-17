package net.cobem.grizzly.communication.events.tests;

import java.util.ArrayList;
import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.communication.EventRequest;
import net.cobem.grizzly.communication.GameEvent;
import net.cobem.grizzly.communication.composers.tests.DisplayTestResultsComposer;
import net.cobem.grizzly.communication.composers.user.SendAllowancesComposer;
import net.cobem.grizzly.habbohotel.sessions.Session;

public class CheckQuizEvent implements GameEvent {

   public void compose(Session Session, EventRequest Request) {
      String Type = Request.readString();
      Request.readInt();
      ArrayList WrongAnswers = new ArrayList();
      if(Type.equals("SafetyQuiz1")) {
         Session.getHabbo().addBadge(Grizzly.getConfig().get("habbo.quiz.safetyquiz1.reward"));
         Session.sendResponse(SendAllowancesComposer.compose(true));
      } else {
         for(int i = 1; i < 6; ++i) {
            int Answer = Request.readInt().intValue();
            switch(i) {
            case 1:
               if(Answer != 3) {
                  WrongAnswers.add(Integer.valueOf(5));
               }
               break;
            case 2:
               if(Answer != 3) {
                  WrongAnswers.add(Integer.valueOf(7));
               }
               break;
            case 3:
               if(Answer != 2) {
                  WrongAnswers.add(Integer.valueOf(0));
               }
               break;
            case 4:
               if(Answer != 1) {
                  WrongAnswers.add(Integer.valueOf(1));
               }
               break;
            case 5:
               if(Answer != 1) {
                  WrongAnswers.add(Integer.valueOf(6));
               }
            }
         }

         if(WrongAnswers.size() == 0) {
            Session.getHabbo().addBadge(Grizzly.getConfig().get("habbo.quiz.habboway1.reward"));
         }
      }

      Session.sendResponse(DisplayTestResultsComposer.compose(Type, WrongAnswers));
   }
}
