package a800.com.drwithyouapp1st;
import org.json.JSONObject;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
//import net.sf.json.JSONObject;
//import net.sf.json.util.JSONTokener;

/**
 * Created by lenovo on 2017/5/10.
 */

public class UrlHtmlUtil {
    public static JSONObject getHtmlJsonByUrl(String urlTemp){
        URL url = null;
        InputStreamReader input = null;
        HttpURLConnection conn;
        JSONObject jsonObj = null;
        try {
            url = new URL(urlTemp);
            conn = (HttpURLConnection) url.openConnection();
            input = new InputStreamReader(conn.getInputStream(),"utf-8");
            Scanner inputStream = new Scanner(input);
            StringBuffer sb = new StringBuffer();
            while (inputStream.hasNext()) {
                sb.append(inputStream.nextLine());
            }
//            jsonObj = JSONObject.fromObject(sb.toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return jsonObj;

    }
}