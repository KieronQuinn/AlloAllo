package com.quinny898.xposed.alloallo;

import android.content.Intent;
import android.content.res.XModuleResources;
import android.widget.HorizontalScrollView;

import java.util.Collection;
import java.util.HashSet;

import de.robv.android.xposed.IXposedHookInitPackageResources;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_InitPackageResources;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

import static de.robv.android.xposed.XposedHelpers.findAndHookConstructor;
import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

public class Xposed implements IXposedHookLoadPackage, IXposedHookZygoteInit, IXposedHookInitPackageResources {
    private String MODULE_PATH;

    public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {
        if (!lpparam.packageName.equals("com.google.android.apps.fireball") && !lpparam.packageName.equals("com.google.android.apps.tachyon"))
            return;
        if (lpparam.packageName.equals("com.google.android.apps.fireball")) {
            //This overrides the method that starts the "WelcomeActivity", which blocks access to the Conversation List Activity
            findAndHookMethod("byk", lpparam.classLoader, "b", int.class, new XC_MethodReplacement() {
                @Override
                protected Object replaceHookedMethod(MethodHookParam methodHookParam) throws Throwable {
                    //Attempt to trigger whatever the Google Talk thing is
                    /*
                    This does nothing and so is disabled

                    Class ghp = XposedHelpers.findClass("ghp", lpparam.classLoader);
                    Context context = (Context) methodHookParam.thisObject;
                    XposedHelpers.callStaticMethod(ghp, "a", context.getPackageManager());
                    Object bms = XposedHelpers.findClass("bms", lpparam.classLoader).newInstance();
                    XposedHelpers.setObjectField(bms, "c", context);
                    XposedHelpers.callMethod(bms, "g");*/

                    //Just kill the method, no need to do anything else is there
                    return null;
                }
            });

            //Methods used in testing to see if I could force enable the option to create a new message. I switched to editing the database to have messages already exist, but left these in for future reference
            /*
            findAndHookMethod("com.google.android.apps.fireball.datamodel.NetworkServiceImpl", lpparam.classLoader, "a", Intent.class, new XC_MethodReplacement() {
                @Override
                protected Object replaceHookedMethod(MethodHookParam methodHookParam) throws Throwable {
                    return 1;
                }
            });

            findAndHookMethod("com.google.android.apps.fireball.datamodel.NetworkServiceImpl", lpparam.classLoader, "c", int.class, new XC_MethodReplacement() {
                @Override
                protected Object replaceHookedMethod(MethodHookParam methodHookParam) throws Throwable {
                    //Argument 0 is an int, but we're ignoring that and always returning registered
                    //I don't think this actually makes any difference but I left it enabled just in case
                    String v0 = "VERIFICATION_STATE_REGISTERED";
                    return v0;
                }


            });*/

            //A load of test hooks that were supposed to add schemes used for SMS (sms, smsto, mms and mmsto) to a list used internally. Don't work, so are disabled
            /*
            findAndHookMethod("daq", lpparam.classLoader, "a", Uri.class, File.class, String.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(final MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    Log.d("AlloAllo", "Force SMS a1");
                    forceSMS(lpparam);
                }
            });

            findAndHookMethod("daq", lpparam.classLoader, "a", Uri.class, Uri.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(final MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    Log.d("AlloAllo", "Force SMS a2");
                    forceSMS(lpparam);
                }
            });

            findAndHookMethod("daq", lpparam.classLoader, "c", Uri.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(final MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    Log.d("AlloAllo", "Force SMS c");
                    forceSMS(lpparam);
                }
            });

            findAndHookMethod("daq", lpparam.classLoader, "f", Uri.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(final MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    Log.d("AlloAllo", "Force SMS f");
                    forceSMS(lpparam);
                }
            });
            */

            //Enable all debug flags except "crash on errors", by flipping them before and during methods that use them. Do enable some debug features, but nothing notable

            /*
                REFERENCE:
                beq.a = new grl("fb.debug_features", 0);
                beq.b = new grj("fb.crash_on_errors");
                beq.c = new grl("fb.enable_self_conversation", 0);
                beq.d = new grl("fb.use_test_certificate", 0);
                beq.e = new grl("fb.enable_suggestion_feedback", 0);
                beq.f = new grl("fb.enable_sms_relay_test", 0);
                beq.g = new grj("fb.bypass_ext_conv_checks");

                debug_features is probably the debug menu in the navigation drawer
                crash_on_errors is not enabled because Analytics are used and I'd spam Google with errors
                enable_self_conversation is probably a way to send messages to yourself, but needs an account
                use_test_certificate appears to use a test certificate when uploading images
                enable_sms_relay_test was what I was looking for, but seems to do nothing
                bypass_ext_conv_checks bypasses if the number has an account, and again seems to do nothing
             */
            findAndHookMethod("ber", lpparam.classLoader, "a", new XC_MethodReplacement() {
                @Override
                protected Object replaceHookedMethod(MethodHookParam methodHookParam) throws Throwable {
                    return true;
                }
            });

            findAndHookConstructor("bgb", lpparam.classLoader, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(final MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    XposedHelpers.setIntField(param.thisObject, "b", 1);
                    XposedHelpers.setBooleanField(param.thisObject, "c", true);
                }
            });

            findAndHookMethod("chh", lpparam.classLoader, "a", HorizontalScrollView.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(final MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    Class beq = XposedHelpers.findClass("beq", lpparam.classLoader);
                    Class grl = XposedHelpers.findClass("grl", lpparam.classLoader);
                    Object pass = grl.getConstructor(String.class, byte.class).newInstance("fb.enable_suggestion_feedback", (byte) 1);
                    XposedHelpers.setObjectField(beq, "e", pass);
                }
            });

