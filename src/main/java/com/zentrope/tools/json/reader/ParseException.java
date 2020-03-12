package com.zentrope.tools.json.reader;


final class ParseException extends Exception {

    private static final long serialVersionUID = 6514111062718245288L;

    public ParseException() {
    }

    public ParseException(final Exception e) {
        super(e);
    }

    public ParseException(final String message) {
        super(message);
    }
}
