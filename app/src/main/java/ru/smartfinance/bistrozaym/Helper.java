package ru.smartfinance.bistrozaym;

import android.content.Context;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    /**
     * Парсит домен из url
     * @param url String
     * @return String
     * @throws URISyntaxException
     */
    public String parseDomainName(String url) throws URISyntaxException {
        URI uri = new URI(url);
        String domain = uri.getHost();
        return domain.startsWith("www.") ? domain.substring(4) : domain;
    }

    /**
     * Проверяет, что строка - email
     * @param email String
     * @return boolean
     */
    public boolean isEmail(String email) {
        return Pattern.matches("[a-zA-Z_\\-0-9]+@[a-zA-Z_\\-0-9]+?\\.[a-zA-Z]{2,6}", email);
    }

    /**
     * Проверяет, что строка - номер телефона
     * @param phone String
     * @return boolean
     */
    public boolean isPhone(String phone) {
        return Pattern.matches("\\+?\\d[-(]?\\d{3}[-)]?\\d{3}-?\\d{2}-?\\d{2}", phone);
    }
}
