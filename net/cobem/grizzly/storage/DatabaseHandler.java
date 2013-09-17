package net.cobem.grizzly.storage;

import java.sql.Connection;
import java.util.List;
import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.storage.DatabasePool;
import net.cobem.grizzly.storage.DatabaseResult;
import net.cobem.grizzly.storage.DatabaseResultMonitor;
import net.cobem.grizzly.utils.Configuration;

public class DatabaseHandler {

   private DatabasePool Pool;
   private DatabaseResultMonitor ResultMonitor;
   private Thread Monitor;
   public int QueryCount;


   public boolean connect(Configuration Config) {
      try {
         Class.forName("com.mysql.jdbc.Driver");
         this.Pool = new DatabasePool(Config);
         this.ResultMonitor = new DatabaseResultMonitor();
         this.Monitor = new Thread(this.ResultMonitor);
         this.Monitor.setPriority(1);
         this.Monitor.start();
         this.QueryCount = 0;
         return true;
      } catch (Exception var3) {
         Grizzly.write(var3.getMessage());
         return false;
      }
   }

   public boolean execute(String SQL) {
      ++this.QueryCount;

      try {
         Throwable ex = null;
         Object var3 = null;

         try {
            Connection DBConnection = this.Pool.GrabConnection();

            try {
               DBConnection.createStatement().execute(SQL);
            } finally {
               if(DBConnection != null) {
                  DBConnection.close();
               }

            }

            return true;
         } catch (Throwable var12) {
            if(ex == null) {
               ex = var12;
            } else if(ex != var12) {
               ex.addSuppressed(var12);
            }

            throw ex;
         }
      } catch (Exception var13) {
         Grizzly.write(var13.getMessage());
         return false;
      }
   }

   public boolean preparedExecute(String param1, List<String> param2) {
      // $FF: Couldn't be decompiled
   }

   public DatabaseResult query(String param1) {
      // $FF: Couldn't be decompiled
   }

   public DatabaseResult preparedQuery(String param1, List<String> param2) {
      // $FF: Couldn't be decompiled
   }

   public int getLastEntry(String Table, String WhereClause) {
      DatabaseResult Entry;
      if(WhereClause != null) {
         Entry = this.query("SELECT id FROM " + Table + " WHERE " + WhereClause + " ORDER BY id DESC LIMIT 1");
         return Entry.getInt().intValue();
      } else {
         Entry = this.query("SELECT id FROM " + Table + " ORDER BY id DESC LIMIT 1");
         return Entry.getInt().intValue();
      }
   }

   public DatabasePool getPool() {
      return this.Pool;
   }

   public void resetCount() {
      this.QueryCount = 0;
   }
}
