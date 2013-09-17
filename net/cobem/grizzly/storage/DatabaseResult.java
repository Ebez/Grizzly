package net.cobem.grizzly.storage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import net.cobem.grizzly.Grizzly;

public class DatabaseResult {

   private ResultSet Result;
   private Connection Connection;
   private boolean ErrorOccured;
   public boolean Opened;
   public boolean RanQuery;
   private String Query;


   public DatabaseResult(String SQL, Connection Connection, ResultSet Result) {
      this.Query = SQL;
      this.Result = Result;
      this.Connection = Connection;
      this.Opened = true;
   }

   public boolean errorCheck() {
      if(this.ErrorOccured) {
         Grizzly.write("Error trying to run damage query: " + this.Query);
      }

      return this.ErrorOccured;
   }

   public boolean close() {
      try {
         this.Result.getStatement().close();
         this.Connection.close();
      } catch (SQLException var2) {
         ;
      }

      return true;
   }

   public String getString() {
      if(this.errorCheck()) {
         return null;
      } else {
         try {
            this.Result.first();
            this.RanQuery = true;
            return this.Result.getString(1);
         } catch (SQLException var2) {
            Grizzly.write(var2.getMessage());
            this.ErrorOccured = true;
            Grizzly.write("Error trying to run damage query: " + this.Query);
            return null;
         }
      }
   }

   public Integer getInt() {
      if(this.errorCheck()) {
         return Integer.valueOf(0);
      } else {
         try {
            this.Result.first();
            this.RanQuery = true;
            return Integer.valueOf(this.Result.getInt(1));
         } catch (SQLException var2) {
            Grizzly.write(var2.getMessage());
            this.ErrorOccured = true;
            Grizzly.write("Error trying to run damage query: " + this.Query);
            return Integer.valueOf(0);
         }
      }
   }

   public ResultSet getRow() {
      if(this.errorCheck()) {
         return null;
      } else {
         try {
            this.RanQuery = true;
            return this.Result.next()?this.Result:null;
         } catch (SQLException var2) {
            Grizzly.write(var2.getMessage());
            this.ErrorOccured = true;
            Grizzly.write("Error trying to run damage query: " + this.Query);
            return null;
         }
      }
   }

   public ResultSet getTable() {
      if(this.errorCheck()) {
         return null;
      } else {
         try {
            this.RanQuery = true;
            return this.Result;
         } catch (Exception var2) {
            Grizzly.write(var2.getMessage());
            this.ErrorOccured = true;
            Grizzly.write("Error trying to run damage query: " + this.Query);
            return null;
         }
      }
   }

   public int rowCount() {
      if(this.errorCheck()) {
         return 0;
      } else {
         try {
            this.RanQuery = true;

            int ex;
            for(ex = 0; this.Result.next(); ++ex) {
               ;
            }

            return ex;
         } catch (Exception var2) {
            Grizzly.write(var2.getMessage());
            this.ErrorOccured = true;
            Grizzly.write("Error trying to run damage query: " + this.Query);
            return 0;
         }
      }
   }
}
