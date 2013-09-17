package net.cobem.grizzly.utils;

import java.util.Date;
import net.cobem.grizzly.utils.DateFormat;

public class DateHandler {

   // $FF: synthetic field
   private static int[] $SWITCH_TABLE$net$cobem$grizzly$utils$DateFormat;


   public int GetDateFrom(Date DateTime, DateFormat Format) {
      switch($SWITCH_TABLE$net$cobem$grizzly$utils$DateFormat()[Format.ordinal()]) {
      case 1:
         return (int)(((new Date()).getTime() - DateTime.getTime()) / 1000L);
      case 2:
         return (int)((new Date()).getTime() / 60000L - DateTime.getTime() / 60000L);
      case 3:
         return 0;
      default:
         return 0;
      }
   }

   // $FF: synthetic method
   static int[] $SWITCH_TABLE$net$cobem$grizzly$utils$DateFormat() {
      if($SWITCH_TABLE$net$cobem$grizzly$utils$DateFormat != null) {
         return $SWITCH_TABLE$net$cobem$grizzly$utils$DateFormat;
      } else {
         int[] var0 = new int[DateFormat.values().length];

         try {
            var0[DateFormat.Hours.ordinal()] = 3;
         } catch (NoSuchFieldError var3) {
            ;
         }

         try {
            var0[DateFormat.Minutes.ordinal()] = 2;
         } catch (NoSuchFieldError var2) {
            ;
         }

         try {
            var0[DateFormat.Seconds.ordinal()] = 1;
         } catch (NoSuchFieldError var1) {
            ;
         }

         $SWITCH_TABLE$net$cobem$grizzly$utils$DateFormat = var0;
         return var0;
      }
   }
}
