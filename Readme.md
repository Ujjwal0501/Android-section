RANGILO version 1.0 21/03/2018



RANGILO Rajasthan - Pdharo Mhare Desh
____________________________________________________________________________________________________________________________________

Platform Used : Android , Unity3d Game engine 2017.3.1f1 , Vuforia 7.0 plugin , Firebase 11.8.0
____________________________________________________________________________________________________________________________________

Requirements : - 
  > Android 4.1(Jelly bean) or higher
  >
  
(Prototype) Functionality :

1. App starts with a Sign-in page (basically built up on Google's Firebase ) , to auntheticate its user ( Aunthentication fails! msg on      wrong credentials.)

  Or Sign up with Unique Email id and Password and then sign in with the crede

2. On "Successful Sign in", dashboard appears containing the information about :-
                                * Wallet Money (can be updated on tap)
                                * travel completed (locations user has visited in the city, based on device geo location)
                                * Current Credits (can be refreshed when tapped)
                                * Start AR button
                                * lower portion showing various nearby Contests(eg. best photograph, cheapest from A to B , most explored in least time etc.)
                 
3. Start AR : Provides Options to user to use AR technology.
              -scan to get 3d models and hidden treasures
              -scan to get information(feature not added)
              -augmented reality animateds (Dummy models used, to be replaced by other models)
              -decode written texts to Native Languages (or encrypted msgs to decrypted)
              -playing certain multiplayer local games between users while interacting with real world

Features : 
    >User engagement by Notifications to there apps , and contests.
    >AR like never before in the world

Challenges :-
  Augmented reality is still the future for technology and right now in transition to what we call Today's World . 
 
  1.The technnology requires high-end and expensive devices for proper functioning.
  2.ARkit is only available on Iphone and ARcore (for androids) has recently launched its first version.
  3.Devices with Ground Plane detection are launched but are expensive.
  4.RealTime database is reqiuired for all users to play or to connect at a same time.
  
Solutions :-

  1.App has been created on Unity + Vuforia , which is a "CROSS PLATFORM Environment" thus apps for both Ios and Android can be build using it.
  2.For the sake of ground plane , Vuforia's GROUND PLANE image is used.
  3.Firebase is used to provide a Safe and Perfect environment for Realtime DB.
  
 glitches : 
      1.uneven background is shown at back of the screen (map like structure) on Turning on AR mode .
          --> this is due to unavailibilty of smart tracking on the phone.
          -->solution to this will be provided when more devices supporting smart tracking will be launched , supporting ARcore.
 
Future Updates and Add-ons :


  1.AR modules like decoding TExt, Seing animations and learning about Rajasthan Royal Culture etc.
  2.to Connect all users With a Chat Room
  
  
Authors :

Ujjwal Kumar, Hrishabh Sharma, Aman Pandey, Punit Jain


