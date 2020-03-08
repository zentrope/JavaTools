package com.zentrope.tools.json;


final class ParseException extends Exception {

    private static final long serialVersionUID = 6514111062718245288L;

    public ParseException() {
    }

    public ParseException(Exception e) {
        super(e);
    }

    public ParseException(String message) {
        super(message);
    }
}
