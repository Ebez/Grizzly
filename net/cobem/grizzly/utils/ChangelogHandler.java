package net.cobem.grizzly.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import net.cobem.grizzly.Grizzly;

public class ChangelogHandler {

   StringBuilder Log;


   public ChangelogHandler() throws IOException {
      try {
         BufferedReader e = new BufferedReader(new FileReader("changelog.txt"));
         this.Log = new StringBuilder();
         String Line = "";
         String NewLine = System.getProperty("line.separator");

         while((Line = e.readLine()) != null) {
            if(Line.contains("[" + Grizzly.Version.String() + "]")) {
               this.Log.append(Line.replace("[" + Grizzly.Version.String() + "] ", ""));
               this.Log.append(NewLine);
            }
         }
      } catch (FileNotFoundException var4) {
         Grizzly.write("Cannot find the changelog!");
      }

   }

   public String[] GrabAll() {
      return this.Log.toString().split(System.getProperty("line.separator"));
   }

   public String GrabLatest() {
      String Return = null;
      int i = 0;
      String[] var6;
      int var5 = (var6 = this.Log.toString().split(System.getProperty("line.separator"))).length;

      for(int var4 = 0; var4 < var5; ++var4) {
         String Line = var6[var4];
         if(i > this.Log.toString().split(System.getProperty("line.separator")).length) {
            ++i;
         } else {
            Return = Line;
         }
      }

      return Return;
   }

   public void WriteTo() {}
}
