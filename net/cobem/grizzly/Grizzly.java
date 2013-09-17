package net.cobem.grizzly;

import java.awt.Toolkit;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import net.cobem.grizzly.console.OutputHandler;
import net.cobem.grizzly.habbohotel.HabboHotel;
import net.cobem.grizzly.licensing.LicenseServer;
import net.cobem.grizzly.licensing.ResponseType;
import net.cobem.grizzly.net.Connection;
import net.cobem.grizzly.plugins.PluginHandler;
import net.cobem.grizzly.storage.DatabaseHandler;
import net.cobem.grizzly.utils.Configuration;
import net.cobem.grizzly.utils.DateFormat;
import net.cobem.grizzly.utils.DateHandler;
import net.cobem.grizzly.utils.GCMonitor;
import net.cobem.grizzly.utils.ShutdownHandler;
import net.cobem.grizzly.utils.Version;
import org.apache.log4j.BasicConfigurator;

public class Grizzly {

   public static Version Version = new Version(1, 0, 7, 6);
   public static String HabboBuild = "RELEASE63-201302071600-466653663";
   public static Date EmulatorStart;
   private static Configuration Config;
   private static DateHandler DateHandler;
   private static Connection Connection;
   private static DatabaseHandler DatabaseHandler;
   private static HabboHotel HabboHotel;
   private static PluginHandler PluginHandler;
   private static ExecutorService ExecutorService;
   private static Thread GCMonitor;
   public static OutputHandler out;


   public static void main(String[] args) {
      BasicConfigurator.configure();
      EmulatorStart = new Date();
      System.out.println("_|                 BIONIC                                              |");
      System.out.println("_|    BIONIC                                     BIONIC       -       |");
      System.out.println("_|             +            BIONIC         -                           |");
      System.out.println("_|                                                   BIONIC          |");
      System.out.println("_|          BIONIC        +                                           |");
      System.out.println("_|                                              BIONIC               |");
      System.out.println("_|    -                             BIONIC                  +          |");
      System.out.println("_|          BIONIC                        +                           |");
      System.out.println("_|                          BIONIC                 BIONIC            |");
      System.out.println(Version.String());
      System.out.println("Built for " + HabboBuild);
      System.out.println("Written by Ebez");
      System.out.println("Credits:Ihton, Grizzly Developers, Neutrino Developers, Legion Developers");
      System.out.println();
      DateHandler = new DateHandler();
      out = new OutputHandler();
      Config = new Configuration();
      if(Config.load("server.properties")) {
         write("Config parsed in " + DateHandler.GetDateFrom(EmulatorStart, DateFormat.Seconds) + "s");
      } else {
         write("Config could not be found!! It should be \'/server.properties\'");
         System.exit(1);
      }

      Connection = new Connection(Integer.parseInt(Config.get("net.grizzly.port")));
      if(Connection.Listen()) {
         write("Netty started listening on port (" + Config.get("net.grizzly.port") + ") in " + DateHandler.GetDateFrom(EmulatorStart, DateFormat.Seconds) + "s");
      } else {
         write("Either net.grizzly.port cannot be found in your properties or the port is not opened!");
         System.exit(1);
      }

      DatabaseHandler = new DatabaseHandler();
      if(DatabaseHandler.connect(Config)) {
         write("JDBC with BoneCP started in " + DateHandler.GetDateFrom(EmulatorStart, DateFormat.Seconds) + "s");
      } else {
         write("Connection to your database has failed! Check your details in server.properties!");
         System.exit(1);
      }

      try {
         HabboHotel = new HabboHotel();
         HabboHotel.load();
      } catch (SQLException var2) {
         ;
      }

      write("HabboHotel Environment started in " + DateHandler.GetDateFrom(EmulatorStart, DateFormat.Seconds) + "s");
      getStorage().execute("UPDATE server_users SET online = \'0\'");
      write("Starting Garbage Collection Monitor... ", true, false);
      GCMonitor = new Thread(new GCMonitor());
      GCMonitor.setPriority(1);
      GCMonitor.start();
      write("Done!", false, true);
      write("Grizzly - Initialized in " + DateHandler.GetDateFrom(EmulatorStart, DateFormat.Seconds) + "s");
      Toolkit.getDefaultToolkit().beep();
      System.out.println();
      write("Checking license..", true, false);
      LicenseServer licenseServer = new LicenseServer();
      if(licenseServer.getResponse().equals(ResponseType.CONNECTION)) {
         write("Can\'t connect to Grizzly\'s license server, contact cobejohnson000@gmail.com if problem persists!");
         Toolkit.getDefaultToolkit().beep();
         System.exit(0);
      } else if(licenseServer.getResponse().equals(ResponseType.ERROR)) {
         write("Your Grizzly license seems to be invalid, if you believe this is a dire problem contact cobejohnson000@gmail.com.");
         Toolkit.getDefaultToolkit().beep();
         System.exit(0);
      } else {
         if(licenseServer.getResponse().equals(ResponseType.SUCCESS)) {
            write(" Success", false, true);
         }

         Runtime.getRuntime().addShutdownHook(new Thread(new ShutdownHandler()));
      }
   }

   public static HabboHotel getHabboHotel() {
      return HabboHotel;
   }

   public static Configuration getConfig() {
      return Config;
   }

   public static DateHandler getDateHandler() {
      return DateHandler;
   }

   public static Connection getConnections() {
      return Connection;
   }

   public static DatabaseHandler getStorage() {
      return DatabaseHandler;
   }

   public static PluginHandler getPlugins() {
      return PluginHandler;
   }

   public static void write(Object Out) {
      if(Config.get("console.grizzly.showdate").equals("1")) {
         System.out.println("[" + (new SimpleDateFormat(Config.get("console.grizzly.date"))).format(new Date()) + "] " + Out);
      } else {
         System.out.println(Out);
      }

   }

   public static void write(Object Out, boolean ShowDate, boolean EndOfLine) {
      if(ShowDate) {
         if(Config.get("console.grizzly.showdate").equals("1")) {
            System.out.print("[" + (new SimpleDateFormat(Config.get("console.grizzly.date"))).format(new Date()) + "] " + Out);
         } else {
            System.out.print(Out);
         }
      } else {
         System.out.print(Out);
      }

      if(EndOfLine) {
         System.out.print("\n");
      }

   }

   public static String getMemory() {
      DecimalFormat DFormat = new DecimalFormat("0.0#%");
      NumberFormat MFormat = NumberFormat.getInstance();
      long TotalMemory = Runtime.getRuntime().totalMemory();
      long FreeMemory = Runtime.getRuntime().freeMemory();
      long UsedMemory = TotalMemory - FreeMemory;
      return MFormat.format(UsedMemory / 1024L) + "(" + DFormat.format((double)UsedMemory / (double)TotalMemory) + ")";
   }

   public static boolean tryParse(String str) {
      try {
         Integer.parseInt(str);
         return true;
      } catch (Exception var2) {
         return false;
      }
   }

   public static int rand(int Min, int Max) {
      return (new Random()).nextInt(Max - Min + 1) + Min;
   }

   public static boolean roleplayEnabled() {
      return getConfig().get("roleplay.commands.enabled").equals("1");
   }
}
