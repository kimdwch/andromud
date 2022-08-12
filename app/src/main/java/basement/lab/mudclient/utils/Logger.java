/*
 Created by KIMDaewon 2019-12-20
 Hynix eMail : skhy.x0112862@partner.sk.com
 eMail : kimdwch@gmail.com

 - description
 출력하는 로그의 위치 및 보안성 검토시 로그 메시지 제거 해야 하는 관계로
 로그 클래스 별도 작성
*/
package basement.lab.mudclient.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import basement.lab.mudclient.BuildConfig;

public final class Logger {
    public static boolean DEBUG_BUILD = BuildConfig.DEBUG; // 릴리즈 빌드시 로그 표시 안함
    public static final String TAG = "### mymud ###";
    private final static int MAX_LEN = 2000; // 2000 bytes 마다 끊어서 출력
    private static final int JSON_INDENT = 2;

    public static final int FILE = 0;
    public static final int VERBOSE = 1;
    public static final int DEBUG = 2;
    public static final int INFO = 3;
    public static final int WARN = 4;
    public static final int ERROR = 5;
    public static final int NO_LOG = 6;  // 로그메시지 표시 안함

    private static int MSG_LEVEL = DEBUG;   // 초기값.
    private static boolean m_bProguardStyle = false;   // 초기값

    public static void setLogMsgStyle(int nLogMsgLevel, boolean bProguardMsgStyle) {
        MSG_LEVEL = nLogMsgLevel;
        m_bProguardStyle = bProguardMsgStyle;
    }

    /**
     * Do not send a log message.
     */
    public static void x(String tag, String msg) {
        return;
    }

    /**
     * Send a {@link #VERBOSE} log message.
     *
     * @param msg The message you would like logged.
     */
    public static void v(String tag, String msg) {
        if (VERBOSE < MSG_LEVEL /*&& !DEBUG_BUILD*/)
            return;
        StackTraceElement[] ste = (new Throwable()).getStackTrace();
        String totalMsg = makeMsg(msg, ste);
        Log.v(tag, totalMsg);
    }

    /**
     * Send a {@link #VERBOSE} log message and log the exception.
     *
     * @param msg The message you would like logged.
     * @param tr  An exception to log
     */
    public static void v(String tag, String msg, Throwable tr) {
        if (VERBOSE < MSG_LEVEL /*&& !DEBUG_BUILD*/)
            return;
        StackTraceElement[] ste = (new Throwable()).getStackTrace();
        String totalMsg = makeMsg(msg, ste);
        Log.v(tag, totalMsg, tr);
    }
    public static void e(String msg) {
        if (DEBUG < MSG_LEVEL || !DEBUG_BUILD)
            return;
        StackTraceElement[] ste = (new Throwable()).getStackTrace();
        String totalMsg = makeMsg(msg, ste);
//        Log.e(TAG, totalMsg);
        cutlogmsg(ERROR,totalMsg);
    }
    public static void w(String msg) {
        if (DEBUG < MSG_LEVEL || !DEBUG_BUILD)
            return;
        StackTraceElement[] ste = (new Throwable()).getStackTrace();
        String totalMsg = makeMsg(msg, ste);
//        Log.w(TAG, totalMsg);
        cutlogmsg(WARN,totalMsg);
    }
    public static void i(String msg) {
        if (DEBUG < MSG_LEVEL || !DEBUG_BUILD)
            return;
        StackTraceElement[] ste = (new Throwable()).getStackTrace();
        String totalMsg = makeMsg(msg, ste);
        cutlogmsg(INFO,totalMsg);
    }
    public static void d(String msg) {
        if (DEBUG < MSG_LEVEL || !DEBUG_BUILD)
            return;
        StackTraceElement[] ste = (new Throwable()).getStackTrace();
        String totalMsg = makeMsg(msg, ste);
        cutlogmsg(DEBUG,totalMsg);
    }
    public static void f(String msg) {
        cutlogmsg(FILE,msg);
    }
    /**
     * Send a {@link #DEBUG} log message.
     *
     * @param msg The message you would like logged.
     */
    public static void d(String tag, String msg) {
        if (DEBUG < MSG_LEVEL /*&& !DEBUG_BUILD*/)
            return;
        StackTraceElement[] ste = (new Throwable()).getStackTrace();
        String totalMsg = makeMsg(msg, ste);
        Log.d(tag, totalMsg);
    }

