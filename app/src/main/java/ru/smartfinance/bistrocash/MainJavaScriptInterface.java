package ru.smartfinance.bistrocash;

import android.webkit.JavascriptInterface;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

class MainJavaScriptInterface {

    @JavascriptInterface
    public void handleHtml(String html) {
        Document doc = Jsoup.parse(html);
        Helper helper = BistroCashApp.getComponent().getHelper();

        ArrayList<String> domains = new ArrayList<>();
        Elements links = doc.select("section.s2 div.single a[href]");
        for (Element link : links) {
            String domain = helper.parseQueryParam("aff_sub-name", link.attr("href"));
            if (domain != null) {
                domains.add(domain);
            }
        }
        if (domains.size() > 0) {
            helper.setPartnerDomains(domains);
        }
    }
}
