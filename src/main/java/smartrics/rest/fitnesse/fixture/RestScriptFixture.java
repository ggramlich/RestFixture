package smartrics.rest.fitnesse.fixture;

import java.util.List;

import smartrics.rest.client.RestData.Header;
import smartrics.rest.fitnesse.fixture.support.BodyTypeAdapter;
import smartrics.rest.fitnesse.fixture.support.HeadersTypeAdapter;
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

    public Integer statusCode() {
        return getLastResponse().getStatusCode();
    }

    public List<Header> headers() {
        return getLastResponse().getHeaders();
    }

    public boolean hasBody(String expected) throws Exception {
        BodyTypeAdapter bodyTypeAdapter = getBodyTypeAdapter();
        String actual = responseBody();
        return equalsWithAdapter(expected, actual, bodyTypeAdapter);
    }

    public boolean hasHeader(String expected) throws Exception {
        List<Header> lastHeaders = getLastResponse().getHeaders();
        return equalsWithAdapter(expected, lastHeaders, new HeadersTypeAdapter());
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
