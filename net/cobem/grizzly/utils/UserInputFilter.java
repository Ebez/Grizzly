package net.cobem.grizzly.utils;


public class UserInputFilter {

   public static String filterString(String str, boolean allowLineBreaks) {
      str = str.replace('\u0001', ' ');
      str = str.replace('\u0002', ' ');
      str = str.replace('\u0003', ' ');
      str = str.replace('\t', ' ');
      if(!allowLineBreaks) {
         str = str.replace('\n', ' ');
         str = str.replace('\r', ' ');
      }

      return str;
   }

   public static Boolean isNullOrEmpty(String str) {
      return str == null?Boolean.valueOf(true):(str.length() == 0?Boolean.valueOf(true):Boolean.valueOf(false));
   }

   public static Boolean isNullOrEmpty(Integer str) {
      return str == null?Boolean.valueOf(true):(str.intValue() == -999999?Boolean.valueOf(true):Boolean.valueOf(false));
   }
}
