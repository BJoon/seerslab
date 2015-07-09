package com.seerslab.parsetuto;

import com.parse.ParseFile;
import com.parse.ParseObject;

/**
 * Created by kimbyungjoon on 2015-07-02.
 */
public class parse extends ParseObject {
    public parse(){

    }

    public ParseFile getPhotoFile() {
        return getParseFile("photo");
    }

    public void setPhotoFile(ParseFile file) {
        put("photo", file);
    }



}
