import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class LanguageCodes {

    private HashMap<String, String> alpha2_languages;
    private HashMap<String, String> alpha3_b_languages;

    /**
     * alpha3_b_languages used by OpenSubtitles with small modifications.
     * alpha2_languages used by most applications like Plex.
     */
    public LanguageCodes() {
        alpha2_languages = new HashMap<>();
        alpha3_b_languages = new HashMap<>();

        alpha2_languages.put("af", "Afrikaans");
        alpha2_languages.put("sq", "Albanian");
        alpha2_languages.put("ar", "Arabic");
        alpha2_languages.put("an", "Aragonese");
        alpha2_languages.put("hy", "Armenian");
        alpha2_languages.put("ast", "Asturian");
        alpha2_languages.put("az", "Azerbaijani");
        alpha2_languages.put("eu", "Basque");
        alpha2_languages.put("be", "Belarusian");
        alpha2_languages.put("bn", "Bengali");
        alpha2_languages.put("bs", "Bosnian");
        alpha2_languages.put("br", "Breton");
        alpha2_languages.put("bg", "Bulgarian");
        alpha2_languages.put("my", "Burmese");
        alpha2_languages.put("ca", "Catalan");
        alpha2_languages.put("zh-Hans", "Chinese (simplified)");
        alpha2_languages.put("zh-Hant", "Chinese (traditional)");
        alpha2_languages.put("zh", "Chinese bilingual");
        alpha2_languages.put("hr", "Croatian");
        alpha2_languages.put("cs", "Czech");
        alpha2_languages.put("da", "Danish");
        alpha2_languages.put("nl", "Dutch");
        alpha2_languages.put("en", "English");
        alpha2_languages.put("eo", "Esperanto");
        alpha2_languages.put("et", "Estonian");
        alpha2_languages.put("ext", "Extremaduran");
        alpha2_languages.put("fi", "Finnish");
        alpha2_languages.put("fr", "French");
        alpha2_languages.put("gl", "Galician");
        alpha2_languages.put("ka", "Georgian");
        alpha2_languages.put("de", "German");
        alpha2_languages.put("el", "Greek");
        alpha2_languages.put("he", "Hebrew");
        alpha2_languages.put("hi", "Hindi");
        alpha2_languages.put("hu", "Hungarian");
        alpha2_languages.put("is", "Icelandic");
        alpha2_languages.put("id", "Indonesian");
        alpha2_languages.put("it", "Italian");
        alpha2_languages.put("ja", "Japanese");
        alpha2_languages.put("kn", "Kannada");
        alpha2_languages.put("kk", "Kazakh");
        alpha2_languages.put("km", "Khmer");
        alpha2_languages.put("ko", "Korean");
        alpha2_languages.put("ku", "Kurdish");
        alpha2_languages.put("lv", "Latvian");
        alpha2_languages.put("lt", "Lithuanian");
        alpha2_languages.put("lb", "Luxembourgish");
        alpha2_languages.put("mk", "Macedonian");
        alpha2_languages.put("ml", "Malay");
        alpha2_languages.put("ml", "Malayalam");
        alpha2_languages.put("mni", "Manipuri");
        alpha2_languages.put("mn", "Mongolian");
        alpha2_languages.put("mne", "Montenegrin");
        alpha2_languages.put("no", "Norwegian");
        alpha2_languages.put("oc", "Occitan");
        alpha2_languages.put("fa", "Persian");
        alpha2_languages.put("pl", "Polish");
        alpha2_languages.put("pt", "Portuguese");
        alpha2_languages.put("pob", "Portuguese (BR)");
        alpha2_languages.put("pom", "Portuguese (MZ)");
        alpha2_languages.put("ro", "Romanian");
        alpha2_languages.put("ru", "Russian");
        alpha2_languages.put("sr", "Serbian");
        alpha2_languages.put("si", "Sinhalese");
        alpha2_languages.put("sk", "Slovak");
        alpha2_languages.put("sl", "Slovenian");
        alpha2_languages.put("es", "Spanish");
        alpha2_languages.put("sw", "Swahili");
        alpha2_languages.put("sv", "Swedish");
        alpha2_languages.put("syc", "Syriac");
        alpha2_languages.put("tl", "Tagalog");
        alpha2_languages.put("ta", "Tamil");
        alpha2_languages.put("te", "Telugu");
        alpha2_languages.put("th", "Thai");
        alpha2_languages.put("tr", "Turkish");
        alpha2_languages.put("uk", "Ukrainian");
        alpha2_languages.put("ur", "Urdu");
        alpha2_languages.put("vi", "Vietnamese");

        alpha3_b_languages.put("afr", "Afrikaans");
        alpha3_b_languages.put("alb", "Albanian");
        alpha3_b_languages.put("ara", "Arabic");
        alpha3_b_languages.put("arg", "Aragonese");
        alpha3_b_languages.put("arm", "Armenian");
        alpha3_b_languages.put("ast", "Asturian");
        alpha3_b_languages.put("aze", "Azerbaijani");
        alpha3_b_languages.put("baq", "Basque");
        alpha3_b_languages.put("bel", "Belarusian");
        alpha3_b_languages.put("ben", "Bengali");
        alpha3_b_languages.put("bos", "Bosnian");
        alpha3_b_languages.put("bre", "Breton");
        alpha3_b_languages.put("bul", "Bulgarian");
        alpha3_b_languages.put("bur", "Burmese");
        alpha3_b_languages.put("cat", "Catalan");
        alpha3_b_languages.put("chi", "Chinese (simplified)");
        alpha3_b_languages.put("zht", "Chinese (traditional)");
        alpha3_b_languages.put("zhe", "Chinese bilingual");
        alpha3_b_languages.put("hrv", "Croatian");
        alpha3_b_languages.put("cze", "Czech");
        alpha3_b_languages.put("dan", "Danish");
        alpha3_b_languages.put("dut", "Dutch");
        alpha3_b_languages.put("eng", "English");
        alpha3_b_languages.put("epo", "Esperanto");
        alpha3_b_languages.put("est", "Estonian");
        alpha3_b_languages.put("ext", "Extremaduran");
        alpha3_b_languages.put("fin", "Finnish");
        alpha3_b_languages.put("fre", "French");
        alpha3_b_languages.put("glg", "Galician");
        alpha3_b_languages.put("geo", "Georgian");
        alpha3_b_languages.put("ger", "German");
        alpha3_b_languages.put("ell", "Greek");
        alpha3_b_languages.put("heb", "Hebrew");
        alpha3_b_languages.put("hin", "Hindi");
        alpha3_b_languages.put("hun", "Hungarian");
        alpha3_b_languages.put("ice", "Icelandic");
        alpha3_b_languages.put("ind", "Indonesian");
        alpha3_b_languages.put("ita", "Italian");
        alpha3_b_languages.put("jpn", "Japanese");
        alpha3_b_languages.put("kan", "Kannada");
        alpha3_b_languages.put("kaz", "Kazakh");
        alpha3_b_languages.put("khm", "Khmer");
        alpha3_b_languages.put("kor", "Korean");
        alpha3_b_languages.put("kur", "Kurdish");
        alpha3_b_languages.put("lav", "Latvian");
        alpha3_b_languages.put("lit", "Lithuanian");
        alpha3_b_languages.put("ltz", "Luxembourgish");
        alpha3_b_languages.put("mac", "Macedonian");
        alpha3_b_languages.put("may", "Malay");
        alpha3_b_languages.put("mal", "Malayalam");
        alpha3_b_languages.put("mni", "Manipuri");
        alpha3_b_languages.put("mon", "Mongolian");
        alpha3_b_languages.put("mne", "Montenegrin");
        alpha3_b_languages.put("nor", "Norwegian");
        alpha3_b_languages.put("oci", "Occitan");
        alpha3_b_languages.put("per", "Persian");
        alpha3_b_languages.put("pol", "Polish");
        alpha3_b_languages.put("por", "Portuguese");
        alpha3_b_languages.put("pob", "Portuguese (BR)");
        alpha3_b_languages.put("pom", "Portuguese (MZ)");
        alpha3_b_languages.put("rum", "Romanian");
        alpha3_b_languages.put("rus", "Russian");
        alpha3_b_languages.put("src", "Serbian");
        alpha3_b_languages.put("sin", "Sinhalese");
        alpha3_b_languages.put("slo", "Slovak");
        alpha3_b_languages.put("slv", "Slovenian");
        alpha3_b_languages.put("spa", "Spanish");
        alpha3_b_languages.put("swa", "Swahili");
        alpha3_b_languages.put("swe", "Swedish");
        alpha3_b_languages.put("syr", "Syriac");
        alpha3_b_languages.put("tgl", "Tagalog");
        alpha3_b_languages.put("tam", "Tamil");
        alpha3_b_languages.put("tel", "Telugu");
        alpha3_b_languages.put("tha", "Thai");
        alpha3_b_languages.put("tur", "Turkish");
        alpha3_b_languages.put("ukr", "Ukrainian");
        alpha3_b_languages.put("urd", "Urdu");
        alpha3_b_languages.put("vie", "Vietnamese");
    }

    public String getISO639_2(String languageName) {
        for (String key : alpha2_languages.keySet()) {
            if (alpha2_languages.get(key).equals(languageName)) {
                return key;
            }
        }
        return null;
    }

    public String convertISO6392_ToISO639_3(String languageCode) {
        String languageName = alpha3_b_languages.get(languageCode);

        return getISO639_2(languageName);
    }

    public String getISO639_3(String languageName) {
        for (String key : alpha3_b_languages.keySet()) {
            if (alpha3_b_languages.get(key).equals(languageName)) {
                return key;
            }
        }
        return null;
    }

    public String getLanguageName(String languageCode) {
        return alpha3_b_languages.get(languageCode);
    }

    public ArrayList<String> getAllLanguages() {
        ArrayList<String> list = new ArrayList<>();
        list.addAll(alpha3_b_languages.values());
        Collections.sort(list);

        return list;
    }

    public boolean containsKey(String key) {
        return alpha3_b_languages.containsKey(key);
    }
}
