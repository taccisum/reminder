package com.github.taccisum.reminder.spring;

import com.github.taccisum.reminder.api.Channel;
import com.github.taccisum.reminder.api.MessageBuilder;
import com.github.taccisum.reminder.api.TargetSelector;
import com.github.taccisum.reminder.builder.MessageBuilderFactory;
import com.github.taccisum.reminder.channel.ChannelFactory;
import com.github.taccisum.reminder.selector.TargetSelectorFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * @author tac
 * @since 25/10/2018
 */
public class ReminderPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean != null) {
            if (bean instanceof MessageBuilder) {
                MessageBuilderFactory.put((MessageBuilder) bean);
            } else if (bean instanceof TargetSelector) {
                TargetSelectorFactory.put((TargetSelector) bean);
            } else if (bean instanceof Channel) {
                ChannelFactory.put((Channel) bean);
            }
        }
        return bean;
    }
}
