package net.cobem.grizzly.communication;

import java.io.IOException;
import java.nio.charset.Charset;
import net.cobem.grizzly.communication.iSerializeEvent;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBufferOutputStream;
import org.jboss.netty.buffer.ChannelBuffers;

public class EventResponse {

   private int Id;
   private ChannelBufferOutputStream bodystream;
   private ChannelBuffer body;
   public String SimpleResponse;


   public EventResponse Initialize(int id) {
      this.Id = id;
      this.body = ChannelBuffers.dynamicBuffer();
      this.bodystream = new ChannelBufferOutputStream(this.body);
      this.SimpleResponse = "";

      try {
         this.bodystream.writeInt(0);
         this.bodystream.writeShort(id);
      } catch (Exception var3) {
         var3.printStackTrace();
      }

      return this;
   }

   public EventResponse addString(Object obj) {
      try {
         this.bodystream.writeUTF(obj.toString());
         this.SimpleResponse = this.SimpleResponse + "[STRING=" + obj + "]";
      } catch (IOException var3) {
         ;
      }

      return this;
   }

   public void addInt(Integer obj) {
      try {
         this.bodystream.writeInt(obj.intValue());
         this.SimpleResponse = this.SimpleResponse + "[INT=" + obj + "]";
      } catch (IOException var3) {
         ;
      }

   }

   public EventResponse addShort(int obj) {
      try {
         this.bodystream.writeShort((short)obj);
         this.SimpleResponse = this.SimpleResponse + "[SHORT=" + obj + "]";
      } catch (IOException var3) {
         ;
      }

      return this;
   }

   public EventResponse addBool(Boolean obj) {
      try {
         this.bodystream.writeBoolean(obj.booleanValue());
         this.SimpleResponse = this.SimpleResponse + "[BOOLEAN=" + obj + "]";
      } catch (IOException var3) {
         ;
      }

      return this;
   }

   public void addBody(iSerializeEvent Obj) {
      try {
         Obj.serialize(this);
      } catch (Exception var3) {
         ;
      }

   }

   public String body() {
      String str = new String(this.body.toString(Charset.defaultCharset()));
      String consoleText = str;

      for(int i = 0; i < 13; ++i) {
         consoleText = consoleText.replace(Character.toString((char)i), "{" + i + "}");
      }

      return consoleText;
   }

   public int header() {
      return this.Id;
   }

   public ChannelBuffer get() {
      this.body.setInt(0, this.body.writerIndex() - 4);
      return this.body;
   }
}
