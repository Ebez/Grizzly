package net.cobem.grizzly.plugins;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.habbohotel.sessions.Session;
import net.cobem.grizzly.plugins.HabboEvent;
import net.cobem.grizzly.plugins.HabboPlugin;
import net.cobem.grizzly.utils.Configuration;

public class PluginHandler {

   private List<File> PluginsList = new ArrayList();
   private List<HabboPlugin> Plugins = new ArrayList();


   public PluginHandler() {
      File[] var4;
      int var3 = (var4 = (new File((Grizzly.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "plugins/").replace("/bin", ""))).listFiles()).length;

      for(int var2 = 0; var2 < var3; ++var2) {
         File Plugin = var4[var2];
         if(!Plugin.getName().contains("properties")) {
            this.PluginsList.add(Plugin);
         }
      }

      this.InitializePlugins();
   }

   private void InitializePlugins() {
      Grizzly.write("");
      Iterator var2 = this.PluginsList.iterator();

      while(var2.hasNext()) {
         File Plugin = (File)var2.next();

         try {
            Configuration e = new Configuration();
            e.load((Grizzly.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "plugins").replace("/bin", "") + "/plugin.properties");
            URLClassLoader Child = URLClassLoader.newInstance(new URL[]{Plugin.toURI().toURL()}, this.getClass().getClassLoader());
            String ConfigName = Plugin.getName().replace(".jar", "");

            try {
               e.get(ConfigName + ".namespace");
            } catch (NullPointerException var11) {
               continue;
            }

            if(e.get(ConfigName + ".enabled").contains("true")) {
               String Namespace = e.get(ConfigName + ".namespace");
               String Author = e.get(ConfigName + ".author");
               String Version = e.get(ConfigName + ".version");
               Class PluginClass = Class.forName(Namespace, true, Child);
               HabboPlugin PluginInstance = (HabboPlugin)PluginClass.asSubclass(HabboPlugin.class).newInstance();
               Grizzly.write(Plugin.getName() + " v" + Version + " by " + Author);
               PluginInstance.OnRegister(ConfigName, Author, Version);
               this.Plugins.add(PluginInstance);
            }
         } catch (Exception var12) {
            var12.printStackTrace();
         }
      }

      Grizzly.write("");
      Grizzly.write("Loaded " + this.Plugins.size() + " plugins");
   }

   public boolean RunEvent(HabboEvent Event, Session Session) {
      Iterator var4 = this.Plugins.iterator();

      while(var4.hasNext()) {
         HabboPlugin Plugin = (HabboPlugin)var4.next();
         Plugin.OnEvent(Event, Session);
      }

      return true;
   }

   public boolean DestructPlugin(Session Session, String Name) {
      int Count = this.Plugins.size();
      Iterator var5 = this.Plugins.iterator();

      while(var5.hasNext()) {
         HabboPlugin Plugin = (HabboPlugin)var5.next();

         try {
            if(Plugin.GrabName().equalsIgnoreCase(Name)) {
               Grizzly.write(Plugin.GrabName() + " has been destructed by " + Session.getHabbo().getUsername());
               this.Plugins.remove(Plugin);
               break;
            }
         } catch (Exception var7) {
            Grizzly.write(var7.getStackTrace());
         }
      }

      return Count != this.Plugins.size();
   }
}
