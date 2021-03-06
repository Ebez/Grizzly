package net.cobem.grizzly.net.codec;

import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.communication.EventRequest;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

public class CodecDecoder extends FrameDecoder {

   protected Object decode(ChannelHandlerContext Handler, Channel Channel, ChannelBuffer Buffer) {
      try {
         if(Buffer.readableBytes() < 5) {
            return null;
         } else {
            int BufferIndex = Buffer.readerIndex();
            byte Delimeter = Buffer.readByte();
            if(Delimeter == 60) {
               Buffer.clear();
               Channel.write("<?xml version=\"1.0\"?>\r\n<!DOCTYPE cross-domain-policy SYSTEM \"/xml/dtds/cross-domain-policy.dtd\">\r\n<cross-domain-policy>\r\n<allow-access-from domain=\"*\" to-ports=\"1-31111\" />\r\n</cross-domain-policy> ").addListener(ChannelFutureListener.CLOSE);
               Grizzly.getHabboHotel().getSessions().remove(Channel);
               return null;
            } else {
               Buffer.readerIndex(BufferIndex);
               int Len = Buffer.readInt() - 2;
               if(Buffer.readableBytes() < Len) {
                  Buffer.readerIndex(BufferIndex);
                  return null;
               } else {
                  return new EventRequest(Short.valueOf(Buffer.readShort()), Buffer.readBytes(Len));
               }
            }
         }
      } catch (Exception var7) {
         return null;
      }
   }
}
