package net.cobem.grizzly.communication;

import java.util.HashMap;
import java.util.Map;
import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.communication.EventRequest;
import net.cobem.grizzly.communication.GameEvent;
import net.cobem.grizzly.communication.HeaderLibrary;
import net.cobem.grizzly.communication.events.catalog.GetPromotedRoomsDataEvent;
import net.cobem.grizzly.communication.events.catalog.InitializeCatalogEvent;
import net.cobem.grizzly.communication.events.catalog.InitializeCatalogPageEvent;
import net.cobem.grizzly.communication.events.catalog.PurchaseCatalogItemEvent;
import net.cobem.grizzly.communication.events.guild.CreateGuildEvent;
import net.cobem.grizzly.communication.events.guild.FinishCreateGuildEvent;
import net.cobem.grizzly.communication.events.handshake.InitializeCryptoEvent;
import net.cobem.grizzly.communication.events.handshake.ReadReleaseEvent;
import net.cobem.grizzly.communication.events.handshake.SSOTicketEvent;
import net.cobem.grizzly.communication.events.handshake.SendTokenEvent;
import net.cobem.grizzly.communication.events.handshake.UniqueIdEvent;
import net.cobem.grizzly.communication.events.messenger.AcceptFriendRequestEvent;
import net.cobem.grizzly.communication.events.messenger.FollowFriendEvent;
import net.cobem.grizzly.communication.events.messenger.RequestFriendshipEvent;
import net.cobem.grizzly.communication.events.messenger.SendInstantMessageEvent;
import net.cobem.grizzly.communication.events.misc.SaveNewNameEvent;
import net.cobem.grizzly.communication.events.misc.SendHotelViewPieceEvent;
import net.cobem.grizzly.communication.events.misc.ValidateNewNameEvent;
import net.cobem.grizzly.communication.events.navigator.CreateRoomEvent;
import net.cobem.grizzly.communication.events.navigator.LoadPopulatedRoomsEvent;
import net.cobem.grizzly.communication.events.navigator.RoomCreationCheckEvent;
import net.cobem.grizzly.communication.events.navigator.SearchRoomEvent;
import net.cobem.grizzly.communication.events.navigator.ViewMyRoomsEvent;
import net.cobem.grizzly.communication.events.rooms.ChangeLooksEvent;
import net.cobem.grizzly.communication.events.rooms.ChangeStateEvent;
import net.cobem.grizzly.communication.events.rooms.ChangeWallStateEvent;
import net.cobem.grizzly.communication.events.rooms.CloseDiceEvent;
import net.cobem.grizzly.communication.events.rooms.CustomizeRoomEvent;
import net.cobem.grizzly.communication.events.rooms.DanceEvent;
import net.cobem.grizzly.communication.events.rooms.EndTypingEvent;
import net.cobem.grizzly.communication.events.rooms.FinishRoomLoadEvent;
import net.cobem.grizzly.communication.events.rooms.GiveRespectEvent;
import net.cobem.grizzly.communication.events.rooms.GiveRightsEvent;
import net.cobem.grizzly.communication.events.rooms.IgnoreUserEvent;
import net.cobem.grizzly.communication.events.rooms.InitializeRoomEvent;
import net.cobem.grizzly.communication.events.rooms.ItemPositionChangeEvent;
import net.cobem.grizzly.communication.events.rooms.KickUserEvent;
import net.cobem.grizzly.communication.events.rooms.LeaveRoomEvent;
import net.cobem.grizzly.communication.events.rooms.ListenUserEvent;
import net.cobem.grizzly.communication.events.rooms.MovementEvent;
import net.cobem.grizzly.communication.events.rooms.ParseHeightMapEvent;
import net.cobem.grizzly.communication.events.rooms.PlaceItemEvent;
import net.cobem.grizzly.communication.events.rooms.PlaceRoomEvent;
import net.cobem.grizzly.communication.events.rooms.RedeemExchangeEvent;
import net.cobem.grizzly.communication.events.rooms.RemoveItemEvent;
import net.cobem.grizzly.communication.events.rooms.RemoveRightsEvent;
import net.cobem.grizzly.communication.events.rooms.RollDiceEvent;
import net.cobem.grizzly.communication.events.rooms.SaveRoomBackgroundEvent;
import net.cobem.grizzly.communication.events.rooms.SayEvent;
import net.cobem.grizzly.communication.events.rooms.ShoutEvent;
import net.cobem.grizzly.communication.events.rooms.ShowSignEvent;
import net.cobem.grizzly.communication.events.rooms.SitEvent;
import net.cobem.grizzly.communication.events.rooms.StartTypingEvent;
import net.cobem.grizzly.communication.events.rooms.ViewUserBadgesEvent;
import net.cobem.grizzly.communication.events.rooms.WaveEvent;
import net.cobem.grizzly.communication.events.rooms.WhisperEvent;
import net.cobem.grizzly.communication.events.stream.FriendStreamLikeStatusEvent;
import net.cobem.grizzly.communication.events.stream.FriendStreamSetStatusEvent;
import net.cobem.grizzly.communication.events.stream.InitializeFriendStreamEvent;
import net.cobem.grizzly.communication.events.tests.CheckQuizEvent;
import net.cobem.grizzly.communication.events.tests.InitializeQuizEvent;
import net.cobem.grizzly.communication.events.trading.AcceptTradeEvent;
import net.cobem.grizzly.communication.events.trading.AddItemToTradeEvent;
import net.cobem.grizzly.communication.events.trading.CancelTradeEvent;
import net.cobem.grizzly.communication.events.trading.ConfirmTradeEvent;
import net.cobem.grizzly.communication.events.trading.InitializeTradeEvent;
import net.cobem.grizzly.communication.events.trading.RemoveItemFromTradeEvent;
import net.cobem.grizzly.communication.events.trading.UnacceptTradeEvent;
import net.cobem.grizzly.communication.events.user.ChangeMottoEvent;
import net.cobem.grizzly.communication.events.user.InitializeInventoryEvent;
import net.cobem.grizzly.communication.events.user.LoadUserDataEvent;
import net.cobem.grizzly.communication.events.user.LoadUserProfileEvent;
import net.cobem.grizzly.communication.events.user.LoadUserTagsEvent;
import net.cobem.grizzly.communication.events.user.SetHomeRoomEvent;
import net.cobem.grizzly.communication.events.user.WearBadgeEvent;
import net.cobem.grizzly.communication.events.wired.SaveWiredEffectEvent;
import net.cobem.grizzly.communication.events.wired.SaveWiredTriggerEvent;
import net.cobem.grizzly.habbohotel.sessions.Session;

