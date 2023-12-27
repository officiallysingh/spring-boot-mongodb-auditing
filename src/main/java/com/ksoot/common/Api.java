package com.ksoot.common;

import static com.ksoot.common.ApiConstants.*;
import static com.ksoot.common.ApiStatus.*;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@ApiResponses(
    value = {
      @ApiResponse(
          responseCode = SC_401,
          description = "Unauthorized",
          content = @Content(examples = @ExampleObject(UNAUTHORIZED_EXAMPLE_RESPONSE))),
      @ApiResponse(
          responseCode = SC_403,
          description = "Forbidden",
          content = @Content(examples = @ExampleObject(FORBIDDEN_EXAMPLE_RESPONSE))),
      @ApiResponse(
          responseCode = SC_500,
          description = "Internal Server error",
          content = @Content(examples = @ExampleObject(INTERNAL_SERVER_ERROR_EXAMPLE_RESPONSE)))
    })
public interface Api {}
