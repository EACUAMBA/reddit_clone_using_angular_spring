/*
 * Copyright (c) 2022
 * EXI Limitada
 * All rights reserved.
 *
 * Created by Edilson Alexandre Cuamba (eacuamba_exi_pc) on 03/06/2022
 */

package com.eacuamba.dev.reddit_clone_using_angular_spring.configuration.security.exception;

import org.springframework.security.authentication.AccountStatusException;

public class AccountNonVerifiedException extends AccountStatusException {
    public AccountNonVerifiedException(String msg) {
        super(msg);
    }
    public AccountNonVerifiedException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
