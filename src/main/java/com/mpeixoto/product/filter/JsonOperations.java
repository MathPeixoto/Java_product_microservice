package com.mpeixoto.product.filter;

import com.mpeixoto.product.exception.JsonPathException;
import com.mpeixoto.product.web.model.ProductDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.springframework.stereotype.Component;

/**
 * Class responsible for passing an object to json.
 *
 * @author mpeixoto
 */
@Component
public class JsonOperations {

  String parseToJson(Object object) throws JsonProcessingException {
    if (object == null) {
      return null;
    }
    return new ObjectMapper().writeValueAsString(object);
  }

  ProductDto deserialize(String jsonBody) {
    try {
      return new ObjectMapper().readValue(jsonBody, ProductDto.class);
    } catch (IOException e) {
      throw new JsonPathException("Wrong json path", e);
    }
  }
}
