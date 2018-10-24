package com.github.taccisum.reminder.channel.descriptor;

import com.github.taccisum.reminder.api.Channel;
import com.github.taccisum.reminder.api.ChannelDescriptor;
import com.github.taccisum.reminder.channel.ChannelFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tac
 * @since 24/10/2018
 */
public class StringChannelDescriptor implements ChannelDescriptor {
    private String val;

    public StringChannelDescriptor(String val) {
        this.val = val;
    }

    @Override
    public List<Channel> toChannels() {
        List<Channel> channels = new ArrayList<>();
        String[] arr = val.split(",");
        for (String s : arr) {
            String[] codes = s.split("_");

            if (codes.length == 1) {
                channels.add(ChannelFactory.create(codes[0].trim()));
            } else if (codes.length > 1) {
                for (int i = 0; i < codes.length; i++) {
                }
            }
        }
        return channels;
    }
}
