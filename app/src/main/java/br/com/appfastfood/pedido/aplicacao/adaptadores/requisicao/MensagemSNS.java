package br.com.appfastfood.pedido.aplicacao.adaptadores.requisicao;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MensagemSNS {
    @JsonProperty("Type")
    private String type;
    @JsonProperty("MessageId")
    private String messageId;

    @JsonProperty("TopicArn")
    private String topicArn;

    @JsonProperty("Message")
    private String message;

    @JsonProperty("Timestamp")
    private String timestamp;

    @JsonProperty("Token")
    private String token;

    @JsonProperty("SubscribeURL")
    private String subscribeURL;

    @JsonProperty("MessageAttributes")
    private String messageAttributes;

    @JsonProperty("SignatureVersion")
    private String signatureVersion;


    public void setMessageAttributes(String messageAttributes) {
        this.messageAttributes = messageAttributes;
    }

    public String getMessageAttributes() {
        return messageAttributes;
    }

    public void setSignatureVersion(String signatureVersion) {
        this.signatureVersion = signatureVersion;
    }

    public String getSignatureVersion() {
        return signatureVersion;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String getType() {
        return type;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setTopicArn(String topicArn) {
        this.topicArn = topicArn;
    }

    public String getTopicArn() {
        return topicArn;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setSubscribeURL(String subscribeURL) {
        this.subscribeURL = subscribeURL;
    }

    public String getSubscribeURL() {
        return subscribeURL;
    }

}