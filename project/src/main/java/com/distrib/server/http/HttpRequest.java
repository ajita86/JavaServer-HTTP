package com.distrib.server.http;

public class HttpRequest {

    private HttpMethod method;
    private String target;
    private String originalHttpVersion;
    private HttpVersion bestCompatiblVersion;

    HttpRequest() {}
    
    HttpMethod getMethod() {
        return method;
    }

    void setMethod(String methodName) throws HttpParsingException {
        for (HttpMethod method: HttpMethod.values()) {
            if(methodName.equals(method.name())) {
                this.method = HttpMethod.valueOf(methodName);
                return;
            }
        }
        throw new HttpParsingException(HttpStatusCode.SERVER_ERROR_501_NOT_IMPLEMENTED);
    }

    String getTarget() {
        return target;
    }

    void setTarget(String target) throws HttpParsingException {
        if (target == null || target.length() == 0) {
            throw new HttpParsingException(HttpStatusCode.SERVER_ERROR_500_INTERNAL_SERVER_ERROR);
        }
        this.target = target;
    }

    HttpVersion getVersion() {
        return bestCompatiblVersion;
    }

    void setVersion(String version) throws HttpParsingException, BadHttpVersionException {
        this.originalHttpVersion = version;
        this.bestCompatiblVersion = HttpVersion.getBestCompatibleVersion(version);
        if (this.bestCompatiblVersion == null) {
            throw new HttpParsingException(
                HttpStatusCode.SERVER_ERROR_505_HTTP_VERSION_NOT_SUPPORTED
            );
        }
    }
}
