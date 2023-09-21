package br.com.pitang.user.car.api.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RegexConstants {

    public static final String EMAIL_PATTERN = "([._\\-a-z0-9]+)@([a-z]+)\\.(\\w+)";
}