public class EventHandler {

   private Map<Short, GameEvent> MessageLibrary = new HashMap();


   public EventHandler() {
      this.RegisterHandshake();
      this.RegisterUser();
      this.RegisterMessenger();
      this.RegisterCatalog();
      this.RegisterNavigator();
      this.RegisterRoom();
      this.RegisterItems();
      this.RegisterStream();
      this.RegisterModeration();
      this.RegisterMisc();
      this.RegisterGuild();
      this.RegisterWired();
   }

   private void RegisterHandshake() {
      this.MessageLibrary.put(Short.valueOf((short)4000), new ReadReleaseEvent());
      this.MessageLibrary.put(Short.valueOf(HeaderLibrary.SendSecretKeyComposer), new SendTokenEvent());
      this.MessageLibrary.put(Short.valueOf(HeaderLibrary.InitializeCryptoComposer), new InitializeCryptoEvent());
      this.MessageLibrary.put(Short.valueOf(HeaderLibrary.GetMachineIDComposer), new UniqueIdEvent());
      this.MessageLibrary.put(Short.valueOf(HeaderLibrary.GetUserTicketComposer), new SSOTicketEvent());
   }

   private void RegisterUser() {
      this.MessageLibrary.put(Short.valueOf(HeaderLibrary.LoadProfileInformationComposer), new LoadUserProfileEvent());
      this.MessageLibrary.put(Short.valueOf(HeaderLibrary.LoadUserInformationComposer), new LoadUserDataEvent());
      this.MessageLibrary.put(Short.valueOf(HeaderLibrary.SetHomeRoomComposer), new SetHomeRoomEvent());
      this.MessageLibrary.put(Short.valueOf(HeaderLibrary.ModifyMottoComposer), new ChangeMottoEvent());
      this.MessageLibrary.put(Short.valueOf(HeaderLibrary.WearBadgeComposer), new WearBadgeEvent());
      this.MessageLibrary.put(Short.valueOf(HeaderLibrary.LoadUserTagsComposer), new LoadUserTagsEvent());
   }

