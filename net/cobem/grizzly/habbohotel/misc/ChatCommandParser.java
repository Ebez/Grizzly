package net.cobem.grizzly.habbohotel.misc;

import gnu.trove.map.hash.THashMap;
import java.util.Map;
import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.habbohotel.misc.ChatCommand;
import net.cobem.grizzly.habbohotel.misc.commands.Badge;
import net.cobem.grizzly.habbohotel.misc.commands.Ban;
import net.cobem.grizzly.habbohotel.misc.commands.CommandList;
import net.cobem.grizzly.habbohotel.misc.commands.Coords;
import net.cobem.grizzly.habbohotel.misc.commands.CreateBot;
import net.cobem.grizzly.habbohotel.misc.commands.Credits;
import net.cobem.grizzly.habbohotel.misc.commands.DestroyBot;
import net.cobem.grizzly.habbohotel.misc.commands.DestructPlugin;
import net.cobem.grizzly.habbohotel.misc.commands.Help;
import net.cobem.grizzly.habbohotel.misc.commands.OfficialStream;
import net.cobem.grizzly.habbohotel.misc.commands.Overrider;
import net.cobem.grizzly.habbohotel.misc.commands.Summon;
import net.cobem.grizzly.habbohotel.misc.commands.ToggleStatus;
import net.cobem.grizzly.habbohotel.roleplay.commands.Hit;
import net.cobem.grizzly.habbohotel.roleplay.commands.Taxi;
import net.cobem.grizzly.habbohotel.sessions.Session;

public class ChatCommandParser {

   private THashMap<String, ChatCommand> ChatCommands = new THashMap();


   public ChatCommandParser() {
      this.add("help", new Help());
      this.add("commands", new CommandList());
      this.add("about", new Help());
      this.add("ban", new Ban());
      this.add("coords", new Coords());
      this.add("summon", new Summon());
      this.add("destructplugin", new DestructPlugin());
      this.add("destroyplugin", new DestructPlugin());
      this.add("deloadplugin", new DestructPlugin());
      this.add("bot", new CreateBot());
      this.add("destroybot", new DestroyBot());
      this.add("togglestatus", new ToggleStatus());
      this.add("credits", new Credits());
      this.add("officialstream", new OfficialStream());
      this.add("badge", new Badge());
      this.add("override", new Overrider());
      if(Grizzly.roleplayEnabled()) {
         this.add("taxi", new Taxi());
         this.add("hit", new Hit());
         this.add("punch", new Hit());
      }

   }

   public void add(String Name, ChatCommand Command) {
      this.ChatCommands.put(Name, Command);
   }

   public boolean parse(Session mSession, String Command) {
      String CommandName = this.splitCommand(Command, true)[0];
      if(!this.ChatCommands.containsKey(CommandName.toLowerCase())) {
         return false;
      } else if(mSession.getHabbo().getRank() < ((ChatCommand)this.ChatCommands.get(CommandName)).rank()) {
         return false;
      } else {
         ((ChatCommand)this.ChatCommands.get(CommandName)).execute(mSession, this.splitCommand(Command, false));
         return true;
      }
   }

   public Map<String, ChatCommand> getList() {
      return this.ChatCommands;
   }

   private String[] splitCommand(String Command, boolean Start) {
      String[] Split = Command.split(" ");
      if(Start) {
         return Split;
      } else {
         try {
            String Ex = Command.replace(Split[0], "").substring(1);
            return Ex.split(" ");
         } catch (Exception var5) {
            return null;
         }
      }
   }
}
