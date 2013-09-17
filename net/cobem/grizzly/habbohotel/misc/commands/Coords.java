package net.cobem.grizzly.habbohotel.misc.commands;

import net.cobem.grizzly.communication.composers.misc.SendNotificationComposer;
import net.cobem.grizzly.habbohotel.misc.ChatCommand;
import net.cobem.grizzly.habbohotel.sessions.Session;

public class Coords implements ChatCommand {

   public int rank() {
      return 1;
   }

   public void execute(Session session, String[] args) {
      StringBuilder Alert = new StringBuilder();
      Alert.append("X:" + session.getActor().CurrentPosition.X + "\r");
      Alert.append("Y:" + session.getActor().CurrentPosition.Y + "\r");
      Alert.append("Z:" + session.getActor().CurrentPosition.Z + "\r");
      Alert.append("Rotation:" + session.getActor().Rotation + "\r");
      session.sendResponse(SendNotificationComposer.compose(Alert.toString()));
   }
}