    /**
     * Send a {@link #DEBUG} log message and log the exception.
     *
     * @param msg The message you would like logged.
     * @param tr  An exception to log
     */
    public static void d(String tag, String msg, Throwable tr) {
        if (DEBUG < MSG_LEVEL /*&& !DEBUG_BUILD*/)
            return;
        StackTraceElement[] ste = (new Throwable()).getStackTrace();
        String totalMsg = makeMsg(msg, ste);
        Log.d(tag, totalMsg, tr);
    }

    /**
     * Send a {@link #INFO} log message.
     *
     * @param msg The message you would like logged.
     */
    public static void i(String tag, String msg) {
        if (INFO < MSG_LEVEL /*&& !DEBUG_BUILD*/)
            return;
        StackTraceElement[] ste = (new Throwable()).getStackTrace();
        String totalMsg = makeMsg(msg, ste);
        Log.i(tag, totalMsg);
    }

    /**
     * Send a {@link #INFO} log message and log the exception.
     *
     * @param msg The message you would like logged.
     * @param tr  An exception to log
     */
    public static void i(String tag, String msg, Throwable tr) {
        if (INFO < MSG_LEVEL /*&& !DEBUG_BUILD*/)
            return;
        StackTraceElement[] ste = (new Throwable()).getStackTrace();
        String totalMsg = makeMsg(msg, ste);
        Log.i(tag, totalMsg, tr);
    }

    /**
     * Send a {@link #WARN} log message.
     *
     * @param msg The message you would like logged.
     */
    public static void w(String tag, String msg) {
        if (WARN < MSG_LEVEL)
            return;
        StackTraceElement[] ste = (new Throwable()).getStackTrace();
        String totalMsg = makeMsg(msg, ste);
        Log.w(tag, totalMsg);
    }

    /**
     * Send a {@link #WARN} log message and log the exception.
     *
     * @param msg The message you would like logged.
     * @param tr  An exception to log
     */
    public static void w(String tag, String msg, Throwable tr) {
        if (WARN < MSG_LEVEL)
            return;
        StackTraceElement[] ste = (new Throwable()).getStackTrace();
        String totalMsg = makeMsg(msg, ste);
        Log.w(tag, totalMsg, tr);
    }

    /**
     * Send a {@link #ERROR} log message.
     *
     * @param msg The message you would like logged.
     */
    public static void e(String tag, String msg) {
        if (ERROR < MSG_LEVEL)
            return;
        StackTraceElement[] ste = (new Throwable()).getStackTrace();
        String totalMsg = makeMsg(msg, ste);
        Log.e(tag, totalMsg);
    }

    /**
     * Send a {@link #ERROR} log message and log the exception.
     *
     * @param msg The message you would like logged.
     * @param tr  An exception to log
     */
    public static void e(String tag, String msg, Throwable tr) {
        if (ERROR < MSG_LEVEL)
            return;
        StackTraceElement[] ste = (new Throwable()).getStackTrace();
        String totalMsg = makeMsg(msg, ste);
        Log.e(tag, totalMsg, tr);
    }

