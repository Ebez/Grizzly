package net.cobem.grizzly.net;

import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.communication.EventRequest;
import net.cobem.grizzly.habbohotel.sessions.Session;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;

public class ConnectionHandler extends SimpleChannelUpstreamHandler {

   public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
      Grizzly.getHabboHotel().getSessions().create(ctx.getChannel());
   }

   public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) {
      Session mSession = (Session)ctx.getChannel().getAttachment();
      Grizzly.getHabboHotel().getSessions().kill(mSession);
   }

   public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) {
      Session mSession = (Session)ctx.getChannel().getAttachment();
      Grizzly.getHabboHotel().getSessions().kill(mSession);
   }

   public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
      try {
         Grizzly.getConnections().getEventHandler().handleRequest(Grizzly.getHabboHotel().getSessions().get(ctx.getChannel()), (EventRequest)e.getMessage());
      } catch (Exception var4) {
         var4.printStackTrace();
      }

   }

   public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
      e.getCause().printStackTrace();
      e.getChannel().close();
   }
}
