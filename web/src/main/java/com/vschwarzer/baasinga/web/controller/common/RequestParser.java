package com.vschwarzer.baasinga.web.controller.common;

import org.springframework.stereotype.Component;

/**
 * Created by Vincent Schwarzer on 22.07.15.
 */
@Component
public class RequestParser {

    private final String ID_SEPERATOR = "-";

    public int getModelIndex(String reqString) {
        return Integer.valueOf(reqString.substring(0, reqString.indexOf(ID_SEPERATOR)));
    }

    public int getAttributeOrRelationIndex(String reqString) {
        return Integer.valueOf(reqString.substring(reqString.indexOf(ID_SEPERATOR) + 1, reqString.length()));
    }


}
