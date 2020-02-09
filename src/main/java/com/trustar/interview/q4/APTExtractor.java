package com.trustar.interview.q4;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * This Class implements logic to extract all the APT Names, aliases and URLs from Mitre CTI repository. Keep in mind that
 * this solutions is based on Github structure and it relies on it.
 */
public class APTExtractor {


    public static final String APT_FILE_NAME_PATTERN = "(intrusion-set--\\S{36}\\.json)";
    public static final String APT_ALIASES_PATTERN = "\"aliases\": \\[\\s*((?:\"(?:\\w*[- \\w*]*)\"[,\\s]*)*)]";
    public static final String APT_NAME_PATTERN = "\"name\": \"(APT\\d{2})";
    public static final String APT_URL_PATTERN = "(http[s]+\\S*)[)\"]";
    public static final String BLACKLISTED_URLS_PATTERN = ".*(symantec\\.com|cybereason\\.com).*";
    private static final String FILE_LIST_URL = "https://github.com/mitre/cti/tree/master/enterprise-attack/intrusion-set";
    private static final String RAW_BASE_URL = "https://raw.githubusercontent.com/mitre/cti/master/enterprise-attack/intrusion-set/";

    public List<APTInfo> extract() {
        List aptInfoList = Collections.EMPTY_LIST;
        try {
            Set<String> aptFiles = getAptFileList();
            aptInfoList = aptFiles.parallelStream()
                    .map(this::getAPTInfoFromFile)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } catch (IOException ioe) {
            throw new RuntimeException("There was an error retrieving APT file list", ioe);
        }
        return aptInfoList;
    }

    /**
     * Search in Mitre CTI Github repo for those files that are listed inside `/enterprise-attack/intrusion-set` folder
     * As the file names follows a specific pattern, APT_FILE_NAME_PATTERN` was defined specific as well and it'll no match
     * files named under another convention.
     *
     * @return
     * @throws IOException
     */
    private Set<String> getAptFileList() throws IOException {
        Set<String> aptFiles = new HashSet<>();
        String content = getContentFromUrlAsString(FILE_LIST_URL);
        Pattern p = Pattern.compile(APT_FILE_NAME_PATTERN);
        Matcher m = p.matcher(content);
        while (m.find())
            aptFiles.add(m.group());
        return aptFiles;
    }

    /**
     * Given a fileName for an intrusion-set, this method downloads its content and check if the name matches pattern
     * <I>APTnn</I>. If the name is for an APT, then gets its aliases and related urls. <b>Symantec.com and Cybereasons.com
     * urls are discarded.</b>
     *
     * @param fileName
     * @return
     */
    private APTInfo getAPTInfoFromFile(String fileName) {
        APTInfo aptInfo = null;
        try {
            String content = getContentFromUrlAsString(RAW_BASE_URL.concat(fileName));
            Optional<String> name = Optional.ofNullable(extractName(content));
            if (name.isPresent()) {
                aptInfo = new APTInfo();
                aptInfo.setName(name.get());
                aptInfo.setRelatedUrls(extractUrls(content));
                aptInfo.setAliases(extractAliases(content));
            }
        } catch (IOException ioe) {
            Logger.getLogger("apt_errors").log(Level.SEVERE, String.format("Could not get file: %s", fileName));
        }
        return aptInfo;
    }

    /**
     * Given a url, gets the content and return it as a String.
     *
     * @param url
     * @return
     * @throws IOException
     */
    private String getContentFromUrlAsString(String url) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet(url);
        CloseableHttpResponse response = httpclient.execute(httpget);
        HttpEntity entity = response.getEntity();
        String content = EntityUtils.toString(entity);
        response.close();
        return content;
    }

    /**
     * This method applies APT_NAME_PATTERN looking for APTnn where nn are numbers. If it isn't a match, returns null.
     *
     * @param content
     * @return
     */
    private String extractName(String content) {
        Pattern pName = Pattern.compile(APT_NAME_PATTERN);
        Matcher mName = pName.matcher(content);
        if (mName.find())
            return mName.group(1);
        return null;
    }

    /**
     * Every file have a field for Aliases of an intrusion-set. For instance: APT29 is also known as "Cozy Bear". This
     * method applies aliases pattern in order to return them.
     *
     * @param content
     * @return
     */
    private String extractAliases(String content) {
        Pattern pAliases = Pattern.compile(APT_ALIASES_PATTERN);
        Matcher mAliases = pAliases.matcher(content);
        while (mAliases.find())
            return (mAliases.group(1).replaceAll("\\s*|", ""));
        return null;
    }


    /**
     * applies urls pattern and return all matches that not satisfy blacklisted pattern. In this case, we discard urls
     * from symantec.com and cybereason.com
     *
     * @param content
     * @return
     */
    private Set<String> extractUrls(String content) {
        Set<String> relatedUrls = new HashSet<>();
        Pattern pUrl = Pattern.compile(APT_URL_PATTERN);
        Matcher mUrls = pUrl.matcher(content);
        while (mUrls.find())
            if (!mUrls.group(1).matches(BLACKLISTED_URLS_PATTERN))
                relatedUrls.add(mUrls.group(1));
        return relatedUrls;
    }

}
