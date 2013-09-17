package net.cobem.grizzly.communication.events.guild;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.communication.EventRequest;
import net.cobem.grizzly.communication.EventResponse;
import net.cobem.grizzly.communication.GameEvent;
import net.cobem.grizzly.communication.HeaderLibrary;
import net.cobem.grizzly.communication.composers.rooms.SendRoomUsersComposer;
import net.cobem.grizzly.communication.composers.user.SendCreditsComposer;
import net.cobem.grizzly.habbohotel.sessions.Session;
import net.cobem.grizzly.utils.UserInputFilter;

public class FinishCreateGuildEvent implements GameEvent {

   public void compose(Session Session, EventRequest Request) {
      ArrayList States = new ArrayList();
      String Name = UserInputFilter.filterString(Request.readString(), false);
      String Description = UserInputFilter.filterString(Request.readString(), true);
      int Room = Request.readInt().intValue();
      int Color = Request.readInt().intValue();
      int SecondColor = Request.readInt().intValue();
      Request.readInt();
      int Base = Request.readInt().intValue();
      int BaseColor = Request.readInt().intValue();
      int Count = Request.readInt().intValue();
      String StateString = "";

      for(int Image = 0; Image < Count * 3; ++Image) {
         int HexCode = Request.readInt().intValue();
         States.add(Integer.valueOf(HexCode));
         StateString = StateString + HexCode + ";";
      }

      String var19 = Grizzly.getHabboHotel().getGuilds().getImage(Base, Color, States);
      String var20 = Grizzly.getHabboHotel().getGuilds().getHEX(Color);
      String SecondHex = Grizzly.getHabboHotel().getGuilds().getHEX(SecondColor);
      String CreationDate = String.valueOf((new Date()).getTime());
      Grizzly.getStorage().preparedExecute("INSERT INTO server_guilds (title, owner, description, room, first_color, second_color, base, base_color, states, creation_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", Arrays.asList(new String[]{Name, String.valueOf(Session.getHabbo().getID()), Description, String.valueOf(Room), String.valueOf(Color), String.valueOf(SecondColor), String.valueOf(Base), String.valueOf(BaseColor), StateString, CreationDate}));
      int Guild = Grizzly.getStorage().getLastEntry("server_guilds", "owner = \'" + Session.getHabbo().getID() + "\'");
      Grizzly.getStorage().preparedExecute("INSERT INTO server_guild_members (guild, user, rank) VALUES (?, ?, ?)", Arrays.asList(new String[]{String.valueOf(Guild), String.valueOf(Session.getHabbo().getID()), String.valueOf(2)}));
      EventResponse Message = new EventResponse();
      Message.Initialize(HeaderLibrary.CatalogPurchaseItemEvent);
      Message.addInt(Integer.valueOf(6165));
      Message.addString("CREATE_GUILD");
      Message.addInt(Integer.valueOf(10));
      Message.addInt(Integer.valueOf(0));
      Message.addInt(Integer.valueOf(0));
      Message.addBool(Boolean.valueOf(false));
      Message.addInt(Integer.valueOf(0));
      Message.addInt(Integer.valueOf(0));
      Message.addBool(Boolean.valueOf(false));
      Session.sendResponse(Message);
      Session.getHabbo().setCredits(Session.getHabbo().getCredits() - 10);
      Session.getHabbo().append();
      Session.sendResponse(SendCreditsComposer.compose(Session.getHabbo().getCredits()));
      Message.Initialize(1635);
      Message.addInt(Integer.valueOf(1));
      Message.addInt(Integer.valueOf(Guild));
      Message.addString(Name);
      Message.addString(var19);
      Message.addString(var20);
      Message.addString(SecondHex);
      Message.addBool(Boolean.valueOf(true));
      Session.sendResponse(Message);
      if(Session.getHabbo().getGuilds().size() == 1) {
         Session.getHabbo().setGuild(Guild);
         Session.getHabbo().append();
      }

      Message.Initialize(0);
      Message.addInt(Integer.valueOf(1));
      Message.addInt(Integer.valueOf(Guild));
      Message.addString(var19);
      if(Session.getActor().inRoom().booleanValue()) {
         Session.getActor().CurrentRoom.sendMessage(SendRoomUsersComposer.compose(Session.getActor().CurrentRoom.getParty()));
      }

   }
}
