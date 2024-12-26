package com.stackjs.ssoclient.service;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import com.stackjs.ssoclient.dto.MessageDto;

@Service
public class MessageService {

    private static final Logger logger = LoggerFactory.getLogger(MessageService.class);
    private final RestClient restClient;
    private final String resourceServerUrl;

    public MessageService(
        RestClient restClient,
        @Value("${resource-server.url}") String resourceServerUrl)
    {
        this.restClient = restClient;
        this.resourceServerUrl = resourceServerUrl;
    }

    public List<MessageDto> findAll() throws RestClientException, RuntimeException {
        MessageDto[] messageDtos;
        try {
            messageDtos = restClient.get()
                    .uri(resourceServerUrl + "/messages")
                    .retrieve()
                    .body(MessageDto[].class);
        } catch (RestClientException exception) {
            logger.error(exception.getMessage(), exception);
            throw exception;
        } catch (RuntimeException exception) {
            logger.error(exception.getMessage(), exception);
            throw exception;
        }

        return Arrays.asList(messageDtos);
    }

    public MessageDto findById(long id) throws RestClientException, RuntimeException {
        MessageDto messageDto;
        try {
            messageDto = restClient.get()
                    .uri(resourceServerUrl + "/messages/" + id)
                    .retrieve()
                    .body(MessageDto.class);
        } catch (RestClientException exception) {
            logger.error(exception.getMessage(), exception);
            throw exception;
        } catch (RuntimeException exception) {
            logger.error(exception.getMessage(), exception);
            throw exception;
        }

        return messageDto;
    }

    public MessageDto save(MessageDto messageDto) throws RestClientException, RuntimeException {
        try {
            restClient.post()
                    .uri(resourceServerUrl + "/messages")
                    .body(messageDto)
                    .retrieve()
                    .toBodilessEntity();
        } catch (RestClientException exception) {
            logger.error(exception.getMessage(), exception);
            throw exception;
        } catch (RuntimeException exception) {
            logger.error(exception.getMessage(), exception);
            throw exception;
        }

        return messageDto;
    }

}
