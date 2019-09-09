package idv.jerry.dramalist.utils;

import android.content.Context;
import android.content.SharedPreferences;

import idv.jerry.dramalist.config.AppConfig;

public class SharedPreferencesUtil {

    private static SharedPreferences getSharedPreferences(Context context){
        if (context == null){
            return null;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences(AppConfig.preferenceName, Context.MODE_PRIVATE);
        return sharedPreferences;
    }

    private static SharedPreferences.Editor getEditor (SharedPreferences sharedPreferences){
        if (sharedPreferences == null){
            return null;
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        return editor;
    }

    public static void putString (Context context, String key, String value){

        SharedPreferences sharedPreferences = getSharedPreferences(context);

        if (sharedPreferences != null){
            SharedPreferences.Editor editor = getEditor(sharedPreferences);
            if (editor != null){
                editor.putString(key, value);
                editor.commit();
            }
        }


    }

    public static String getString (Context context, String key){
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        String value = "";
        if (sharedPreferences != null){
            value = sharedPreferences.getString(key, "");
        }
        return value;
    }

    public static void clearString(Context context, String key){
        SharedPreferences sharedPreferences = getSharedPreferences(context);

        if (sharedPreferences != null){
            SharedPreferences.Editor editor = getEditor(sharedPreferences);

            if (editor != null){
                editor.putString(key, "");
                editor.commit();
            }
        }
    }
}
