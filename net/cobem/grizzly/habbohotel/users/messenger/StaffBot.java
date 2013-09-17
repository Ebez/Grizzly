package net.cobem.grizzly.habbohotel.users.messenger;

import java.util.HashMap;
import java.util.Map;
import net.cobem.grizzly.Grizzly;

public class StaffBot {

   private int ID = (new Integer(Grizzly.getConfig().get("habbo.messenger.staffbot.id"))).intValue();
   private String Username = Grizzly.getConfig().get("habbo.messenger.staffbot.username");
   private String Motto = Grizzly.getConfig().get("habbo.messenger.staffbot.motto");
   private String Look = Grizzly.getConfig().get("habbo.messenger.staffbot.look");
   private Map<String, String> Responses = new HashMap();


   public StaffBot() {
      this.fillResponses();
   }

   private void fillResponses() {}
}
