package org.lu.hypervisor.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Not Authorized")
public class NotAuthorizedException extends Exception {
}
