package com.distrib.server.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Logger;

public class HttpParser {

    private final static Logger LOGGER = (Logger) LoggerFactory.getLogger(HttpParser.class);

    private static final int SP = 0x20; // 32
    private static final int CR = 0x0D; // 13
    private static final int LF = 0x0A; // 10

    public HttpRequest parseHttpRequest(InputStream inputStream) throws HttpParsingException, BadHttpVersionException {
        InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.US_ASCII);

        HttpRequest request = new HttpRequest();

        try {
            parseRequestLine(reader, request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        parseHeaders(reader, request);
        parseBody(reader, request);

        return request;
    }

    private void parseRequestLine(InputStreamReader reader, HttpRequest req) throws IOException, HttpParsingException, BadHttpVersionException{
        StringBuilder dataBuffer = new StringBuilder();

        boolean methodParsed = false;
        boolean requestTargetParsed = false;

        int _byte;

        while ((_byte = reader.read()) >= 0) {
            if(_byte == CR) {
                _byte = reader.read();
                if(_byte == LF) {
                    LOGGER.debug("Request line VERSION to process : {}", dataBuffer.toString());
                    if (!methodParsed || !requestTargetParsed) {
                        throw new HttpParsingException(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
                    }
                    try {
                        req.setVersion(dataBuffer.toString());
                    } catch (BadHttpVersionException e) {
                        throw new HttpParsingException(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
                    }
                    return;
                } else {
                    throw new HttpParsingException(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
                }
            }

            if (_byte == SP) {
                // process previous data
                if(!methodParsed) {
                    LOGGER.debug("Request line METHOD to process : {}", dataBuffer.toString());
                    methodParsed = true;
                    req.setMethod(dataBuffer.toString());
                } else if (!requestTargetParsed) {
                    LOGGER.debug("Request line TARGET to process : {}", dataBuffer.toString());
                    requestTargetParsed = true;
                    req.setTarget(dataBuffer.toString());
                }
                
                dataBuffer.delete(0, dataBuffer.length());
            } else {
                // keep adding data to buffer
                dataBuffer.append((char)_byte);

                //Adding checks to see if method is parsed yet or not
                if(!methodParsed) {
                    if (dataBuffer.length() > HttpMethod.MAX_LENGTH) {
                        throw new HttpParsingException(HttpStatusCode.SERVER_ERROR_501_NOT_IMPLEMENTED);
                    }
                }
            }
        }
    }

    private void parseBody(InputStreamReader reader, HttpRequest req) {
        
    }

    private void parseHeaders(InputStreamReader reader, HttpRequest req) {
        
    }

    

}
