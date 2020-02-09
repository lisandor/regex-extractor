package com.trustar.interview.q4;

import java.util.HashSet;
import java.util.Set;

public class APTInfo {

    private String name;
    private String aliases;
    private Set<String> relatedUrls;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAliases() {
        return aliases;
    }

    public void setAliases(String aliases) {
        this.aliases = aliases;
    }

    public Set<String> getRelatedUrls() {
        return relatedUrls;
    }

    public void setRelatedUrls(Set<String> relatedUrls) {
        this.relatedUrls = relatedUrls;
    }

    public void addRelatedUrl(String url) {
        if (relatedUrls == null)
            relatedUrls = new HashSet<>();
        relatedUrls.add(url);
    }

    @Override
    public String toString() {
        return "APTInfo{" +
                "name='" + name + '\'' +
                ", aliases=" + aliases +
                ", relatedUrls=" + relatedUrls +
                '}';
    }

}
