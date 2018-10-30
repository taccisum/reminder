package com.github.taccisum.reminder.channel.descriptor;

import com.github.taccisum.reminder.api.Channel;
import com.github.taccisum.reminder.api.ChannelDescriptor;
import com.github.taccisum.reminder.channel.ChannelFactory;
import com.github.taccisum.reminder.channel.ChannelFallbackProxy;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tac
 * @since 24/10/2018
 */
public class StringChannelDescriptor implements ChannelDescriptor {
    public static final String FIRST_SEPARATOR = ",";
    public static final String SECOND_SEPARATOR = "@";
    private String val;

    public StringChannelDescriptor(String val) {
        this.val = val;
    }

    @Override
    public List<Channel> toChannels() {
        List<Channel> channels = new ArrayList<>();
        String[] arr = val.split(FIRST_SEPARATOR);
        for (String s : arr) {
            String[] codes = s.split(SECOND_SEPARATOR);

            if (codes.length == 1) {
                channels.add(findChannel(codes[0].trim()));
            } else if (codes.length > 1) {
                List<Channel> ls = new ArrayList<>();
                for (String code : codes) {
                    ls.add(findChannel(code));
                }

                Channel channel = null;
                for (int i = ls.size() - 1; i >= 0; i--) {
                    if (i == ls.size() - 1) {
                        channel = ls.get(i);
                    } else {
                        channel = new ChannelFallbackProxy(ls.get(i), channel);
                    }
                }

                channels.add(channel);
            }
        }
        return channels;
    }

    private Channel findChannel(String code) {
        return ChannelFactory.create(code);
    }

    @Override
    public String toString() {
        return val;
    }
}
