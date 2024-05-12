package br.com.fiap.pedidos.domain.service;

import br.com.fiap.pedidos.domain.exception.ErroConverterObjetoParaJsonException;
import br.com.fiap.pedidos.domain.model.MensagemEmail;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SQSService {

    @Value("${aws.sqs.endpoint.envia-emails}")
    private String sqsEndpoint;

    private final AmazonSQS sqsClient;

    private final ObjectMapper objectMapper;


    public SQSService(AmazonSQS amazonSQS, ObjectMapper objectMapper) {
        this.sqsClient = amazonSQS;
        this.objectMapper = objectMapper;
    }

    public void enviarMensagem(MensagemEmail mensagem) {
        SendMessageRequest sendMessageRequest = null;

        try {
            sendMessageRequest = new SendMessageRequest()
                    .withQueueUrl(sqsEndpoint)
                    .withMessageBody(objectMapper.writeValueAsString(mensagem));
            sqsClient.sendMessage(sendMessageRequest);
        } catch (JsonProcessingException e) {
            throw new ErroConverterObjetoParaJsonException("Erro ao converter MensagemEmail para json");
        }
    }
}