   private void RegisterMessenger() {
      this.MessageLibrary.put(Short.valueOf(HeaderLibrary.AcceptFriendRequestComposer), new AcceptFriendRequestEvent());
      this.MessageLibrary.put(Short.valueOf(HeaderLibrary.SendInstantMessageComposer), new SendInstantMessageEvent());
      this.MessageLibrary.put(Short.valueOf(HeaderLibrary.SendFriendRequestComposer), new RequestFriendshipEvent());
      this.MessageLibrary.put(Short.valueOf(HeaderLibrary.FollowFriendComposer), new FollowFriendEvent());
   }

   private void RegisterCatalog() {
      this.MessageLibrary.put(Short.valueOf(HeaderLibrary.LoadCatalogIndexComposer), new InitializeCatalogEvent());
      this.MessageLibrary.put(Short.valueOf(HeaderLibrary.LoadCatalogPageComposer), new InitializeCatalogPageEvent());
      this.MessageLibrary.put(Short.valueOf(HeaderLibrary.PurchaseFromCatalogComposer), new PurchaseCatalogItemEvent());
      this.MessageLibrary.put(Short.valueOf(HeaderLibrary.GetPromotedRoomsCreationDataComposer), new GetPromotedRoomsDataEvent());
   }

   private void RegisterNavigator() {
      this.MessageLibrary.put(Short.valueOf(HeaderLibrary.RoomCreationCheckComposer), new RoomCreationCheckEvent());
      this.MessageLibrary.put(Short.valueOf(HeaderLibrary.RoomCreationComposer), new CreateRoomEvent());
      this.MessageLibrary.put(Short.valueOf(HeaderLibrary.LoadUserRoomsComposer), new ViewMyRoomsEvent());
      this.MessageLibrary.put(Short.valueOf(HeaderLibrary.LoadPopulatedRoomsComposer), new LoadPopulatedRoomsEvent());
      this.MessageLibrary.put(Short.valueOf(HeaderLibrary.RoomSearchComposer), new SearchRoomEvent());
   }

