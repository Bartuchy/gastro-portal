package com.gastro.portal.mailing;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Email {
    private String from;
    private String to;
    private String subject;
    private String content;
}
