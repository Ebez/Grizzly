package net.cobem.grizzly.console;


public enum OutputType {

   Info("Info", 0),
   Debug("Debug", 1),
   Warning("Warning", 2),
   Error("Error", 3);
   // $FF: synthetic field
   private static final OutputType[] ENUM$VALUES = new OutputType[]{Info, Debug, Warning, Error};


   private OutputType(String var1, int var2) {}
}
