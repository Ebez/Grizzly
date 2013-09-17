package net.cobem.grizzly.storage;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;
import java.sql.Connection;
import java.sql.SQLException;
import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.utils.Configuration;

public class DatabasePool {

   private BoneCP Pool;


   public DatabasePool(Configuration Config) {
      BoneCPConfig CPConfig = new BoneCPConfig();
      CPConfig.setJdbcUrl("jdbc:mysql://" + Config.get("jdbc.grizzly.host") + "/" + Config.get("jdbc.grizzly.database"));
      CPConfig.setMinConnectionsPerPartition(2);
      CPConfig.setMaxConnectionsPerPartition(10);
      CPConfig.setPartitionCount(1);
      CPConfig.setUsername(Config.get("jdbc.grizzly.username"));
      CPConfig.setPassword(Config.get("jdbc.grizzly.password"));

      try {
         this.Pool = new BoneCP(CPConfig);
      } catch (SQLException var4) {
         Grizzly.write(var4.getMessage());
      }

   }

   public Connection GrabConnection() {
      try {
         return this.Pool.getConnection();
      } catch (SQLException var2) {
         return null;
      }
   }

   public BoneCP GrabPool() {
      return this.Pool;
   }
}
