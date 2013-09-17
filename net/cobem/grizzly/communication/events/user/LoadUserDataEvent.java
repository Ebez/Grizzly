package net.cobem.grizzly.communication.events.user;

import java.text.SimpleDateFormat;
import java.util.Date;
import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.communication.EventRequest;
import net.cobem.grizzly.communication.GameEvent;
import net.cobem.grizzly.communication.composers.messenger.SendFriendListComposer;
import net.cobem.grizzly.communication.composers.messenger.SendPendingFriendRequestsComposer;
import net.cobem.grizzly.communication.composers.moderation.InitializeModToolsComposer;
import net.cobem.grizzly.communication.composers.user.SendAllowancesComposer;
import net.cobem.grizzly.communication.composers.user.SendBadgesComposer;
import net.cobem.grizzly.communication.composers.user.SendCitizenshipComposer;
import net.cobem.grizzly.communication.composers.user.SendClubComposer;
import net.cobem.grizzly.communication.composers.user.SendClubRankComposer;
import net.cobem.grizzly.communication.composers.user.SendCreditsComposer;
import net.cobem.grizzly.communication.composers.user.SendCurrenciesComposer;
import net.cobem.grizzly.communication.composers.user.SendHotelViewWelcomeComposer;
import net.cobem.grizzly.communication.composers.user.SendUserInformationComposer;
import net.cobem.grizzly.habbohotel.sessions.Session;
import net.cobem.grizzly.habbohotel.users.UserDAO;

public class LoadUserDataEvent implements GameEvent {

   public void compose(Session Session, EventRequest Request) {
      if(!Session.getChannel().isWritable()) {
         Session.getChannel().disconnect();
      } else if(UserDAO.isBanned(Session.getHabbo().getID())) {
         Session.getChannel().disconnect();
      } else {
         if(Session.getHabbo().getRank() >= 5) {
            Session.sendResponse(InitializeModToolsComposer.compose());
         }

         Session.getHabbo().getHome();
         Session.sendResponse(SendClubRankComposer.compose(Session.getHabbo().getRank()));
         Session.sendResponse(SendUserInformationComposer.compose(Session));
         Session.sendResponse(SendHotelViewWelcomeComposer.compose());
         Session.sendResponse(SendAllowancesComposer.compose(Session.getHabbo().hasBadge(Grizzly.getConfig().get("habbo.quiz.safetyquiz1.reward").trim())));
         Session.getHabbo().startMessenger();
         Session.sendResponse(SendCurrenciesComposer.compose(Session.getHabbo().getPixels(), Session.getHabbo().getCurrency()));
         Session.sendResponse(SendCreditsComposer.compose(Session.getHabbo().getCredits()));
         Session.sendResponse(SendClubComposer.compose(100));
         Session.sendResponse(SendFriendListComposer.compose(Session.getHabbo().getRank(), Session.getHabbo().getMessenger()));
         Session.sendResponse(SendPendingFriendRequestsComposer.compose(Session.getHabbo().getMessenger()));
         Session.sendResponse(SendBadgesComposer.compose(Session.getHabbo().getBadges(), Session.getHabbo().getEquippedBadges()));
         Session.getHabbo().refreshMessenger(true);
         if(!Session.getHabbo().hasBadge(Grizzly.getConfig().get("habbo.quiz.safetyquiz1.reward"))) {
            Session.sendResponse(SendCitizenshipComposer.compose());
         }

         Grizzly.getStorage().execute("UPDATE server_users SET online = \'1\', last_active = \'" + (new SimpleDateFormat("M-d-yyyy")).format(new Date()) + "\' WHERE id = \'" + Session.getHabbo().getID() + "\'");
      }
   }
}
