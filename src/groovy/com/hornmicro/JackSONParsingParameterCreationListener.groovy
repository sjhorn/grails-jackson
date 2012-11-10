package com.hornmicro

import grails.util.GrailsNameUtils

import javax.servlet.http.HttpServletRequest

import org.apache.commons.io.IOUtils
import org.apache.commons.logging.LogFactory
import org.codehaus.groovy.grails.web.converters.AbstractParsingParameterCreationListener
import org.codehaus.groovy.grails.web.converters.Converter
import org.codehaus.groovy.grails.web.converters.JSONParsingParameterCreationListener
import org.codehaus.groovy.grails.web.converters.exceptions.ConverterException
import org.codehaus.groovy.grails.web.servlet.mvc.GrailsParameterMap

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper

class JackSONParsingParameterCreationListener extends AbstractParsingParameterCreationListener {
    static final String CACHED_JSON = "org.codehaus.groovy.grails.CACHED_JSON_REQUEST_CONTENT"
    static final LOG = LogFactory.getLog(JSONParsingParameterCreationListener)

    void paramsCreated(GrailsParameterMap params) {
        def request = params.getRequest()
        if (request.format != 'json') {
            return
        }

        try {
            Map map = parse(request)
            def flattenedMap = map
            if (map['class']) {
                params[GrailsNameUtils.getPropertyName(map['class'])] = map
            } else if (map) {
                for (entry in map) {
                    params[entry.key] = entry.value
                }
                flattenedMap = params
            }

//            def target = [:]
//            createFlattenedKeys(map, map, target)
//            for (entry in target) {
//                if (!map[entry.key]) {
//                    flattenedMap[entry.key] = entry.value
//                }
//            }
//            params.updateNestedKeys(target)
        } catch (Exception e) {
            LOG.error "Error parsing incoming JSON request: ${e.message}", e
        }
    }
    
    Map parse(HttpServletRequest request) throws ConverterException {
        Object json = request.getAttribute(CACHED_JSON)
        if (json != null) return json

        String encoding = request.getCharacterEncoding()
        if (encoding == null) {
            encoding = Converter.DEFAULT_REQUEST_ENCODING
        }
        try {
            PushbackInputStream pushbackInputStream = null
            int firstByte = -1
            try {
                pushbackInputStream = new PushbackInputStream(request.getInputStream())
                firstByte = pushbackInputStream.read()
            } catch (IOException ioe) {
            
            }
            
            if(firstByte == -1) {
                return [:]
            }
            
            pushbackInputStream.unread(firstByte)
            
            ObjectMapper mapper = new ObjectMapper()
            //mapper.configure(JsonGenerator.Feature.AUTO_CLOSE_JSON_CONTENT, false)
            json = mapper.readValue(IOUtils.toString(pushbackInputStream, encoding), Map)
            
            request.setAttribute(CACHED_JSON, json)
            return json
        } catch (JsonParseException e) {
            throw new ConverterException("Error parsing JSON", e)
        }
    }
}
