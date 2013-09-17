package net.cobem.grizzly.communication.events.navigator;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.communication.EventRequest;
import net.cobem.grizzly.communication.GameEvent;
import net.cobem.grizzly.communication.composers.navigation.SendUserRoomsComposer;
import net.cobem.grizzly.habbohotel.rooms.Room;
import net.cobem.grizzly.habbohotel.sessions.Session;
import net.cobem.grizzly.storage.DatabaseResult;
import net.cobem.grizzly.utils.UserInputFilter;

public class SearchRoomEvent implements GameEvent {

   public void compose(Session Session, EventRequest Request) {
      HashMap Return = new HashMap();
      String Search = Request.readString();
      String Query = Search.split(":")[0];
      String Value = null;
      if(Search.contains(":")) {
         Value = UserInputFilter.filterString(Search.split(":")[1], false);
      }

      DatabaseResult GrabRooms;
      if(Query != null && Value != null) {
         switch(Query.hashCode()) {
         case 106164915:
            if(Query.equals("owner")) {
               GrabRooms = Grizzly.getStorage().preparedQuery("SELECT id FROM server_users WHERE username = ?", Arrays.asList(new String[]{Value}));
               int Results2 = GrabRooms.getInt().intValue();
               DatabaseResult GrabRooms1 = Grizzly.getStorage().preparedQuery("SELECT * FROM server_rooms WHERE owner = ?", Arrays.asList(new String[]{String.valueOf(Results2)}));

               try {
                  ResultSet Results1 = GrabRooms1.getTable();

                  while(Results1.next()) {
                     Return.put(Integer.valueOf(Results1.getInt("id")), new Room(Results1));
                  }
               } catch (SQLException var13) {
                  ;
               }
            }
            break;
         case 1919900571:
            if(!Query.equals("user_count")) {
               ;
            }
         }
      } else {
         GrabRooms = Grizzly.getStorage().preparedQuery("SELECT * FROM server_rooms WHERE name LIKE %?%", Arrays.asList(new String[]{Search}));

         try {
            ResultSet Results = GrabRooms.getTable();

            while(Results.next()) {
               Return.put(Integer.valueOf(Results.getInt("id")), new Room(Results));
            }
         } catch (SQLException var12) {
            ;
         }
      }

      Session.sendResponse(SendUserRoomsComposer.compose(Return));
   }
}
