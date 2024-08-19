package es.princip.getp.mail.command;

import es.princip.getp.domain.member.model.Email;

public record SendMailCommand(
    Email email,
    String title,
    String text
) {

    public static SendMailCommand of(
        final Email email, 
        final String title,
        final String text
    ) {
        return new SendMailCommand(email, title, text);
    }
}