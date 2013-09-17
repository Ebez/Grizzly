package net.cobem.grizzly.licensing;


public enum ResponseType {

   CONNECTION("CONNECTION", 0),
   ERROR("ERROR", 1),
   SUCCESS("SUCCESS", 2);
   // $FF: synthetic field
   private static final ResponseType[] ENUM$VALUES = new ResponseType[]{CONNECTION, ERROR, SUCCESS};


   private ResponseType(String var1, int var2) {}
}
