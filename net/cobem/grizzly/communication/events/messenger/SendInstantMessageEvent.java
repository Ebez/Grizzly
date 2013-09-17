package net.cobem.grizzly.communication.events.messenger;

import java.util.Random;
import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.communication.EventRequest;
import net.cobem.grizzly.communication.GameEvent;
import net.cobem.grizzly.communication.composers.messenger.SendInstantMessageComposer;
import net.cobem.grizzly.communication.composers.misc.SendNotificationComposer;
import net.cobem.grizzly.habbohotel.rooms.Room;
import net.cobem.grizzly.habbohotel.rooms.robots.Robot;
import net.cobem.grizzly.habbohotel.sessions.Session;
import net.cobem.grizzly.utils.UserInputFilter;

public class SendInstantMessageEvent implements GameEvent {

   public void compose(Session Session, EventRequest Request) {
      int Reciever = Request.readInt().intValue();
      String Message = UserInputFilter.filterString(Request.readString(), true);
      if(Reciever == (new Integer(Grizzly.getConfig().get("habbo.messenger.staffbot.id"))).intValue()) {
         if(Message.split(" ").length >= 2) {
            boolean FoundCommand;
            boolean InvalidParameters;
            label78: {
               label77: {
                  label76: {
                     String Command = Message.split(" ")[0];
                     FoundCommand = true;
                     InvalidParameters = false;
                     String Username;
                     switch(Command.hashCode()) {
                     case -1268958287:
                        if(Command.equals("follow")) {
                           if(Message.split(" ").length < 2) {
                              InvalidParameters = true;
                           } else if(Message.split(" ")[1].equals("me")) {
                              Robot Username3 = new Robot(Session, (new Random()).nextInt(999) + 9000, Grizzly.getConfig().get("habbo.messenger.staffbot.name"), Grizzly.getConfig().get("habbo.messenger.staffbot.motto"), Grizzly.getConfig().get("habbo.messenger.staffbot.look"), Session.getActor().CurrentRoom);
                              Username3.X = Session.getActor().CurrentRoom.getModel().DoorX;
                              Username3.Y = Session.getActor().CurrentRoom.getModel().DoorY;
                              Username3.Rotation = Session.getActor().CurrentRoom.getModel().DoorRot;
                              Session.getActor().CurrentRoom.generateRobot(Username3);
                           } else {
                              Robot Bot;
                              if(Integer.parseInt(Message.split(" ")[1]) != 0) {
                                 int Username2 = (new Integer(Message.split(" ")[1])).intValue();
                                 if(Grizzly.getHabboHotel().getSessions().getByID(Username2) != null) {
                                    Bot = new Robot(Session, (new Random()).nextInt(999) + 9000, Grizzly.getConfig().get("habbo.messenger.staffbot.name"), Grizzly.getConfig().get("habbo.messenger.staffbot.motto"), Grizzly.getConfig().get("habbo.messenger.staffbot.look"), Grizzly.getHabboHotel().getSessions().getByID(Username2).getActor().CurrentRoom);
                                    Bot.X = Grizzly.getHabboHotel().getSessions().getByID(Username2).getActor().CurrentRoom.getModel().DoorX;
                                    Bot.Y = Grizzly.getHabboHotel().getSessions().getByID(Username2).getActor().CurrentRoom.getModel().DoorY;
                                    Bot.Rotation = Grizzly.getHabboHotel().getSessions().getByID(Username2).getActor().CurrentRoom.getModel().DoorRot;
                                    Grizzly.getHabboHotel().getSessions().getByID(Username2).getActor().CurrentRoom.generateRobot(Bot);
                                 }
                              } else {
                                 Username = Message.split(" ")[1];
                                 if(Grizzly.getHabboHotel().getSessions().getByName(Username) != null) {
                                    Bot = new Robot(Session, (new Random()).nextInt(999) + 9000, Grizzly.getConfig().get("habbo.messenger.staffbot.name"), Grizzly.getConfig().get("habbo.messenger.staffbot.motto"), Grizzly.getConfig().get("habbo.messenger.staffbot.look"), Grizzly.getHabboHotel().getSessions().getByName(Username).getActor().CurrentRoom);
                                    Bot.X = Grizzly.getHabboHotel().getSessions().getByName(Username).getActor().CurrentRoom.getModel().DoorX;
                                    Bot.Y = Grizzly.getHabboHotel().getSessions().getByName(Username).getActor().CurrentRoom.getModel().DoorY;
                                    Bot.Rotation = Grizzly.getHabboHotel().getSessions().getByName(Username).getActor().CurrentRoom.getModel().DoorRot;
                                    Grizzly.getHabboHotel().getSessions().getByName(Username).getActor().CurrentRoom.generateRobot(Bot);
                                 }
                              }
                           }
                           break label78;
                        }
                        break label77;
                     case -1065789087:
                        if(!Command.equals("roomalert")) {
                           break label77;
                        }
                        break label76;
                     case 3321:
                        if(!Command.equals("ha")) {
                           break label77;
                        }
                        break;
                     case 3631:
                        if(!Command.equals("ra")) {
                           break label77;
                        }
                        break label76;
                     case 92899676:
                        if(Command.equals("alert")) {
                           break label78;
                        }
                        break label77;
                     case 909424680:
                        if(!Command.equals("hotelalert")) {
                           break label77;
                        }
                        break;
                     default:
                        break label77;
                     }

                     if(Message.split(" ").length < 2) {
                        InvalidParameters = true;
                     } else {
                        Username = "";
                        if(Message.split(" ").length > 2 && !Message.split(" ")[2].startsWith("www.")) {
                           Grizzly.getHabboHotel().sendHotelAlert(Message.replace(Message.split(" ")[0], ""));
                           Session.sendResponse(SendInstantMessageComposer.compose((new Integer(Grizzly.getConfig().get("habbo.messenger.staffbot.id"))).intValue(), "Sending " + Message.replace(Message.split(" ")[0], "") + " to the hotel!"));
                        } else {
                           Grizzly.getHabboHotel().sendHotelAlert(Message.split(" ")[1]);
                           Session.sendResponse(SendInstantMessageComposer.compose((new Integer(Grizzly.getConfig().get("habbo.messenger.staffbot.id"))).intValue(), "Sending " + Message.split(" ")[1] + " to the hotel!"));
                        }
                     }
                     break label78;
                  }

                  if(Message.split(" ").length < 3) {
                     InvalidParameters = true;
                  } else if(Integer.parseInt(Message.split(" ")[1]) != 0) {
                     Room Username1 = Grizzly.getHabboHotel().getRooms().getByID(Integer.parseInt(Message.split(" ")[1]));
                     Username1.sendMessage(SendNotificationComposer.compose(Message.replace(Message.split(" ")[0], "").replace(Message.split(" ")[1], "")));
                     Session.sendResponse(SendInstantMessageComposer.compose((new Integer(Grizzly.getConfig().get("habbo.messenger.staffbot.id"))).intValue(), "Sending " + Message.replace(Message.split(" ")[0], "").replace(Message.split(" ")[1], "") + " to the room " + Message.split(" ")[1] + "!"));
                  }
                  break label78;
               }

               FoundCommand = false;
            }

            if(!FoundCommand) {
               Session.sendResponse(SendInstantMessageComposer.compose((new Integer(Grizzly.getConfig().get("habbo.messenger.staffbot.id"))).intValue(), "I can\'t find that command in my manual.."));
            }

            if(InvalidParameters) {
               Session.sendResponse(SendInstantMessageComposer.compose((new Integer(Grizzly.getConfig().get("habbo.messenger.staffbot.id"))).intValue(), "You havent given me enough parameters for that command.. It says it right here in my manual!"));
            }
         } else {
            Session.sendResponse(SendInstantMessageComposer.compose((new Integer(Grizzly.getConfig().get("habbo.messenger.staffbot.id"))).intValue(), "I only run commands bro."));
         }
      } else if(Grizzly.getHabboHotel().getSessions().getByID(Reciever) != null) {
         Grizzly.getHabboHotel().getSessions().getByID(Reciever).sendResponse(SendInstantMessageComposer.compose(Session.getHabbo().getID(), Message));
      } else {
         Session.sendResponse(SendInstantMessageComposer.compose(Reciever, "Your friend is offline!"));
      }

   }
}