            findAndHookMethod("coi", lpparam.classLoader, "call", new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(final MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    Class beq = XposedHelpers.findClass("beq", lpparam.classLoader);
                    Class grl = XposedHelpers.findClass("grl", lpparam.classLoader);
                    Object pass = grl.getConstructor(String.class, byte.class).newInstance("fb.bypass_ext_conv_checks", (byte) 1);
                    XposedHelpers.setObjectField(beq, "g", pass);
                }
            });

            Class addContactsResponse = XposedHelpers.findClass("media.webrtc.server.tachyon.proto.nano.TachyonUserdata$AddContactsResponse", lpparam.classLoader);
            findAndHookMethod("com.google.android.apps.fireball.datamodel.protohandlers.AddContactsHandler", lpparam.classLoader, "handleResult", addContactsResponse, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(final MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    Class beq = XposedHelpers.findClass("beq", lpparam.classLoader);
                    Class grl = XposedHelpers.findClass("grl", lpparam.classLoader);
                    Object pass = grl.getConstructor(String.class, byte.class).newInstance("fb.enable_sms_relay_test", (byte) 1);
                    XposedHelpers.setObjectField(beq, "f", pass);
                }
            });

            Class createGroupResponse = XposedHelpers.findClass("media.webrtc.server.tachyon.proto.nano.TachyonGroup$CreateGroupResponse", lpparam.classLoader);
            findAndHookMethod("com.google.android.apps.fireball.datamodel.protohandlers.CreateGroupHandler", lpparam.classLoader, "handleResult", createGroupResponse, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(final MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    Class beq = XposedHelpers.findClass("beq", lpparam.classLoader);
                    Class grl = XposedHelpers.findClass("grl", lpparam.classLoader);
                    Object pass = grl.getConstructor(String.class, byte.class).newInstance("fb.enable_sms_relay_test", (byte) 1);
                    XposedHelpers.setObjectField(beq, "f", pass);
                }
            });

            Class getProfileResponse = XposedHelpers.findClass("media.webrtc.server.tachyon.proto.nano.TachyonUserdata$GetProfileResponse", lpparam.classLoader);
            findAndHookMethod("com.google.android.apps.fireball.datamodel.protohandlers.GetProfileHandler", lpparam.classLoader, "handleResult", getProfileResponse, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(final MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    Class beq = XposedHelpers.findClass("beq", lpparam.classLoader);
                    Class grl = XposedHelpers.findClass("grl", lpparam.classLoader);
                    Object pass = grl.getConstructor(String.class, byte.class).newInstance("fb.enable_sms_relay_test", (byte) 1);
                    XposedHelpers.setObjectField(beq, "f", pass);
                }
            });

            Class tachyonCommonId = XposedHelpers.findClass("media.webrtc.server.tachyon.proto.nano.TachyonCommon$Id", lpparam.classLoader);
            findAndHookMethod("czj", lpparam.classLoader, "a", String.class, int.class, tachyonCommonId, Intent.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(final MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    Class beq = XposedHelpers.findClass("beq", lpparam.classLoader);
                    Class grl = XposedHelpers.findClass("grl", lpparam.classLoader);
                    Object pass = grl.getConstructor(String.class, byte.class).newInstance("fb.use_test_certificate", (byte) 1);
                    XposedHelpers.setObjectField(beq, "d", pass);
                }
            });
            //END OF DEBUG FEATURES

            //A test hook that returned whether the method that contained google talk's package name was used. It was. Disabled but left for checking
            /*
            findAndHookMethod("ghp", lpparam.classLoader, "a", PackageManager.class, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(final MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    Log.d("AlloAllo", "ghp returned " + Boolean.toString((Boolean) param.getResult()));
                }
            });
            */


        } else {
            //not-yet-working Duo testing
            /*
            Log.d("AlloAllo", "Got tachyon");
            Class xk = XposedHelpers.findClass("xk", lpparam.classLoader);
            findAndHookConstructor("akj", lpparam.classLoader, xk, boolean.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(final MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    Log.d("AlloAllo", "akj");
                }
            });

            findAndHookConstructor("zt", lpparam.classLoader, int.class, Fragment.class, String.class, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(final MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    if(param.args[2].equals("Registration")){
                        Object f = XposedHelpers.findClass("xx", lpparam.classLoader).getConstructor().newInstance();
                        XposedHelpers.setObjectField(param.thisObject, "b", f);
                    }
                }
            });*/
        }


    }

    //A now unused method that added sms, mms, smsto and mmsto to a list of schemes on demand
    private void forceSMS(LoadPackageParam lpparam) {
        Class hcp = XposedHelpers.findClass("hcp", lpparam.classLoader);
        Class daq = XposedHelpers.findClass("daq", lpparam.classLoader);
        Object input = XposedHelpers.callMethod(hcp, "a", "android.resource", "content", "file", "fireball", "sms", "mms", "smsto", "mmsto");
        XposedHelpers.setObjectField(daq, "a", new HashSet((Collection) input));
    }


    //Resource redirection
    @Override
    public void initZygote(IXposedHookZygoteInit.StartupParam startupParam) throws Throwable {
        MODULE_PATH = startupParam.modulePath;
    }

    //Replace stuff for Fireball (Allo)
    @Override
    public void handleInitPackageResources(XC_InitPackageResources.InitPackageResourcesParam resparam) throws Throwable {
        if (!resparam.packageName.equals("com.google.android.apps.fireball"))
            return;

        XModuleResources modRes = XModuleResources.createInstance(MODULE_PATH, resparam.res);
        //Icon -> Icon
        resparam.res.setReplacement("com.google.android.apps.fireball", "mipmap", "ic_launcher", modRes.fwd(R.mipmap.allo));
        //String -> Allo
        resparam.res.setReplacement("com.google.android.apps.fireball", "string", "app_name", modRes.fwd(R.string.allo));
    }
}