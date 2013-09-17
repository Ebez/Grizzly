package net.cobem.grizzly.storage;


public enum DatabaseResultType {

   String("String", 0),
   Int("Int", 1),
   Row("Row", 2),
   Table("Table", 3),
   RowCount("RowCount", 4);
   // $FF: synthetic field
   private static final DatabaseResultType[] ENUM$VALUES = new DatabaseResultType[]{String, Int, Row, Table, RowCount};


   private DatabaseResultType(String var1, int var2) {}
}
