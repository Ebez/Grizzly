package net.cobem.grizzly.utils;

import net.cobem.grizzly.Grizzly;

public class ShutdownHandler implements Runnable {

   public void run() {
      Grizzly.getStorage().execute("UPDATE server_users SET online = \'0\'");
      Grizzly.write("Bye Bye");
   }
}
