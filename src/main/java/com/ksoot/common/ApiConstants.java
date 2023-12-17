package com.ksoot.common;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ApiConstants {

    public static final String RECORD_CREATED_RESPONSE =
            """
                    {
                        "messages": [
                            "Record created successfully"
                        ]
                    }
                    """;

    public static final String RECORD_UPDATED_RESPONSE =
            """
                    {
                        "messages": [
                            "Record updated successfully"
                        ]
                    }
                    """;

    public static final String RECORD_DELETED_RESPONSE =
            """
                    {
                        "messages": [
                            "Record deleted successfully"
                        ]
                    }
                    """;

    public static final String BAD_REQUEST_EXAMPLE_RESPONSE =
            """
                    {
                        "type": "about:blank",
                        "code": "constraint-violations",
                        "title": "Bad Request",
                        "status": 400,
                        "detail": "Constraint violations has happened, please correct the request and try again",
                        "instance": "/api/example",
                        "method": "POST",
                        "timestamp": "2023-09-28T22:33:05.781257+05:30",
                        "violations": [
                            {
                                "code": "XYZ-100",
                                "detail": "must not be empty",
                                "propertyPath": "name"
                            },
                            {
                                "code": "XYZ-101",
                                "detail": "must not be null",
                                "propertyPath": "type"
                            }
                        ]
                    }
                    """;

    public static final String UNAUTHORIZED_EXAMPLE_RESPONSE =
            """
                    {
                        "type": "about:blank",
                        "code": "401",
                        "title": "Unauthorized",
                        "status": 401,
                        "detail": "Full authentication is required to access this resource",
                        "instance": "/api/states",
                        "method": "POST",
                        "timestamp": "2023-09-28T22:40:28.480729+05:30"
                    }
                    """;

    public static final String FORBIDDEN_EXAMPLE_RESPONSE =
            """
                    {
                        "type": "about:blank",
                        "timestamp": "2023-09-28T22:40:28.480729+05:30",
                        "code": "403",
                        "title": "Forbidden",
                        "status": 403,
                        "detail": "Insufficient permissions to access the requested resource",
                        "instance": "/api/states",
                        "method": "POST"
                    }
                    """;

    public static final String NOT_FOUND_EXAMPLE_RESPONSE =
            """
                    {
                        "type": "about:blank",
                        "code": "404",
                        "title": "Not Found",
                        "status": 404,
                        "detail": "Requested resource not found",
                        "instance": "/vehicles/v1/fuel-types/1",
                        "method": "GET",
                        "timestamp": "2023-09-28T22:35:56.968474+05:30"
                    }
                    """;

    public static final String INTERNAL_SERVER_ERROR_EXAMPLE_RESPONSE =
            """
                    {
                        "type": "about:blank",
                        "code": "XYZ-123",
                        "title": "Internal Server Error",
                        "status": 500,
                        "detail": "Something has gone wrong, please contact administrator",
                        "instance": "/api/example",
                        "method": "POST",
                        "timestamp": "2023-09-28T22:24:54.886137+05:30"
                    }
                    """;
}
