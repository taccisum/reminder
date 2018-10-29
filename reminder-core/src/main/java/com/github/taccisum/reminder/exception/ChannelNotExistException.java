package com.github.taccisum.reminder.exception;

/**
 * @author tac - liaojf@cheegu.com
 * @since 2018/10/29
 */
public class ChannelNotExistException extends RemindingException {
    public ChannelNotExistException(String code) {
        super(String.format("channel \"%s\" does not exist.", code));
    }
}