   private void RegisterRoom() {
      this.MessageLibrary.put(Short.valueOf(HeaderLibrary.LoadUserRoomComposer), new InitializeRoomEvent());
      this.MessageLibrary.put(Short.valueOf(HeaderLibrary.LeaveRoomComposer), new LeaveRoomEvent());
      this.MessageLibrary.put(Short.valueOf(HeaderLibrary.LoadRoomInformationComposer), new FinishRoomLoadEvent());
      this.MessageLibrary.put(Short.valueOf(HeaderLibrary.ParseRoomHeightMapComposer), new ParseHeightMapEvent());
      this.MessageLibrary.put(Short.valueOf(HeaderLibrary.RoomTalkComposer), new SayEvent());
      this.MessageLibrary.put(Short.valueOf(HeaderLibrary.RoomShoutComposer), new ShoutEvent());
      this.MessageLibrary.put(Short.valueOf(HeaderLibrary.RoomMovementComposer), new MovementEvent());
      this.MessageLibrary.put(Short.valueOf(HeaderLibrary.RoomWhisperComposer), new WhisperEvent());
      this.MessageLibrary.put(Short.valueOf(HeaderLibrary.RoomWaveComposer), new WaveEvent());
      this.MessageLibrary.put(Short.valueOf(HeaderLibrary.RoomShowSignComposer), new ShowSignEvent());
      this.MessageLibrary.put(Short.valueOf(HeaderLibrary.RoomDanceComposer), new DanceEvent());
      this.MessageLibrary.put(Short.valueOf(HeaderLibrary.RoomSitComposer), new SitEvent());
      this.MessageLibrary.put(Short.valueOf(HeaderLibrary.InitializeTypeBubbleComposer), new StartTypingEvent());
      this.MessageLibrary.put(Short.valueOf(HeaderLibrary.EndTypeBubbleComposer), new EndTypingEvent());
      this.MessageLibrary.put(Short.valueOf(HeaderLibrary.ModifyLookComposer), new ChangeLooksEvent());
      this.MessageLibrary.put(Short.valueOf(HeaderLibrary.PlaceItemComposer), new PlaceItemEvent());
      this.MessageLibrary.put(Short.valueOf(HeaderLibrary.RemoveRoomItemComposer), new RemoveItemEvent());
      this.MessageLibrary.put(Short.valueOf(HeaderLibrary.RoomPlaceEffectComposer), new PlaceRoomEvent());
      this.MessageLibrary.put(Short.valueOf(HeaderLibrary.RepositionItemComposer), new ItemPositionChangeEvent());
      this.MessageLibrary.put(Short.valueOf(HeaderLibrary.RepositionWallItemComposer), new ItemPositionChangeEvent());
      this.MessageLibrary.put(Short.valueOf(HeaderLibrary.ToggleWallStateComposer), new ChangeWallStateEvent());
      this.MessageLibrary.put(Short.valueOf(HeaderLibrary.RepositionFloorItemComposer), new ChangeStateEvent());
      this.MessageLibrary.put(Short.valueOf(HeaderLibrary.SaveRoomBackgroundComposer), new SaveRoomBackgroundEvent());
      this.MessageLibrary.put(Short.valueOf(HeaderLibrary.RedeemExchangeComposer), new RedeemExchangeEvent());
      this.MessageLibrary.put(Short.valueOf(HeaderLibrary.RollDiceComposer), new RollDiceEvent());
      this.MessageLibrary.put(Short.valueOf(HeaderLibrary.CloseDiceComposer), new CloseDiceEvent());
      this.MessageLibrary.put(Short.valueOf(HeaderLibrary.RoomRemoveUserComposer), new KickUserEvent());
      this.MessageLibrary.put(Short.valueOf(HeaderLibrary.GiveRespectComposer), new GiveRespectEvent());
      this.MessageLibrary.put(Short.valueOf(HeaderLibrary.GiveRoomRightsComposer), new GiveRightsEvent());
      this.MessageLibrary.put(Short.valueOf(HeaderLibrary.RoomIgnoreUserComposer), new IgnoreUserEvent());
      this.MessageLibrary.put(Short.valueOf(HeaderLibrary.RoomListenUserComposer), new ListenUserEvent());
      this.MessageLibrary.put(Short.valueOf(HeaderLibrary.RemoveRoomRightsComposer), new RemoveRightsEvent());
      this.MessageLibrary.put(Short.valueOf(HeaderLibrary.InitializeTradeComposer), new InitializeTradeEvent());
      this.MessageLibrary.put(Short.valueOf(HeaderLibrary.InsertItemToTradeComposer), new AddItemToTradeEvent());
      this.MessageLibrary.put(Short.valueOf(HeaderLibrary.RemoveItemFromTradeComposer), new RemoveItemFromTradeEvent());
      this.MessageLibrary.put(Short.valueOf(HeaderLibrary.CancelTradeComposer), new CancelTradeEvent());
      this.MessageLibrary.put(Short.valueOf(HeaderLibrary.AcceptTradeComposer), new AcceptTradeEvent());
      this.MessageLibrary.put(Short.valueOf(HeaderLibrary.UndoAcceptTradeComposer), new UnacceptTradeEvent());
      this.MessageLibrary.put(Short.valueOf(HeaderLibrary.ConfirmTradeComposer), new ConfirmTradeEvent());
      this.MessageLibrary.put(Short.valueOf(HeaderLibrary.ViewUserBadgesComposer), new ViewUserBadgesEvent());
      this.MessageLibrary.put(Short.valueOf(HeaderLibrary.InitializeRoomSettingsComposer), new CustomizeRoomEvent());
   }

