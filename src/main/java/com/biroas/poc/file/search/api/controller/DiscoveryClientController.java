package com.biroas.poc.file.search.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.ServiceUnavailableException;
import java.net.URI;
import java.util.Optional;

@RestController
public class DiscoveryClientController {

    @Autowired
    private DiscoveryClient discoveryClient;

    public Optional<URI> serviceUrl() {
        return discoveryClient.getInstances("poc-file-search-api")
                .stream()
                .map(si -> si.getUri())
                .findFirst();
    }

    @GetMapping("/discoveryClient")
    public String discoveryPing() throws ServiceUnavailableException {
        URI service = serviceUrl()
                .map(s -> s.resolve("/ping"))
                .orElseThrow(ServiceUnavailableException::new);
        return service.toString();
    }

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }
}
