package com.bankcuscatlan.productservice.infraestructura.config.client;

import com.bankcuscatlan.productservice.model.dto.fakeapi.Product;
import com.bankcuscatlan.productservice.model.dto.product.ResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "fake-apiproducts", url = "${fake-api.host}",
        configuration = FeignConfig.class)
public interface FakeApiClient {

    @GetMapping(value = "${fake-api.path}/{id}")
    Product getProduct(@PathVariable Integer id);
}