    public static void t(final Context context, final String msg)
    {
        if (ERROR < MSG_LEVEL)
            return;
        StackTraceElement[] ste = (new Throwable()).getStackTrace();
        String totalMsg = makeMsg(msg, ste);
        Log.i(TAG, "Toast : "+totalMsg);
        new Thread(new Runnable() {
            final Context con = context;
            final String str = totalMsg;
            @Override
            public void run() {
                try {
                    Toast.makeText(con, str, Toast.LENGTH_SHORT).show();
                }catch (RuntimeException e)
                {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private static String makeMsg(String msg, StackTraceElement[] ste) {
        StringBuilder strMsg = new StringBuilder();
        String mdata = json(xml(msg));
        if (m_bProguardStyle) {
            strMsg.append(String.format(" at %s.%s(%s:%s)\n", ste[1].getClassName(), ste[1].getMethodName(), ste[1].getFileName(), ste[1].getLineNumber()));
        }
        strMsg.append(String.format("(%s:%s) %s()* [%s]", ste[1].getFileName(), ste[1].getLineNumber(), ste[1].getMethodName(), mdata));

        return strMsg.toString();
    }
    public static boolean isEmpty(CharSequence str) {
        return str == null || str.length() == 0;
    }
    private static String json(@Nullable String json)
    {
        if (isEmpty(json)) {
            return "Empty/Null json content";
        }
        json = json.trim();
        try {
            if (json.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(json);
                return "\n"+jsonObject.toString(JSON_INDENT)+"\n";
            }
        }
        catch (JSONException e)
        {
            return json;
        }
        try {
            if (json.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(json);
                return "\n"+jsonArray.toString(JSON_INDENT)+"\n";
            }
        }
        catch (JSONException e)
        {
            return json;
        }

        return json;
    }
    private static String xml(@Nullable String xml)
    {
        if (isEmpty(xml)) {
            return "Empty/Null xml content";
        }
        try {
            Source xmlInput = new StreamSource(new StringReader(xml));
            StreamResult xmlOutput = new StreamResult(new StringWriter());
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.transform(xmlInput, xmlOutput);
            return "\n"+xmlOutput.getWriter().toString().replaceFirst(">", ">\n")+"\n";
        } catch (TransformerException e) {
            return xml;
        }
    }
    public static void saveFile(Context context, String str)
    {
        String path = context.getFilesDir()+"/logs/";
        File dir = new File(path);
        if(!dir.exists())
            if(!dir.mkdir())
            {
                d("Log dir make failed");
                return;
            }
        File logfile = new File(path+System.currentTimeMillis()+".log");

        if(!logfile.exists()) {
            FileWriter writer = null;
            try {
                logfile.createNewFile();
                writer = new FileWriter(logfile, true);
                writer.write(str);
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    writer.close();
                }catch (IOException | NullPointerException e)
                {
                    e.printStackTrace();
                }
            }
        }

        /*FileWriter fw = null;
        try {
            fw = new FileWriter(LogFileInfo.path+"/"+LogFileInfo.name);
            fw.write(str);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
    }

    private static String cutlogmsg(int lv, String s)
    {
        String logmsg = s;

        int len = logmsg.length();
        if(len > MAX_LEN) {
            int idx = 0, nextIdx = 0;
            while(idx < len) {
                String msg;
                nextIdx += MAX_LEN;
                msg = logmsg.substring(idx, Math.min(nextIdx, len));
                idx = nextIdx;
                switch (lv)
                {
                    case ERROR:
                        Log.e(TAG, msg);
                        break;
                    case INFO:
                        Log.i(TAG, msg);
                        break;
                    case VERBOSE:
                        Log.v(TAG, msg);
                        break;
                    case WARN:
                        Log.w(TAG, msg);
                        break;
                    case FILE:
                        return msg;
                    default:
                        Log.d(TAG, msg);
                        break;
                }
            }
        } else {
            switch (lv)
            {
                case ERROR:
                    Log.e(TAG, logmsg);
                    break;
                case INFO:
                    Log.i(TAG, logmsg);
                    break;
                case VERBOSE:
                    Log.v(TAG, logmsg);
                    break;
                case WARN:
                    Log.w(TAG, logmsg);
                    break;
                case FILE:
                    return logmsg;
                default:
                    Log.d(TAG, logmsg);
                    break;
            }
        }
        return s;
    }
}
