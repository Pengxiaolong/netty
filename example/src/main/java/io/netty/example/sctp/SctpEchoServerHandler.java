/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package io.netty.example.sctp;

import io.netty.buffer.MessageBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundMessageHandlerAdapter;
import io.netty.channel.socket.SctpData;
import io.netty.channel.socket.SctpMessage;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handler implementation for the SCTP echo server.
 */
@Sharable
public class SctpEchoServerHandler extends ChannelInboundMessageHandlerAdapter<SctpMessage> {

    private static final Logger logger = Logger.getLogger(
            SctpEchoServerHandler.class.getName());

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        logger.log(Level.WARNING, "Unexpected exception from downstream.", cause);
        ctx.close();
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, SctpMessage msg) throws Exception {
        if (msg instanceof SctpData) {
            MessageBuf<Object> out = ctx.nextOutboundMessageBuffer();
            out.add(msg);
            ctx.flush();
        } else {
            logger.log(Level.INFO, "Received SCTP Notification", msg);
        }
    }
}
