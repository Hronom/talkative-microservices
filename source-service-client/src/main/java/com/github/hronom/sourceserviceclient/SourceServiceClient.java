package com.github.hronom.sourceserviceclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedResources;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "source-service", configuration = SourceServiceClientConfig.class)
public interface SourceServiceClient {
    @RequestMapping(method = RequestMethod.GET, value = "/entries", consumes = MediaTypes.HAL_JSON_VALUE)
    PagedResources<Entry> getStores(@RequestParam("page") long page, @RequestParam("size") long size);
}
