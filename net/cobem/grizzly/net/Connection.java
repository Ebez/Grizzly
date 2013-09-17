package net.cobem.grizzly.net;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import net.cobem.grizzly.communication.EventHandler;
import net.cobem.grizzly.net.ConnectionHandler;
import net.cobem.grizzly.net.codec.CodecDecoder;
import net.cobem.grizzly.net.codec.CodecEncoder;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelException;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

public class Connection {

   private NioServerSocketChannelFactory ChannelFactory = new NioServerSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool());
   private ServerBootstrap Bootstrap;
   private EventHandler EventHandler;
   private int ConnectionPort;


   public Connection(int Port) {
      this.Bootstrap = new ServerBootstrap(this.ChannelFactory);
      this.ConnectionPort = Port;
      this.EventHandler = new EventHandler();
      this.ConfigurePipeline();
   }

   private void ConfigurePipeline() {
      ChannelPipeline Line = this.Bootstrap.getPipeline();
      Line.addLast("encoder", new CodecEncoder());
      Line.addLast("decoder", new CodecDecoder());
      Line.addLast("handler", new ConnectionHandler());
   }

   public boolean Listen() {
      try {
         this.Bootstrap.bind(new InetSocketAddress(this.ConnectionPort));
         return true;
      } catch (ChannelException var2) {
         return false;
      }
   }

   public EventHandler getEventHandler() {
      return this.EventHandler;
   }
}
