package net.cobem.grizzly.habbohotel.stream;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.habbohotel.sessions.Session;
import net.cobem.grizzly.habbohotel.stream.StreamObject;
import net.cobem.grizzly.storage.DatabaseResult;
import net.cobem.grizzly.utils.UserInputFilter;

public class StreamHandler {

   public boolean changeMotto(Session Session) {
      Grizzly.getStorage().execute("INSERT INTO server_stream (type, user, date, message) VALUES (\'3\', \'" + Session.getHabbo().getID() + "\', \'" + (new Date()).getTime() + "\', \' \')");
      return true;
   }

   public boolean setStatus(Session Session, String Status) {
      Grizzly.getStorage().execute("INSERT INTO server_stream (type, user, date, message) VALUES (\'5\', \'" + Session.getHabbo().getID() + "\', " + "\'" + (new Date()).getTime() + "\', " + "\'" + UserInputFilter.filterString(Status.replace("\'", ""), false) + "\')");
      return true;
   }

   public boolean addFriend(Session Session, int FriendID) {
      Grizzly.getStorage().execute("INSERT INTO server_stream (type, user, date, message) VALUES (\'0\', \'" + Session.getHabbo().getID() + "\', \'" + (new Date()).getTime() + "\', \'" + FriendID + "\')");
      return true;
   }

   public boolean addOfficial(String Message) {
      Grizzly.getStorage().execute("INSERT INTO server_stream (type, user, date, message, official) VALUES (\'4\', \'-1\', \'" + (new Date()).getTime() + "\', " + "\'" + UserInputFilter.filterString(Message.replace("\'", ""), false) + "\', \'1\')");
      return true;
   }

   public List<StreamObject> getRegular() {
      ArrayList ReturnList = new ArrayList();
      DatabaseResult GetStream = Grizzly.getStorage().query("SELECT * FROM server_stream WHERE official = \'0\' ORDER BY id DESC LIMIT 12");
      ResultSet Stream = GetStream.getTable();

      try {
         while(Stream.next()) {
            ReturnList.add(new StreamObject(Stream));
         }
      } catch (Exception var5) {
         var5.printStackTrace();
      }

      return ReturnList;
   }

   public List<StreamObject> getOfficial() {
      ArrayList ReturnList = new ArrayList();
      DatabaseResult GetStream = Grizzly.getStorage().query("SELECT * FROM server_stream WHERE official = \'1\' ORDER BY id DESC LIMIT 3");
      ResultSet Stream = GetStream.getTable();

      try {
         while(Stream.next()) {
            ReturnList.add(new StreamObject(Stream));
         }
      } catch (Exception var5) {
         ;
      }

      return ReturnList;
   }

   public StreamObject getStreamObject(int ID) {
      DatabaseResult GetStream = Grizzly.getStorage().query("SELECT * FROM server_stream WHERE id = \'" + ID + "\'");
      ResultSet Stream = GetStream.getRow();
      return new StreamObject(Stream);
   }
}
