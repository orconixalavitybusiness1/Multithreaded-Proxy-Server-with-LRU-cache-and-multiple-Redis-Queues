public class Request {
    private final String requestId;
    private final String payload;

    public Request(String requestId, String payload) {
        this.requestId = requestId;
        this.payload = payload;
    }

    @Override
    public String toString() {
        return "Request{" +
                "requestId='" + requestId + '\'' +
                ", payload='" + payload + '\'' +
                '}';
    }

    public String getRequestId() {
        return requestId;
    }

    public String getPayload() {
        return payload;
    }
}
