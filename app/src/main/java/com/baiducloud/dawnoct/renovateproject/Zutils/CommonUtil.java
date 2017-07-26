package com.baiducloud.dawnoct.renovateproject.Zutils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.os.Vibrator;
import android.view.inputmethod.InputMethodManager;

import java.util.List;

/**
 * Created by zhangjie on 2017/5/9.
 */

public class CommonUtil {
    public static final String locadate="[\n" +
            "    {\n" +
            "        \"bridges\": [\n" +
            "            {\n" +
            "                \"id\": \"001\",\n" +
            "                \"name\": \"江山大桥\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": \"002\",\n" +
            "                \"name\": \"江山大桥2\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": \"003\",\n" +
            "                \"name\": \"江山大桥3\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": \"004\",\n" +
            "                \"name\": \"江山大桥4\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"id\": \"001\",\n" +
            "        \"name\": \"阿项目1\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"bridges\": [\n" +
            "            {\n" +
            "                \"id\": \"005\",\n" +
            "                \"name\": \"江山大桥5\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": \"006\",\n" +
            "                \"name\": \"江山大桥6\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": \"007\",\n" +
            "                \"name\": \"江山大桥7\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": \"008\",\n" +
            "                \"name\": \"江山大桥8\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"id\": \"002\",\n" +
            "        \"name\": \"比项目2\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"bridges\": [\n" +
            "            {\n" +
            "                \"id\": \"009\",\n" +
            "                \"name\": \"江山大桥9\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": \"0010\",\n" +
            "                \"name\": \"江山大桥10\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": \"0011\",\n" +
            "                \"name\": \"江山大桥11\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": \"0012\",\n" +
            "                \"name\": \"江山大桥12\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"id\": \"003\",\n" +
            "        \"name\": \"啊项目3\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"bridges\": [\n" +
            "            {\n" +
            "                \"id\": \"009\",\n" +
            "                \"name\": \"江山大桥9\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": \"0010\",\n" +
            "                \"name\": \"江山大桥10\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": \"0011\",\n" +
            "                \"name\": \"江山大桥11\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": \"0012\",\n" +
            "                \"name\": \"江山大桥12\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"id\": \"003\",\n" +
            "        \"name\": \"啊项目3\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"bridges\": [\n" +
            "            {\n" +
            "                \"id\": \"009\",\n" +
            "                \"name\": \"江山大桥9\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": \"0010\",\n" +
            "                \"name\": \"江山大桥10\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": \"0011\",\n" +
            "                \"name\": \"江山大桥11\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": \"0012\",\n" +
            "                \"name\": \"江山大桥12\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"id\": \"003\",\n" +
            "        \"name\": \"啊项目3\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"bridges\": [\n" +
            "            {\n" +
            "                \"id\": \"009\",\n" +
            "                \"name\": \"江山大桥9\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": \"0010\",\n" +
            "                \"name\": \"江山大桥10\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": \"0011\",\n" +
            "                \"name\": \"江山大桥11\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": \"0012\",\n" +
            "                \"name\": \"江山大桥12\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"id\": \"003\",\n" +
            "        \"name\": \"啊项目3\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"bridges\": [\n" +
            "            {\n" +
            "                \"id\": \"009\",\n" +
            "                \"name\": \"江山大桥9\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": \"0010\",\n" +
            "                \"name\": \"江山大桥10\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": \"0011\",\n" +
            "                \"name\": \"江山大桥11\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": \"0012\",\n" +
            "                \"name\": \"江山大桥12\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"id\": \"003\",\n" +
            "        \"name\": \"吧项目3\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"bridges\": [\n" +
            "            {\n" +
            "                \"id\": \"009\",\n" +
            "                \"name\": \"江山大桥9\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": \"0010\",\n" +
            "                \"name\": \"江山大桥10\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": \"0011\",\n" +
            "                \"name\": \"江山大桥11\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": \"0012\",\n" +
            "                \"name\": \"江山大桥12\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"id\": \"003\",\n" +
            "        \"name\": \"吧项目3\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"bridges\": [\n" +
            "            {\n" +
            "                \"id\": \"009\",\n" +
            "                \"name\": \"江山大桥9\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": \"0010\",\n" +
            "                \"name\": \"江山大桥10\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": \"0011\",\n" +
            "                \"name\": \"江山大桥11\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": \"0012\",\n" +
            "                \"name\": \"江山大桥12\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"id\": \"003\",\n" +
            "        \"name\": \"此项目3\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"bridges\": [\n" +
            "            {\n" +
            "                \"id\": \"009\",\n" +
            "                \"name\": \"江山大桥9\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": \"0010\",\n" +
            "                \"name\": \"江山大桥10\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": \"0011\",\n" +
            "                \"name\": \"江山大桥11\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": \"0012\",\n" +
            "                \"name\": \"江山大桥12\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"id\": \"003\",\n" +
            "        \"name\": \"此项目3\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"bridges\": [\n" +
            "            {\n" +
            "                \"id\": \"009\",\n" +
            "                \"name\": \"江山大桥9\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": \"0010\",\n" +
            "                \"name\": \"江山大桥10\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": \"0011\",\n" +
            "                \"name\": \"江山大桥11\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": \"0012\",\n" +
            "                \"name\": \"江山大桥12\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"id\": \"003\",\n" +
            "        \"name\": \"此项目3\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"bridges\": [\n" +
            "            {\n" +
            "                \"id\": \"009\",\n" +
            "                \"name\": \"江山大桥9\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": \"0010\",\n" +
            "                \"name\": \"江山大桥10\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": \"0011\",\n" +
            "                \"name\": \"江山大桥11\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": \"0012\",\n" +
            "                \"name\": \"江山大桥12\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"id\": \"003\",\n" +
            "        \"name\": \"此项目3\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"bridges\": [\n" +
            "            {\n" +
            "                \"id\": \"009\",\n" +
            "                \"name\": \"江山大桥9\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": \"0010\",\n" +
            "                \"name\": \"江山大桥10\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": \"0011\",\n" +
            "                \"name\": \"江山大桥11\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": \"0012\",\n" +
            "                \"name\": \"江山大桥12\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"id\": \"003\",\n" +
            "        \"name\": \"此项目3\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"bridges\": [\n" +
            "            {\n" +
            "                \"id\": \"009\",\n" +
            "                \"name\": \"江山大桥9\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": \"0010\",\n" +
            "                \"name\": \"江山大桥10\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": \"0011\",\n" +
            "                \"name\": \"江山大桥11\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": \"0012\",\n" +
            "                \"name\": \"江山大桥12\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"id\": \"003\",\n" +
            "        \"name\": \"此项目3\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"bridges\": [\n" +
            "            {\n" +
            "                \"id\": \"009\",\n" +
            "                \"name\": \"江山大桥9\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": \"0010\",\n" +
            "                \"name\": \"江山大桥10\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": \"0011\",\n" +
            "                \"name\": \"江山大桥11\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": \"0012\",\n" +
            "                \"name\": \"江山大桥12\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"id\": \"003\",\n" +
            "        \"name\": \"的项目3\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"bridges\": [\n" +
            "            {\n" +
            "                \"id\": \"009\",\n" +
            "                \"name\": \"江山大桥9\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": \"0010\",\n" +
            "                \"name\": \"江山大桥10\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": \"0011\",\n" +
            "                \"name\": \"江山大桥11\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": \"0012\",\n" +
            "                \"name\": \"江山大桥12\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"id\": \"003\",\n" +
            "        \"name\": \"的项目3\"\n" +
            "    }\n" +
            "]";
    public static final String localData = "[\n" +
            "    {\n" +
            "        \"bridges\": [\n" +
            "            {\n" +
            "                \"id\": \"001\",\n" +
            "                \"name\": \"江山大桥\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": \"002\",\n" +
            "                \"name\": \"江山大桥2\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": \"003\",\n" +
            "                \"name\": \"江山大桥3\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": \"004\",\n" +
            "                \"name\": \"江山大桥4\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"id\": \"001\",\n" +
            "        \"name\": \"阿项目1\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"bridges\": [\n" +
            "            {\n" +
            "                \"id\": \"005\",\n" +
            "                \"name\": \"江山大桥5\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": \"006\",\n" +
            "                \"name\": \"江山大桥6\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": \"007\",\n" +
            "                \"name\": \"江山大桥7\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": \"008\",\n" +
            "                \"name\": \"江山大桥8\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"id\": \"002\",\n" +
            "        \"name\": \"波项目2\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"bridges\": [\n" +
            "            {\n" +
            "                \"id\": \"009\",\n" +
            "                \"name\": \"江山大桥9\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": \"0010\",\n" +
            "                \"name\": \"江山大桥10\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": \"0011\",\n" +
            "                \"name\": \"江山大桥11\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": \"0012\",\n" +
            "                \"name\": \"江山大桥12\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"id\": \"003\",\n" +
            "        \"name\": \"啊项目3\"\n" +
            "    }\n" +
            "]";
    /**
     * 是否存在外部存储
     *
     * @return
     */
    public static boolean isExternalStorageExists() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return true;
        }
        return false;
    }

    public static void hideKeyboard(Activity activity) {
        try {
            ((InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    /*
    * 打开设置网络界面
    */
    public static void setNetwork(final Context context) {
        // 提示对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        try {
            builder.setTitle("网络设置提示")
                    .setMessage("网络连接不可用,是否进行设置?")
                    .setPositiveButton("设置",
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    Intent intent = null;
                                    // 判断手机系统的版本 即API大于10 就是3.0或以上版本
                                    if (android.os.Build.VERSION.SDK_INT > 10) {
                                        intent = new Intent(
                                                android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                                    } else {
                                        intent = new Intent();
                                        ComponentName component = new ComponentName(
                                                "com.android.settings",
                                                "com.android.settings.WirelessSettings");
                                        intent.setComponent(component);
                                        intent.setAction("android.intent.action.VIEW");
                                    }
                                    context.startActivity(intent);
                                }
                            })
                    .setNegativeButton("取消",
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    dialog.dismiss();
                                }
                            }).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 调用震动器
     *
     * @param context      调用该方法的Context
     * @param milliseconds 震动的时长，单位是毫秒
     */
    public static void vibrate(final Context context, long milliseconds) {
        Vibrator vib = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
        vib.vibrate(milliseconds);
    }

    /**
     * 调用震动器
     *
     * @param context  调用该方法的Context
     * @param pattern  自定义震动模式 。数组中数字的含义依次是[静止时长，震动时长，静止时长，震动时长。。。]时长的单位是毫秒
     * @param isRepeat 是否反复震动，如果是true，反复震动，如果是false，只震动一次
     */
    public static void vibrate(final Context context, long[] pattern, boolean isRepeat) {
        Vibrator vib = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
        vib.vibrate(pattern, isRepeat ? 1 : -1);
    }

    /**
     * 播放音乐
     *
     * @param context
     */
    public static void playMusic(Context context) {
//        MediaPlayer mp = MediaPlayer.create(context, R.raw.beep);
//        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
//        mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//            @Override
//            public void onPrepared(MediaPlayer mediaPlayer) {
//                mediaPlayer.start();
//            }
//        });
//        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//            @Override
//            public void onCompletion(MediaPlayer mediaPlayer) {
//                mediaPlayer.release();
//            }
//        });
    }

    /**
     * 拆分List集合
     */
    public static List[] subList(List list, int pageSize) {
        //list: 要拆分的列表
        //pageSize: 每个子列表条数
        int total = list.size();
        //总页数
        int pageCount = total % pageSize == 0 ? total / pageSize : total / pageSize + 1;
        List[] result = new List[pageCount];
        for (int i = 0; i < pageCount; i++) {
            int start = i * pageSize;
            //最后一条可能超出总数
            int end = start + pageSize > total ? total : start + pageSize;
            List subList = list.subList(start, end);
            result[i] = subList;
        }
        return result;
    }
}