   private void RegisterItems() {
      this.MessageLibrary.put(Short.valueOf(HeaderLibrary.InitializeFloorItemsComposer), new InitializeInventoryEvent());
   }

   private void RegisterModeration() {}

   private void RegisterMisc() {
      this.MessageLibrary.put(Short.valueOf(HeaderLibrary.CheckNameChangeComposer), new ValidateNewNameEvent());
      this.MessageLibrary.put(Short.valueOf(HeaderLibrary.CompleteNameChangeComposer), new SaveNewNameEvent());
      this.MessageLibrary.put(Short.valueOf(HeaderLibrary.CancelChangeNameComposer), new SaveNewNameEvent());
      this.MessageLibrary.put(Short.valueOf(HeaderLibrary.InitializeQuizComposer), new InitializeQuizEvent());
      this.MessageLibrary.put(Short.valueOf(HeaderLibrary.CheckQuizComposer), new CheckQuizEvent());
      this.MessageLibrary.put(Short.valueOf(HeaderLibrary.SendHotelViewPieceComposer), new SendHotelViewPieceEvent());
   }

   private void RegisterStream() {
      this.MessageLibrary.put(Short.valueOf(HeaderLibrary.InitializeFriendStreamComposer), new InitializeFriendStreamEvent());
      this.MessageLibrary.put(Short.valueOf(HeaderLibrary.FriendStreamSendMessageComposer), new FriendStreamSetStatusEvent());
      this.MessageLibrary.put(Short.valueOf(HeaderLibrary.FriendStreamLikeMessageComposer), new FriendStreamLikeStatusEvent());
   }

   private void RegisterGuild() {
      this.MessageLibrary.put(Short.valueOf(HeaderLibrary.InitializeCreateGroupComposer), new CreateGuildEvent());
      this.MessageLibrary.put(Short.valueOf(HeaderLibrary.FinishCreateGroupComposer), new FinishCreateGuildEvent());
   }

   private void RegisterWired() {
      this.MessageLibrary.put(Short.valueOf(HeaderLibrary.SaveWiredEffectComposer), new SaveWiredEffectEvent());
      this.MessageLibrary.put(Short.valueOf(HeaderLibrary.SaveWiredTriggerComposer), new SaveWiredTriggerEvent());
   }

   public void handleRequest(Session Session, EventRequest Message) throws Exception {
      if(!this.MessageLibrary.containsKey(Message.header()) && Message.body().length() >= 3) {
         Grizzly.write("[UNKNOWN][#" + Message.header() + "] [" + Message.body() + "]");
      } else {
         if(Grizzly.getConfig().get("net.grizzly.packetlog").equals("1")) {
            Grizzly.write("[" + Message.header() + "] " + Message.body() + " sent to " + Session.ip());
         }

         if(this.MessageLibrary.containsKey(Message.header())) {
            Grizzly.write("[#" + Message.header() + "] [" + Message.body() + "] " + ((GameEvent)this.MessageLibrary.get(Message.header())).toString());
            ((GameEvent)this.MessageLibrary.get(Message.header())).compose(Session, Message);
         }

      }
   }
}
