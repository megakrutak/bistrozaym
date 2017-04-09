package ru.smartfinance.bistrozaym;

import android.content.Context;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


class Helper {

    private Context context;
    private ArrayList<String> partnerDomains;

    Helper(Context context) {
        this.context = context;
    }

    /**
     * Возвращает название партнера по имени домена
     *
     * @param url String
     * @return String
     */
    String getPartnerNameByUrl(String url) throws URISyntaxException {
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
     * @throws URISyntaxException Исключение
     */
    private String parseDomainName(String url) throws URISyntaxException {
        URI uri = new URI(url);
        String domain = uri.getHost();
        return domain.startsWith("www.") ? domain.substring(4) : domain;
    }

    /**
     * Проверяет, что строка - email
     * @param email String
     * @return boolean
     */
    boolean isEmail(String email) {
        return Pattern.matches("[a-zA-Z_\\-0-9]+@[a-zA-Z_\\-0-9]+?\\.[a-zA-Z]{2,6}", email);
    }

    /**
     * Проверяет, что строка - номер телефона
     * @param phone String
     * @return boolean
     */
    boolean isPhone(String phone) {
        return Pattern.matches("\\+?\\d[-(]?\\d{3}[-)]?\\d{3}-?\\d{2}-?\\d{2}", phone);
    }

    /**
     * Заполняет список доменов партнеров
     *
     * @param partnerDomains ArrayList<String>
     */
    void setPartnerDomains(ArrayList<String> partnerDomains) {
        this.partnerDomains = partnerDomains;
    }

    /**
     * Возвращает список доменов-партнеров
     *
     * @return String[]
     */
    ArrayList<String> getPartnerDomains() {
        return partnerDomains;
    }

    /**
     * Парсит параметр query-запроса
     *
     * @param key String
     * @param query String
     * @return String
     */
    String parseQueryParam(String key, String query) {
        String[] params = query.split("&");
        for (String param: params) {
            String[] keyValue = param.split("=");
            if (keyValue.length > 1 && keyValue[0].equals(key)) {
                return keyValue[1];
            }
        }

        return null;
    }
}
