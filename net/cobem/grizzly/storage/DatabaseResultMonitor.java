package net.cobem.grizzly.storage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.cobem.grizzly.storage.DatabaseResult;

public class DatabaseResultMonitor implements Runnable {

   public List<DatabaseResult> ResultsOpen = new ArrayList();


   public boolean AddTo(DatabaseResult Result) {
      this.ResultsOpen.add(Result);
      return true;
   }

   public void run() {
      while(this.ResultsOpen.size() > 0) {
         List var1 = this.ResultsOpen;
         synchronized(this.ResultsOpen) {
            Iterator Iterator = this.ResultsOpen.iterator();

            while(Iterator.hasNext()) {
               DatabaseResult Result = (DatabaseResult)Iterator.next();
               if(Result.RanQuery && Result.Opened) {
                  Result.close();
                  this.ResultsOpen.remove(Result);
               }
            }
         }

         try {
            Thread.sleep(5000L);
         } catch (InterruptedException var4) {
            ;
         }
      }

   }
}
