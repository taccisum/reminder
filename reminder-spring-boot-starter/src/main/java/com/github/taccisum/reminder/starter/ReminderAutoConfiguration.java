package com.github.taccisum.reminder.starter;

import com.github.taccisum.reminder.Reminder;
import com.github.taccisum.reminder.api.Dispatcher;
import com.github.taccisum.reminder.api.Metadata;
import com.github.taccisum.reminder.api.Sender;
import com.github.taccisum.reminder.dispatcher.DefaultDispatcher;
import com.github.taccisum.reminder.metadata.IniMetadata;
import com.github.taccisum.reminder.sender.DefaultSender;
import com.github.taccisum.reminder.spring.MessageBuilderPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * @author tac
 * @since 25/10/2018
 */
public class ReminderAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public MessageBuilderPostProcessor messageBuilderPostProcessor() {
        return new MessageBuilderPostProcessor();
    }

    @Bean
    @ConditionalOnMissingBean
    public Reminder reminder(Dispatcher dispatcher) {
        Reminder reminder = new Reminder();
        reminder.setDispatcher(dispatcher);
        return reminder;
    }

    @Bean
    @ConditionalOnMissingBean
    public Dispatcher dispatcher(Sender sender) {
        DefaultDispatcher dispatcher = new DefaultDispatcher();
        dispatcher.setSender(sender);
        return dispatcher;
    }

    @Bean
    @ConditionalOnMissingBean
    public Metadata metadata() {
        return new IniMetadata("classpath:templates.ini");
    }

    @Bean
    @ConditionalOnMissingBean
    public Sender sender() {
        return new DefaultSender();
    }
}
