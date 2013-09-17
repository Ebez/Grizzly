package net.cobem.grizzly.communication;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;

public class EventRequest implements Cloneable {

   private Short Header;
   private ChannelBuffer Buffer;


   public EventRequest(Short id, ChannelBuffer buffer) {
      this.Header = id;
      this.Buffer = buffer != null && buffer.readableBytes() != 0?buffer:ChannelBuffers.EMPTY_BUFFER;
   }

   public byte[] readBytes(int Amount) {
      return this.Buffer.readBytes(Amount).array();
   }

   public int readShort() {
      return ByteBuffer.wrap(this.readBytes(2)).asShortBuffer().get();
   }

   public ChannelBuffer readFixed() {
      return this.Buffer.readBytes(this.readShort());
   }

   public Integer readInt() {
      try {
         return Integer.valueOf(ByteBuffer.wrap(this.readBytes(4)).asIntBuffer().get());
      } catch (Exception var2) {
         return Integer.valueOf(0);
      }
   }

   public String readString() {
      return this.readFixed().toString(Charset.defaultCharset());
   }

   public boolean readBoolean() {
      return this.Buffer.readableBytes() > 0 && this.Buffer.readByte() == 1;
   }

   public Short header() {
      return this.Header;
   }

   public String body() {
      String consoleText = this.Buffer.toString(Charset.defaultCharset());

      for(int i = 0; i < 13; ++i) {
         consoleText = consoleText.replace(Character.toString((char)i), "{" + i + "}");
      }

      return consoleText;
   }

   public int length() {
      return this.Buffer.readableBytes();
   }
}
