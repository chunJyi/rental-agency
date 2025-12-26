package com.tc.agency.rental.config;

public enum CustomHttpStatus {

    // ✅ 2xx Success
    OK(200, "OK"),
    CREATED(201, "User Created Successfully"),
    ACCEPTED(202, "Request Accepted"),
    NO_CONTENT(204, "No Content"),

    // ⚠️ 4xx Client Errors (Authentication / Authorization)
    BAD_REQUEST(400, "Bad Request — Invalid request format or missing data"),
    UNAUTHORIZED(401, "Unauthorized — Invalid credentials or missing token"),
    FORBIDDEN(403, "Forbidden — Access denied or insufficient permissions"),
    NOT_FOUND(404, "Not Found — User or resource not found"),
    CONFLICT(409, "Conflict — User already exists"),
    UNPROCESSABLE_ENTITY(422, "Unprocessable Entity — Validation failed"),
    TOO_MANY_REQUESTS(429, "Too Many Requests — Too many login attempts"),

    // 🚫 5xx Server Errors
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
    SERVICE_UNAVAILABLE(503, "Authentication Service Unavailable"),

    // 🔐 Custom application-defined statuses (non-standard but useful)
    TOKEN_EXPIRED(498, "Token Expired — Please login again"),
        TOKEN_INVALID(499, "Invalid Token — Signature or format invalid"),
    TOKEN_MISSING(440, "Missing Token — Authorization header required"),
    ACCOUNT_LOCKED(423, "Account Locked — Too many failed attempts"),
    SESSION_EXPIRED(419, "Session Expired — Please re-authenticate");

    private final int code;
    private final String message;

    CustomHttpStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static CustomHttpStatus fromCode(int code) {
        for (CustomHttpStatus status : values()) {
            if (status.code == code) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown AuthHttpStatus code: " + code);
    }

    @Override
    public String toString() {
        return code + " - " + message;
    }

}
