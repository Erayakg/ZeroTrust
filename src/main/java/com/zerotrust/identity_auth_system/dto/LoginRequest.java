package com.zerotrust.identity_auth_system.dto;

import java.io.Serializable;

public record LoginRequest (String username, String password) implements Serializable {

}
