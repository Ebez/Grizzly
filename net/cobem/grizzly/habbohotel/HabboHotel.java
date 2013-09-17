package net.cobem.grizzly.habbohotel;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Random;
import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.communication.composers.misc.SendNotificationComposer;
import net.cobem.grizzly.habbohotel.catalog.CatalogHandler;
import net.cobem.grizzly.habbohotel.furni.FurnitureHandler;
import net.cobem.grizzly.habbohotel.guild.GuildHandler;
import net.cobem.grizzly.habbohotel.misc.ChatCommandParser;
import net.cobem.grizzly.habbohotel.rooms.RoomHandler;
import net.cobem.grizzly.habbohotel.rooms.items.interactions.InteractionHandler;
import net.cobem.grizzly.habbohotel.rooms.models.ModelHandler;
import net.cobem.grizzly.habbohotel.sessions.SessionHandler;
import net.cobem.grizzly.habbohotel.stream.StreamHandler;

public class HabboHotel {

   private SessionHandler SessionHandler;
   private CatalogHandler CatalogHandler;
   private FurnitureHandler FurnitureHandler;
   private RoomHandler RoomHandler;
   private ModelHandler ModelHandler;
   private ChatCommandParser ChatCommandParser;
   private InteractionHandler InteractionHandler;
   private StreamHandler StreamHandler;
   private GuildHandler GuildHandler;


   public void load() throws SQLException {
      Grizzly.write("");
      this.SessionHandler = new SessionHandler();
      this.CatalogHandler = new CatalogHandler();
      this.FurnitureHandler = new FurnitureHandler();
      this.ModelHandler = new ModelHandler();
      this.RoomHandler = new RoomHandler();
      this.InteractionHandler = new InteractionHandler();
      this.ChatCommandParser = new ChatCommandParser();
      this.StreamHandler = new StreamHandler();
      this.GuildHandler = new GuildHandler();
      Grizzly.write("");
   }

   public SessionHandler getSessions() {
      return this.SessionHandler;
   }

   public CatalogHandler getCatalog() {
      return this.CatalogHandler;
   }

   public FurnitureHandler getFurniture() {
      return this.FurnitureHandler;
   }

   public RoomHandler getRooms() {
      return this.RoomHandler;
   }

   public ModelHandler getModels() {
      return this.ModelHandler;
   }

   public ChatCommandParser getCommands() {
      return this.ChatCommandParser;
   }

   public InteractionHandler getInteractions() {
      return this.InteractionHandler;
   }

   public StreamHandler getStream() {
      return this.StreamHandler;
   }

   public GuildHandler getGuilds() {
      return this.GuildHandler;
   }

   public int parseSmile(String Str) {
      return !Str.contains(":)") && !Str.contains("=)") && !Str.contains(":D") && !Str.contains("=D")?(!Str.contains(":@") && !Str.contains(">:(") && !Str.contains(">:@")?(!Str.contains(":o") && !Str.contains("D:")?(!Str.contains(":(") && !Str.contains(":\'(") && !Str.contains("=(") && !Str.contains("=\'(")?0:4):3):2):1;
   }

   public String generateName() {
      String Pool = "abcdefghijklmnopqrstuvwxyz";
      String Name = "";

      for(int i = 0; i < 5; ++i) {
         if(Name.length() >= 3) {
            Name = Name + (new Random()).nextInt(26);
         } else {
            Name = Name + Pool.split("")[(new Random()).nextInt(26)];
         }
      }

      return Name;
   }

   public String generateLook() {
      HashMap RandomLooks = new HashMap();
      RandomLooks.put(Integer.valueOf(1), "hr-555-1344.hd-3098-5.lg-3200-108.ch-655-1322.sh-905-1339");
      RandomLooks.put(Integer.valueOf(2), "hr-3025-1348.hd-190-15.lg-285-1321.ch-3001-1324.sh-906-80");
      RandomLooks.put(Integer.valueOf(3), "hr-835-54.hd-628-10.lg-3023-69.ch-818-93.sh-3016-1320");
      RandomLooks.put(Integer.valueOf(4), "hr-115-41.hd-207-28.lg-3078-89.ch-808-1336.sh-300-72");
      RandomLooks.put(Integer.valueOf(5), "hr-505-44.hd-615-10.lg-700-83.ch-3113-74.sh-905-81");
      RandomLooks.put(Integer.valueOf(6), "hr-679-45.hd-180-1.lg-3116-66.ch-266.sh-908-64");
      RandomLooks.put(Integer.valueOf(7), "hr-165-40.hd-3092-9.lg-280-67.ch-3001-68.sh-295-69");
      RandomLooks.put(Integer.valueOf(8), "hr-890-46.hd-605-16.lg-3116-70.ch-675-73.sh-730-73");
      RandomLooks.put(Integer.valueOf(9), "hr-890-52.hd-629-20.lg-700-96.ch-665-95.sh-3068-1334");
      RandomLooks.put(Integer.valueOf(10), "hr-830-1342.hd-185-16.lg-285-101.ch-3030-90.sh-908-63");
      int Response = (new Random()).nextInt(RandomLooks.size());
      if(Response == 0) {
         ++Response;
      }

      if(Response > RandomLooks.size()) {
         --Response;
      }

      return (String)RandomLooks.get(Integer.valueOf(Response));
   }

   public void sendHotelAlert(String Str) {
      this.getSessions().sendHotelMessage(SendNotificationComposer.compose(Str));
   }

   public void refreshCatalog() {
      this.getCatalog().refresh();
   }
}
