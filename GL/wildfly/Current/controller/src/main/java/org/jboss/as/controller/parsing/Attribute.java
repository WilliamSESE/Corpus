/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2011, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.jboss.as.controller.parsing;

import java.util.HashMap;
import java.util.Map;

/**
 * An enumeration of all the recognized core configuration XML attributes, by local name.
 *
 * @author <a href="mailto:david.lloyd@redhat.com">David M. Lloyd</a>
 */
public enum Attribute {
    // always first
    UNKNOWN(null),

    // xsi attributes in alpha order
    NO_NAMESPACE_SCHEMA_LOCATION("noNamespaceSchemaLocation"),
    SCHEMA_LOCATION("schemaLocation"),

    // domain attributes in alpha order
    ADMIN_ONLY_POLICY("admin-only-policy"),
    ALIAS("alias"),
    ALLOW_EMPTY_PASSWORDS("allow-empty-passwords"),
    ALLOWED_USERS("allowed-users"),
    APP_NAME("app-name"),
    APPLICATION("application"),
    ATTRIBUTE("attribute"),
    AUTO_START("auto-start"),
    BASE_DN("base-dn"),
    BASE_ROLE("base-role"),
    BOOT_TIME("boot-time"),
    CACHE_FAILURES("cache-failures"),
    CODE("code"),
    COMPACT("compact"),
    CONNECTION("connection"),
    CONNECTOR("connector"),
    CONSOLE_ENABLED("console-enabled"),
    CONTENT("content"),
    DATE_FORMAT("date-format"),
    DATE_SEPARATOR("date-separator"),
    DEFAULT_INTERFACE("default-interface"),
    DEFAULT_USER("default-user"),
    DEBUG_ENABLED("debug-enabled"),
    DEBUG_OPTIONS("debug-options"),
    DEPLOYMENT("deployment"),
    DEPLOYMENT_OVERLAY("deployment-overlay"),
    DESTINATION_ADDRESS("destination-address"),
    DIRECTORY_GROUPING("directory-grouping"),
    DESTINATION_PORT("destination-port"),
    ENABLED("enabled"),
    ENV_CLASSPATH_IGNORED("env-classpath-ignored"),
    ESCAPE_CONTROL_CHARACTERS("escape-control-characters"),
    ESCAPE_NEW_LINE("escape-new-line"),
    EVICTION_TIME("eviction-time"),
    FACILITY("facility"),
    FILE("file"),
    FILTER("filter"),
    FIXED_PORT("fixed-port"),
    FIXED_SOURCE_PORT("fixed-source-port"),
    FORCE("force"),
    FORMATTER("formatter"),
    GROUP("group"),
    GROUP_ATTRIBUTE("group-attribute"),
    GROUP_DN_ATTRIBUTE("group-dn-attribute"),
    GROUP_NAME_ATTRIBUTE("group-name-attribute"),
    GROUP_NAME("group-name"),
    HOST("host"),
    HTTP("http"),
    HTTP_UPGRADE_ENABLED("http-upgrade-enabled"),
    HTTPS("https"),
    IGNORE_UNUSED_CONFIG("ignore-unused-configuration"),
    INCLUDE_ALL("include-all"),
    INCLUDE_DATE("include-date"),
    INITIAL_CONTEXT_FACTORY("initial-context-factory"),
    INTERFACE("interface"),
    ITERATIVE("iterative"),
    JAVA_HOME("java-home"),
    KEY_PASSWORD("key-password"),
    KEYSTORE_PASSWORD("keystore-password"),
    LOG_BOOT("log-boot"),
    LOG_READ_ONLY("log-read-only"),
    MANAGEMENT_SUBSYSTEM_ENDPOINT("management-subsystem-endpoint"),
    MAP_GROUPS_TO_ROLES("map-groups-to-roles"),
    MAX_CACHE_SIZE("max-cache-size"),
    MAX_FAILURE_COUNT("max-failure-count"),
    MAX_LENGTH("max-length"),
    MAX_SIZE("max-size"),
    MAX_THREADS("max-threads"),
    MECHANISM("mechanism"),
    MESSAGE_TRANSFER("message-transfer"),
    MODULE("module"),
    MULTICAST_ADDRESS("multicast-address"),
    MULTICAST_PORT("multicast-port"),
    NAME("name"),
    NATIVE("native"),
    PASSWORD("password"),
    PATH("path"),
    PATTERN("pattern"),
    PERMISSION_COMBINATION_POLICY("permission-combination-policy"),
    PLAIN_TEXT("plain-text"),
    PORT("port"),
    PORT_OFFSET("port-offset"),
    PREFIX("prefix"),
    PRINCIPAL_ATTRIBUTE("principal-attribute"),
    PROFILE("profile"),
    PROTOCOL("protocol"),
    PROVIDER("provider"),
    REALM("realm"),
    RECURSIVE("recursive"),
    REF("ref"),
    REGULAR_EXPRESSION("regular-expression"),
    RESULT_PATTERN("result-pattern"),
    RELATIVE_TO("relative-to"),
    REQUIRES_ADDRESSABLE("requires-addressable"),
    REQUIRES_READ("requires-read"),
    REQUIRES_WRITE("requires-write"),
    REVERSE_GROUP("reverse-group"),
    RUNTIME_NAME("runtime-name"),
    SEARCH_CREDENTIAL("search-credential"),
    SEARCH_BY("search-by"),
    SEARCH_DN("search-dn"),
    SECURE_PORT("secure-port"),
    SECURITY_REALM("security-realm"),
    SHA1("sha1"),
    SIZE("size"),
    SKIP_GROUP_LOADING("skip-group-loading"),
    SOCKET_BINDING_GROUP("socket-binding-group"),
    SOCKET_BINDING_REF("socket-binding-ref"),
    SOURCE_INTERFACE("source-interface"),
    SOURCE_NETWORK("source-network"),
    SOURCE_PORT("source-port"),
    SYSLOG_FORMAT ("syslog-format"),
    TRUNCATE("truncate"),
    TYPE("type"),
    URL("url"),
    USER("user"),
    USER_DN("user-dn"),
    USER_DN_ATTRIBUTE("user-dn-attribute"),
    USERNAME("username"),
    USERNAME_ATTRIBUTE("username-attribute"),
    USERNAME_LOAD("username-load"),
    VALUE("value"),
    WILDCARD("wildcard")
    ;

    private final String name;

    Attribute(final String name) {
        this.name = name;
    }

    /**
     * Get the local name of this element.
     *
     * @return the local name
     */
    public String getLocalName() {
        return name;
    }

    private static final Map<String, Attribute> MAP;

    static {
        final Map<String, Attribute> map = new HashMap<String, Attribute>();
        for (Attribute element : values()) {
            final String name = element.getLocalName();
            if (name != null) map.put(name, element);
        }
        MAP = map;
    }

    public static Attribute forName(String localName) {
        final Attribute element = MAP.get(localName);
        return element == null ? UNKNOWN : element;
    }
}
