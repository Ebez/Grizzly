package net.cobem.grizzly.console;

import net.cobem.grizzly.console.OutputType;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

public class OutputHandler {

   private Logger LogMechanism = Logger.getLogger(OutputHandler.class.getName());
   private String Pattern = "%-5p[%t] %F:%L - %m%n";


   public OutputHandler() {
      this.LogMechanism.addAppender(new ConsoleAppender(new PatternLayout(this.Pattern)));
   }

   public void write(String Text, OutputType Type) {
      if(Type == OutputType.Debug) {
         this.LogMechanism.debug(Text);
      }

      if(Type == OutputType.Info) {
         this.LogMechanism.info(Text);
      }

      if(Type == OutputType.Warning) {
         this.LogMechanism.warn(Text);
      }

      if(Type == OutputType.Error) {
         this.LogMechanism.error(Text);
      }

   }

   public void write(String Class, String Text, OutputType Type) {
      Logger LogByClass = Logger.getLogger(Class);
      LogByClass.addAppender(new ConsoleAppender(new PatternLayout(this.Pattern)));
      if(Type == OutputType.Debug) {
         LogByClass.debug(Text);
      } else if(Type == OutputType.Info) {
         LogByClass.info(Text);
      } else if(Type == OutputType.Warning) {
         LogByClass.warn(Text);
      } else if(Type == OutputType.Error) {
         LogByClass.error(Text);
      }
   }
}
