package com.ksoot.common;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ApiStatus {

    // =========================
    // --- 1xx Informational ---

    /**
     * {@code 100 Continue}.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7231#section-6.2.1">HTTP/1.1: Semantics and
     * Content, section 6.2.1</a>
     */
    public static final String SC_100 = "100";

    /**
     * {@code 101 Switching Protocols}.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7231#section-6.2.2">HTTP/1.1: Semantics and
     * Content, section 6.2.2</a>
     */
    public static final String SC_101 = "101";

    /**
     * {@code 102 Processing}.
     *
     * @see <a href="https://tools.ietf.org/html/rfc2518#section-10.1">WebDAV</a>
     */
    public static final String SC_102 = "102";

    /**
     * {@code 103 Early Hints}.
     *
     * @see <a href="https://tools.ietf.org/html/rfc8297">An HTTP Status Code for Indicating Hints</a>
     * @since 6.0.5
     */
    public static final String SC_103 = "103";

    // ===================
    // --- 2xx Success ---
    /**
     * {@code 200 OK}.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7231#section-6.3.1">HTTP/1.1: Semantics and
     * Content, section 6.3.1</a>
     */
    public static final String SC_200 = "200";

    /**
     * {@code 201 Created}.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7231#section-6.3.2">HTTP/1.1: Semantics and
     * Content, section 6.3.2</a>
     */
    public static final String SC_201 = "201";

    /**
     * {@code 202 Accepted}.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7231#section-6.3.3">HTTP/1.1: Semantics and
     * Content, section 6.3.3</a>
     */
    public static final String SC_202 = "202";

    /**
     * {@code 203 Non-Authoritative Information}.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7231#section-6.3.4">HTTP/1.1: Semantics and
     * Content, section 6.3.4</a>
     */
    public static final String SC_203 = "203";

    /**
     * {@code 204 No Content}.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7231#section-6.3.5">HTTP/1.1: Semantics and
     * Content, section 6.3.5</a>
     */
    public static final String SC_204 = "204";

    /**
     * {@code 205 Reset Content}.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7231#section-6.3.6">HTTP/1.1: Semantics and
     * Content, section 6.3.6</a>
     */
    public static final String SC_205 = "205";

    /**
     * {@code 206 Partial Content}.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7233#section-4.1">HTTP/1.1: Range Requests,
     * section 4.1</a>
     */
    public static final String SC_206 = "206";

    /**
     * {@code 207 Multi-Status}.
     *
     * @see <a href="https://tools.ietf.org/html/rfc4918#section-13">WebDAV</a>
     */
    public static final String SC_207 = "207";

    /**
     * {@code 208 Already Reported}.
     *
     * @see <a href="https://tools.ietf.org/html/rfc5842#section-7.1">WebDAV Binding Extensions</a>
     */
    public static final String SC_208 = "208";

    /**
     * {@code 226 IM Used}.
     *
     * @see <a href="https://tools.ietf.org/html/rfc3229#section-10.4.1">Delta encoding in HTTP</a>
     */
    public static final String SC_226 = "226";

    // =======================
    // --- 3xx Redirection ---

    /**
     * {@code 300 Multiple Choices}.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7231#section-6.4.1">HTTP/1.1: Semantics and
     * Content, section 6.4.1</a>
     */
    public static final String SC_300 = "300";

    /**
     * {@code 301 Moved Permanently}.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7231#section-6.4.2">HTTP/1.1: Semantics and
     * Content, section 6.4.2</a>
     */
    public static final String SC_301 = "301";

    /**
     * {@code 302 Found}.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7231#section-6.4.3">HTTP/1.1: Semantics and
     * Content, section 6.4.3</a>
     */
    public static final String SC_302 = "302";

    /**
     * {@code 303 See Other}.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7231#section-6.4.4">HTTP/1.1: Semantics and
     * Content, section 6.4.4</a>
     */
    public static final String SC_303 = "303";

    /**
     * {@code 304 Not Modified}.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7232#section-4.1">HTTP/1.1: Conditional Requests,
     * section 4.1</a>
     */
    public static final String SC_304 = "304";

    /**
     * {@code 307 Temporary Redirect}.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7231#section-6.4.7">HTTP/1.1: Semantics and
     * Content, section 6.4.7</a>
     */
    public static final String SC_307 = "307";

    /**
     * {@code 308 Permanent Redirect}.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7238">RFC 7238</a>
     */
    public static final String SC_308 = "308";

    // ========================
    // --- 4xx Client Error ---

    /**
     * {@code 400 Bad Request}.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7231#section-6.5.1">HTTP/1.1: Semantics and
     * Content, section 6.5.1</a>
     */
    public static final String SC_400 = "400";

    /**
     * {@code 401 Unauthorized}.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7235#section-3.1">HTTP/1.1: Authentication,
     * section 3.1</a>
     */
    public static final String SC_401 = "401";

    /**
     * {@code 402 Payment Required}.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7231#section-6.5.2">HTTP/1.1: Semantics and
     * Content, section 6.5.2</a>
     */
    public static final String SC_402 = "402";

    /**
     * {@code 403 Forbidden}.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7231#section-6.5.3">HTTP/1.1: Semantics and
     * Content, section 6.5.3</a>
     */
    public static final String SC_403 = "403";

    /**
     * {@code 404 Not Found}.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7231#section-6.5.4">HTTP/1.1: Semantics and
     * Content, section 6.5.4</a>
     */
    public static final String SC_404 = "404";

    /**
     * {@code 405 Method Not Allowed}.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7231#section-6.5.5">HTTP/1.1: Semantics and
     * Content, section 6.5.5</a>
     */
    public static final String SC_405 = "405";

    /**
     * {@code 406 Not Acceptable}.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7231#section-6.5.6">HTTP/1.1: Semantics and
     * Content, section 6.5.6</a>
     */
    public static final String SC_406 = "406";

    /**
     * {@code 407 Proxy Authentication Required}.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7235#section-3.2">HTTP/1.1: Authentication,
     * section 3.2</a>
     */
    public static final String SC_407 = "407";

    /**
     * {@code 408 Request Timeout}.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7231#section-6.5.7">HTTP/1.1: Semantics and
     * Content, section 6.5.7</a>
     */
    public static final String SC_408 = "408";

    /**
     * {@code 409 Conflict}.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7231#section-6.5.8">HTTP/1.1: Semantics and
     * Content, section 6.5.8</a>
     */
    public static final String SC_409 = "409";

    /**
     * {@code 410 Gone}.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7231#section-6.5.9">HTTP/1.1: Semantics and
     * Content, section 6.5.9</a>
     */
    public static final String SC_410 = "410";

    /**
     * {@code 411 Length Required}.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7231#section-6.5.10">HTTP/1.1: Semantics and
     * Content, section 6.5.10</a>
     */
    public static final String SC_411 = "411";

    /**
     * {@code 412 Precondition failed}.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7232#section-4.2">HTTP/1.1: Conditional Requests,
     * section 4.2</a>
     */
    public static final String SC_412 = "412";

    /**
     * {@code 413 Payload Too Large}.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7231#section-6.5.11">HTTP/1.1: Semantics and
     * Content, section 6.5.11</a>
     * @since 4.1
     */
    public static final String SC_413 = "413";

    /**
     * {@code 414 URI Too Long}.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7231#section-6.5.12">HTTP/1.1: Semantics and
     * Content, section 6.5.12</a>
     * @since 4.1
     */
    public static final String SC_414 = "414";

    /**
     * {@code 415 Unsupported Media Type}.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7231#section-6.5.13">HTTP/1.1: Semantics and
     * Content, section 6.5.13</a>
     */
    public static final String SC_415 = "415";

    /**
     * {@code 416 Requested Range Not Satisfiable}.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7233#section-4.4">HTTP/1.1: Range Requests,
     * section 4.4</a>
     */
    public static final String SC_416 = "416";

    /**
     * {@code 417 Expectation Failed}.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7231#section-6.5.14">HTTP/1.1: Semantics and
     * Content, section 6.5.14</a>
     */
    public static final String SC_417 = "417";

    /**
     * {@code 418 I'm a teapot}.
     *
     * @see <a href="https://tools.ietf.org/html/rfc2324#section-2.3.2">HTCPCP/1.0</a>
     */
    public static final String SC_418 = "418";

    /**
     * {@code 422 Unprocessable Entity}.
     *
     * @see <a href="https://tools.ietf.org/html/rfc4918#section-11.2">WebDAV</a>
     */
    public static final String SC_422 = "422";

    /**
     * {@code 423 Locked}.
     *
     * @see <a href="https://tools.ietf.org/html/rfc4918#section-11.3">WebDAV</a>
     */
    public static final String SC_423 = "423";

    /**
     * {@code 424 Failed Dependency}.
     *
     * @see <a href="https://tools.ietf.org/html/rfc4918#section-11.4">WebDAV</a>
     */
    public static final String SC_424 = "424";

    /**
     * {@code 425 Too Early}.
     *
     * @see <a href="https://tools.ietf.org/html/rfc8470">RFC 8470</a>
     * @since 5.2
     */
    public static final String SC_425 = "425";

    /**
     * {@code 426 Upgrade Required}.
     *
     * @see <a href="https://tools.ietf.org/html/rfc2817#section-6">Upgrading to TLS Within
     * HTTP/1.1</a>
     */
    public static final String SC_426 = "426";

    /**
     * {@code 428 Precondition Required}.
     *
     * @see <a href="https://tools.ietf.org/html/rfc6585#section-3">Additional HTTP Status Codes</a>
     */
    public static final String SC_428 = "428";

    /**
     * {@code 429 Too Many Requests}.
     *
     * @see <a href="https://tools.ietf.org/html/rfc6585#section-4">Additional HTTP Status Codes</a>
     */
    public static final String SC_429 = "429";

    /**
     * {@code 431 Request Header Fields Too Large}.
     *
     * @see <a href="https://tools.ietf.org/html/rfc6585#section-5">Additional HTTP Status Codes</a>
     */
    public static final String SC_431 = "431";

    /**
     * {@code 451 Unavailable For Legal Reasons}.
     *
     * @see <a href="https://tools.ietf.org/html/draft-ietf-httpbis-legally-restricted-status-04">An
     * HTTP Status Code to Report Legal Obstacles</a>
     * @since 4.3
     */
    public static final String SC_451 = "451";

    // ========================
    // --- 5xx Server Error ---

    /**
     * {@code 500 Internal Server Error}.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7231#section-6.6.1">HTTP/1.1: Semantics and
     * Content, section 6.6.1</a>
     */
    public static final String SC_500 = "500";

    /**
     * {@code 501 Not Implemented}.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7231#section-6.6.2">HTTP/1.1: Semantics and
     * Content, section 6.6.2</a>
     */
    public static final String SC_501 = "501";

    /**
     * {@code 502 Bad Gateway}.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7231#section-6.6.3">HTTP/1.1: Semantics and
     * Content, section 6.6.3</a>
     */
    public static final String SC_502 = "502";

    /**
     * {@code 503 Service Unavailable}.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7231#section-6.6.4">HTTP/1.1: Semantics and
     * Content, section 6.6.4</a>
     */
    public static final String SC_503 = "503";

    /**
     * {@code 504 Gateway Timeout}.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7231#section-6.6.5">HTTP/1.1: Semantics and
     * Content, section 6.6.5</a>
     */
    public static final String SC_504 = "504";

    /**
     * {@code 505 HTTP Version Not Supported}.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7231#section-6.6.6">HTTP/1.1: Semantics and
     * Content, section 6.6.6</a>
     */
    public static final String SC_505 = "505";

    /**
     * {@code 506 Variant Also Negotiates}
     *
     * @see <a href="https://tools.ietf.org/html/rfc2295#section-8.1">Transparent Content
     * Negotiation</a>
     */
    public static final String SC_506 = "506";

    /**
     * {@code 507 Insufficient Storage}
     *
     * @see <a href="https://tools.ietf.org/html/rfc4918#section-11.5">WebDAV</a>
     */
    public static final String SC_507 = "507";

    /**
     * {@code 508 Loop Detected}
     *
     * @see <a href="https://tools.ietf.org/html/rfc5842#section-7.2">WebDAV Binding Extensions</a>
     */
    public static final String SC_508 = "508";

    /**
     * {@code 509 Bandwidth Limit Exceeded}
     */
    public static final String SC_509 = "509";

    /**
     * {@code 510 Not Extended}
     *
     * @see <a href="https://tools.ietf.org/html/rfc2774#section-7">HTTP Extension Framework</a>
     */
    public static final String SC_510 = "510";

    /**
     * {@code 511 Network Authentication Required}.
     *
     * @see <a href="https://tools.ietf.org/html/rfc6585#section-6">Additional HTTP Status Codes</a>
     */
    public static final String SC_511 = "511";
}
