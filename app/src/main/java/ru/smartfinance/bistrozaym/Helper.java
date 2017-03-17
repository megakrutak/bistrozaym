package ru.smartfinance.bistrozaym;

import android.content.Context;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by mihail on 18.03.2017.
 */

public class Helper {

    private Context context;

    public Helper(Context context) {
        this.context = context;
    }

    /**
     * Возвращает название партнера по имени домена
     *
     * @param url String
     * @return String
     */
    public String getPartnerNameByUrl(String url) throws URISyntaxException {
        String domain = parseDomainName(url);
        String[] array = context.getResources().getStringArray(R.array.partner_names);

        for (String str : array) {
            StringTokenizer st = new StringTokenizer(str, "|");
            String partnerName = st.nextToken();
            String domainName = st.nextToken();
            if (domainName.equals(domain)) {
                return partnerName;
            }
        }
        return domain;
    }


    public String parseDomainName(String url) throws URISyntaxException {
        URI uri = new URI(url);
        String domain = uri.getHost();
        return domain.startsWith("www.") ? domain.substring(4) : domain;
    }
}
