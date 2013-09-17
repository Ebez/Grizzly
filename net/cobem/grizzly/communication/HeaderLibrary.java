package net.cobem.grizzly.communication;


public class HeaderLibrary {

   public static short ReadRevisionComposer = 4000;
   public static short InitializeCryptoComposer = 1525;
   public static short SendSecretKeyComposer = 3557;
   public static short GetClientVariablesComposer = 3561;
   public static short GetMachineIDComposer = 3786;
   public static short GetUserTicketComposer = 1887;
   public static short LoadUserInformationComposer = 3897;
   public static short LoadProfileInformationComposer = 3096;
   public static short CompleteLoginComposer = 3943;
   public static short AcceptFriendRequestComposer = 1606;
   public static short SendFriendRequestComposer = 2926;
   public static short SendInstantMessageComposer = 2790;
   public static short LoadCatalogIndexComposer = 383;
   public static short LoadCatalogPageComposer = 3187;
   public static short PurchaseFromCatalogComposer = 402;
   public static short RoomCreationCheckComposer = 2369;
   public static short RoomCreationComposer = 2004;
   public static short LoadUserRoomsComposer = 2606;
   public static short LoadUserRoomComposer = 3056;
   public static short LoadRoomInformationComposer = 2090;
   public static short InitializeFloorItemsComposer = 1074;
   public static short ParseRoomHeightMapComposer = 243;
   public static short InitializeTypeBubbleComposer = 382;
   public static short EndTypeBubbleComposer = 2952;
   public static short RoomShoutComposer = 1152;
   public static short RoomTalkComposer = 1148;
   public static short LoadPopulatedRoomsComposer = 1308;
   public static short RoomMovementComposer = 1193;
   public static short LeaveRoomComposer = 2457;
   public static short RoomShowSignComposer = 2123;
   public static short ModifyLookComposer = 3519;
   public static short RoomWaveComposer = 964;
   public static short PlaceItemComposer = 2775;
   public static short RoomSearchComposer = 1341;
   public static short RoomDanceComposer = 1199;
   public static short RepositionItemComposer = 1937;
   public static short ToggleWallStateComposer = 2126;
   public static short RepositionWallItemComposer = 327;
   public static short RepositionFloorItemComposer = 2241;
   public static short RoomPlaceEffectComposer = 3006;
   public static short CloseDiceComposer = 2027;
   public static short RollDiceComposer = 2446;
   public static short RoomSitComposer = 1202;
   public static short RemoveRoomItemComposer = 2996;
   public static short RoomRemoveUserComposer = 957;
   public static short GiveRoomRightsComposer = 1283;
   public static short RemoveRoomRightsComposer = 1099;
   public static short RoomIgnoreUserComposer = 1141;
   public static short RoomListenUserComposer = 2377;
   public static short GiveRespectComposer = 1087;
   public static short RoomBanUserComposer = 2731;
   public static short InitializeFriendStreamComposer = 763;
   public static short FriendStreamSendMessageComposer = 2931;
   public static short FriendStreamLikeMessageComposer = 1325;
   public static short ViewUserBadgesComposer = 822;
   public static short ToggleFriendStreamComposer = 624;
   public static short InitializeRoomSettingsComposer = 1244;
   public static short SetGameVolumeComposer = 979;
   public static short SetRoomAsStaffPickedComposer = 3642;
   public static short SetHomeRoomComposer = 3896;
   public static short SendCallForHelpComposer = 724;
   public static short RoomWhisperComposer = 784;
   public static short CheckNameChangeComposer = 3232;
   public static short CompleteNameChangeComposer = 256;
   public static short ModifyMottoComposer = 3776;
   public static short FollowFriendComposer = 0;
   public static short CancelChangeNameComposer = 3904;
   public static short InitializeTradeComposer = 156;
   public static short InsertItemToTradeComposer = 3921;
   public static short RemoveItemFromTradeComposer = 2314;
   public static short AcceptTradeComposer = 2748;
   public static short UndoAcceptTradeComposer = 1250;
   public static short CancelTradeComposer = 2887;
   public static short ConfirmTradeComposer = 591;
   public static short GetGroupInformationComposer = 3526;
   public static short InitializeCreateGroupComposer = 1142;
   public static short FinishCreateGroupComposer = 1195;
   public static short ModToolsShowRoomInformationComposer = 1847;
   public static short ModToolsTravelToRoomComposer = 2251;
   public static short ModToolsShowUserInformationComposer = 3173;
   public static short InitializeQuizComposer = 1752;
   public static short CheckQuizComposer = 1773;
   public static short SendHotelViewPieceComposer = 669;
   public static short SaveRoomBackgroundComposer = 242;
   public static short LoadUserTagsComposer = 3030;
   public static short SaveWiredEffectComposer = 3616;
   public static short SaveWiredTriggerComposer = 2140;
   public static short WearBadgeComposer = 3078;
   public static short GetPromotedRoomsCreationDataComposer = 3627;
   public static short RedeemExchangeComposer = 2180;
   public static int SendBannerMessageEvent = 1030;
   public static int SendSecretKeyEvent = 1989;
   public static int SendMachineIDEvent = 1130;
   public static int SendAuthenticationOkayEvent = 347;
   public static int InitializeLoginEvent = 3984;
   public static int InitializeProfileEvent = 2319;
   public static int ShowUserInformationEvent = 2427;
   public static int CheckAllowancesEvent = 62;
   public static int InitializeModToolsEvent = 2659;
   public static int InitializeCreditsEvent = 3464;
   public static int InitializeClubEvent = 3457;
   public static int SendFrankNotificationEvent = 3578;
   public static int SendNotificationEvent = 2131;
   public static int InitializePendingFriendRequestsEvent = 1671;
   public static int InitializeMessengerEvent = 109;
   public static int UpdateFriendStateEvent = 1310;
   public static int SendInstantMessageEvent = 600;
   public static int ShowCatalogIndexEvent = 957;
   public static int ShowCatalogPageEvent = 2416;
   public static int CatalogPurchaseItemEvent = 2082;
   public static int UpdateUserInventoryEvent = 57;
   public static int DisposeItemFromInventoryEvent = 606;
   public static int SendUserInventoryEvent = 2963;
   public static int ShowNewInventoryCountEvent = 2897;
   public static int RoomCreationCheckResultsEvent = 3648;
   public static int SendRoomInformationEvent = 3098;
   public static int ViewUserRoomsEvent = 3838;
   public static int InitializeRoomUserStatusesEvent = 2397;
   public static int SendRoomFullAlertEvent = 2945;
   public static int InitializeRoomLoadEvent = 2291;
   public static int SendRoomModelEvent = 22;
   public static int SendRoomPapersEvent = 1186;
   public static int InitializeRoomRightHoldersEvent = 2950;
   public static int SendRoomOwnerPowerEvent = 2207;
   public static int SendRoomEventsEvent = 381;
   public static int InitializeFirstHeightMapEvent = 3071;
   public static int InitializeSecondHeightMapEvent = 2606;
   public static int SendWallItemToRoomEvent = 3029;
   public static int SendFloorItemToRoomEvent = 2461;
   public static int UpdateFloorItemEvent = 2290;
   public static int RemoveRoomFloorEvent = 1705;
   public static int RemoveRoomItemEvent = 2731;
   public static int RemoveRoomWallEvent = 2313;
   public static int SendNewRoomUserInformationEvent = 3379;
   public static int InitializeRoomUsersEvent = 2330;
   public static int InitializeFloorItemsEvent = 2616;
   public static int InitializeWallItemsEvent = 2862;
   public static int SendRoomChatEvent = 2655;
   public static int SendRoomShoutEvent = 3098;
   public static int SendRoomSettingsInterfaceEvent = 1024;
   public static int SendMoreRoomInformationEvent = 423;
   public static int SendWaveStatusEvent = 3872;
   public static int LeaveRoomEvent = 1173;
   public static int RemoveRoomUserEntityEvent = 979;
   public static int ToggleChatBubbleEvent = 1552;
   public static int UpdateRoomUserInformationEvent = 3341;
   public static int SendDanceStatusEvent = 244;
   public static int UpdateFloorExtraDataEvent = 582;
   public static int UpdateWallExtraDataEvent = 2602;
   public static int InitializeClubRankEvent = 901;
   public static int SendFriendRequestEvent = 3399;
   public static int SendHotelViewEvent = 3941;
   public static int InitializeCurrenciesEvent = 3995;
   public static int SendRoomEffectEvent = 3113;
   public static int SendModToolsRoomInformationEvent = 2516;
   public static int SendModToolsUserInformationEvent = 3301;
   public static int SendModToolsRoomChatlogEvent = 818;
   public static int ModToolsAddHelpRequestEvent = 1841;
   public static int ModToolsShowCallsForHelp = 3242;
   public static int ShowGroupDialogEvent = 97;
   public static int SendRoomWhisperEvent = 2832;
   public static int InitializeTradeEvent = 66;
   public static int UpdateTradeEvent = 2683;
   public static int CloseTradeEvent = 1386;
   public static int AcceptTradeEvent = 3113;
   public static int CompleteTradeEvent = 401;
   public static int CloseCompletedTradeEvent = 543;
   public static int InitializeFriendStreamEvent = 2705;
   public static int ViewUserBadgesEvent = 31;
   public static int UpdateItemOnRollerEvent = 1406;
   public static int BallRollAnimationEvent = 2290;
   public static int DisplayTestResultsEvent = 2048;
   public static int DisplayTestContainerEvent = 3665;
   public static int ShowGuildPiecesEvent = 3871;
   public static int CheckNameResponseEvent = 1027;
   public static int SaveChangeNameEvent = 3286;
   public static int InitializeBadgesEvent = 2999;
   public static int SendToHomeRoomEvent = 3001;
   public static int SendAllowanceEvent = 2507;
   public static int InitializeCitizenshipPanelEvent = 3244;
   public static int SendHotelViewWelcomeEvent = 1608;
   public static int SentRespectStatusEvent = 3098;
   public static int SendIgnoreStatusEvent = 2600;
   public static int SendIdleStatusEvent = 3131;
   public static int SendDrinkStatusEvent = 1168;
   public static int SendUserTagsEvent = 20;
   public static int SendOffersConfigurationEvent = 3329;
   public static int SendRoomUserRightsEvent = 3260;
   public static int SendSettingsDialogEvent = 2443;
   public static int SendWiredTriggerDialogEvent = 3191;
   public static int SendWiredEffectDialogEvent = 878;
   public static int SendWiredConditionDialogEvent = 2423;
   public static int SendWiredDialogFinishedEvent = 2466;
   public static int SendPromotedRoomsCreationDataEvent = 3073;


}