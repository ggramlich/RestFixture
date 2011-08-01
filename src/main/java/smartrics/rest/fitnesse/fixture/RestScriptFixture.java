package smartrics.rest.fitnesse.fixture;

import java.util.List;

import smartrics.rest.client.RestData.Header;
import smartrics.rest.fitnesse.fixture.support.BodyTypeAdapter;
import smartrics.rest.fitnesse.fixture.support.HeadersTypeAdapter;
import smartrics.rest.fitnesse.fixture.support.LetHandler;
import smartrics.rest.fitnesse.fixture.support.LetHandlerFactory;
import smartrics.rest.fitnesse.fixture.support.RestDataTypeAdapter;

public class RestScriptFixture extends RestFixture {

    public RestScriptFixture(String hostName) {
        super(hostName);
        initialize();
    }

    public RestScriptFixture(String hostName, String configName) {
        super(hostName, configName);
        initialize();
    }

    private void initialize() {
        initialize(Runner.SLIM);
    }

    public void get(String resourceUrl) {
        doMethod("Get", resourceUrl, null);
    }

    public void post(String resourceUrl) {
        doMethod("Post", resourceUrl, emptifyBody(getRequestBody()));
    }

    public void put(String resourceUrl) {
        doMethod("Put", resourceUrl, emptifyBody(getRequestBody()));
    }

    public void delete(String resourceUrl) {
        doMethod("Delete", resourceUrl, null);
    }

    public String header(String expr) {
        LetHandler letHandler = LetHandlerFactory.getHandlerFor("header");
        return letHandler.handle(getLastResponse(), namespaceContext, expr);
    }

    public String body(String expr) {
        LetHandler letHandler = LetHandlerFactory.getHandlerFor("body");
        return letHandler.handle(getLastResponse(), namespaceContext, expr);
    }

    public String js(String expr) {
        LetHandler letHandler = LetHandlerFactory.getHandlerFor("js");
        return letHandler.handle(getLastResponse(), namespaceContext, expr);
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setMultipartFileName(String multipartFileName) {
        this.multipartFileName = multipartFileName;
    }

    public void setMultipartFileParameterName(String multipartFileParameterName) {
        this.multipartFileParameterName = multipartFileParameterName;
    }

    public Integer statusCode() {
        if (getLastResponse() == null) {
            return -1;
        }
        return getLastResponse().getStatusCode();
    }

    public List<Header> headers() {
        if (getLastResponse() == null) {
            return null;
        }
        return getLastResponse().getHeaders();
    }

    public boolean hasBody(String expected) throws Exception {
        BodyTypeAdapter bodyTypeAdapter = getBodyTypeAdapter();
        String actual = responseBody();
        return equalsWithAdapter(expected, actual, bodyTypeAdapter);
    }

    public boolean hasHeader(String expected) throws Exception {
        return equalsWithAdapter(expected, headers(), new HeadersTypeAdapter());
    }

    public String responseBody() {
        return getLastResponse().getBody();
    }

    protected boolean equalsWithAdapter(String expected, Object actual, RestDataTypeAdapter typeAdapter) throws Exception {
        typeAdapter.set(actual);
        Object parse = typeAdapter.parse(expected);
        return typeAdapter.equals(parse, actual);
    }

}
