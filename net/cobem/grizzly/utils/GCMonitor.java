package net.cobem.grizzly.utils;


public class GCMonitor implements Runnable {

   public void run() {
      while(true) {
         System.gc();

         try {
            Thread.sleep(7000L);
         } catch (InterruptedException var2) {
            ;
         }
      }
   }
}
