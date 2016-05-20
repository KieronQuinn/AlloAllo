# AlloAllo
An Xposed module that allows access to SOME features of Allo without authentication, bypassing the SMS code requirement

Note that AlloAllo is only **part** of the requirements to get Allo to launch and show messages, the other being a modified database to actually have messages and user show up

[The pre-modified Titanium Backup can be found here](https://drive.google.com/open?id=0BwduK1C1PXjxMTVoN21YelF1bGs), however you **will** need to use the Xposed module as well as it

## AlloAllo:
- Disables the "Welcome" activity, removing the phone number verificiation
- Enables a ton of debug flags in Allo, including the debug option in the navigation drawer
- Includes commented out code designed to poke around at the incomplete SMS/Hangouts code in Allo (it does nothing)
- Includes a disabled activity that fired intents at Allo, again with no results
- Changes the app name to "Allo" and the icon to the newer one (to fool your friends or something)

## It does not:

- Enable you to use Allo as a service
- Allow you to sign into Allo (Even if you link your account, you **do not** get verified or signed in)
- Allow you to send images, text or anything, even to other users with the module
- Compromise any of Google's services, it is simply a client-side bypass and is essentially an "offline mode"

It was made for fun, to see if I could at least have a look around the app before its release, and I released the APK + Titanium Backup so others could too

## Database Documention (DIY conversations & users)
Coming soon
