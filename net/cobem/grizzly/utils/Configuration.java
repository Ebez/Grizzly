package net.cobem.grizzly.utils;

import java.io.FileInputStream;
import java.util.Properties;

public class Configuration {

   private Properties Config = new Properties();


   public boolean load(String File) {
      try {
         this.Config.load(new FileInputStream(File));
         return true;
      } catch (Exception var3) {
         return false;
      }
   }

   public String get(String key) {
      return this.Config.getProperty(key).trim();
   }

   public int size() {
      return this.Config.size();
   }
}
