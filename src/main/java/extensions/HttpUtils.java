package extensions;

import org.openqa.selenium.WebElement;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HttpUtils {

    public static boolean isLinkValid(String link) {
        try {
            HttpURLConnection huc = (HttpURLConnection)(new URL(link).openConnection());
            huc.setRequestMethod("HEAD");
            huc.connect();

            int responseCode = huc.getResponseCode();

            if (responseCode >= 400) {
                return false;
            } else {
                return true;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public static List<String> LinksValidation(List<String> listLinks) {
        List<String> tmpList = new ArrayList<>();
        for (String link : listLinks) {
            boolean validLink = isLinkValid(link);
            if (!validLink) {
                tmpList.add(link);
            }
        }
        return tmpList;
    }


    public static List<String> LinksValidation(List<WebElement> listLinks, String attribute) {
        List<String> tmpList = new ArrayList<>();
        for (int i = 0; i < listLinks.size(); i++) {
            String link = listLinks.get(i).getAttribute(attribute);
            boolean validLink = isLinkValid(link);
            if (!validLink) {
                tmpList.add(link);
            }
        }
        return tmpList;
    }

}
