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
package io.netty.handler.codec.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.internal.StringUtil;

import java.util.Map;

/**
 * The default {@link LastHttpContent} implementation.
 */
public class DefaultLastHttpContent extends DefaultHttpContent implements LastHttpContent {

    private final HttpHeaders trailingHeaders;
    private final boolean validateHeaders;

    public DefaultLastHttpContent() {
        this(Unpooled.buffer(0));
    }

    public DefaultLastHttpContent(ByteBuf content) {
        this(content, true);
    }

    public DefaultLastHttpContent(ByteBuf content, boolean validateHeaders) {
        super(content);
        trailingHeaders = new TrailingHeaders(validateHeaders);
        this.validateHeaders = validateHeaders;
    }

    @Override
    public LastHttpContent copy() {
        DefaultLastHttpContent copy = new DefaultLastHttpContent(content().copy(), validateHeaders);
        copy.trailingHeaders().set(trailingHeaders());
        return copy;
    }

    @Override
    public LastHttpContent duplicate() {
        DefaultLastHttpContent copy = new DefaultLastHttpContent(content().duplicate(), validateHeaders);
        copy.trailingHeaders().set(trailingHeaders());
        return copy;
    }

    @Override
    public LastHttpContent retain(int increment) {
        super.retain(increment);
        return this;
    }

    @Override
    public LastHttpContent retain() {
        super.retain();
        return this;
    }

    @Override
    public LastHttpContent touch() {
        super.touch();
        return this;
    }

    @Override
    public HttpHeaders trailingHeaders() {
        return trailingHeaders;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder(super.toString());
        buf.append(StringUtil.NEWLINE);
        appendHeaders(buf);

        // Remove the last newline.
        buf.setLength(buf.length() - StringUtil.NEWLINE.length());
        return buf.toString();
    }

    private void appendHeaders(StringBuilder buf) {
        for (Map.Entry<String, String> e: trailingHeaders()) {
            buf.append(e.getKey());
            buf.append(": ");
            buf.append(e.getValue());
            buf.append(StringUtil.NEWLINE);
        }
    }

    private static final class TrailingHeaders extends DefaultHttpHeaders {
        TrailingHeaders(boolean validate) {
            super(validate);
        }

        @Override
        void validateHeaderName0(CharSequence name) {
            super.validateHeaderName0(name);
            if (equalsIgnoreCase(name, HttpHeaders.Names.CONTENT_LENGTH) ||
                    equalsIgnoreCase(name, HttpHeaders.Names.TRANSFER_ENCODING) ||
                    equalsIgnoreCase(name, HttpHeaders.Names.TRAILER)) {
                throw new IllegalArgumentException(
                        "prohibited trailing header: " + name);
            }
        }
    }
}
