package com.distrib.server.http;

public enum HttpStatusCode {
    
    /*------CLIENT ERROR CODES------- */
    CLIENT_ERROR_400_BAD_REQUEST(400, "Bad Request"),
    CLIENT_ERROR_405_METHOD_NOT_ALLOWED(405, "Method not allowed"),
    CLIENT_ERROR_414_URI_TOO_LONG(414, "URI too long"),
    /*------SERVER ERROR CODES------- */
    SERVER_ERROR_500_INTERNAL_SERVER_ERROR(500, "Internal server error"),
    SERVER_ERROR_501_NOT_IMPLEMENTED(501, "Method not implemented"), 
    SERVER_ERROR_505_HTTP_VERSION_NOT_SUPPORTED(505, "HTTP version not supported")
    ;

    public final int STATUS_CODE;
    public final String MESSAGE;

    HttpStatusCode(int STATUS_CODE, String MESSAGE) {
        this.STATUS_CODE = STATUS_CODE;
        this.MESSAGE = MESSAGE;
    }

}
