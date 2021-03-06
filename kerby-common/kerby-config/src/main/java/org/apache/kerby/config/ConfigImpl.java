/**
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *

 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License. 
 *
 */
package org.apache.kerby.config;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ConfigImpl implements Config {

    private String resource;
    private Map<String, ConfigObject> properties;
    /**
     * Config resources
     */
    private List<Config> configs;

    protected ConfigImpl(String resource) {
        this.resource = resource;
        this.properties = new HashMap<>();
        this.configs = new ArrayList<>(0);
    }

    protected void reset() {
        this.properties.clear();
        this.configs.clear();
    }

    @Override
    public String getResource() {
        return resource;
    }

    @Override
    public Set<String> getNames() {
        Set<String>propNames = new HashSet<>(properties.keySet());
        for (Config config : configs) {
            propNames.addAll(config.getNames());
        }
        return propNames;
    }

    @Override
    public String getString(String name) {
        String result = null;

        ConfigObject co = properties.get(name);
        if (co != null) {
            result = co.getPropertyValue();
        } else {
            for (Config config : configs) {
                result = config.getString(name);
                if (result != null) {
                    break;
                }
            }
        }

        return result;
    }

    @Override
    public String getString(ConfigKey name, boolean useDefault) {
        if (useDefault) {
            return getString(name.getPropertyKey(),
                    (String) name.getDefaultValue());
        }
        return getString(name.getPropertyKey());
    }

    @Override
    public String getString(String name, String defaultValue) {
        String result = getString(name);
        if (result == null) {
            result = defaultValue;
        }
        return result;
    }

    @Override
    public String getTrimmed(String name) {
        String result = getString(name);
        if (null != result) {
            result = result.trim();
        }
        return result;
    }

    @Override
    public String getTrimmed(ConfigKey name) {
        return getTrimmed(name.getPropertyKey());
    }

    @Override
    public Integer getInt(String name) {
        Integer result = null;
        String value = getTrimmed(name);
        if (value != null) {
            result = Integer.valueOf(value);
        }
        return result;
    }

    @Override
    public Integer getInt(ConfigKey name, boolean useDefault) {
        if (useDefault) {
            return getInt(name.getPropertyKey(),
                    getDefaultValueAs(name, Integer.class));
        }
        return getInt(name.getPropertyKey());
    }

    private <T> T getDefaultValueAs(ConfigKey confKey, Class<T> cls) {
        Object defValue = confKey.getDefaultValue();
        if (defValue != null && cls != null) {
            return (T) defValue;
        }
        return null;
    }

    @Override
    public Integer getInt(String name, Integer defaultValue) {
        Integer result = getInt(name);
        if (result == null) {
            result = defaultValue;
        }
        return result;
    }

    @Override
    public void setInt(String name, Integer value) {
        set(name, String.valueOf(value));
    }

    @Override
    public void setInt(ConfigKey name, Integer value) {
        set(name.getPropertyKey(), String.valueOf(value));
    }

    @Override
    public Long getLong(String name) {
        Long result = null;
        String value = getTrimmed(name);
        if (value != null) {
            result = Long.valueOf(value);
        }
        return result;
    }

    @Override
    public Long getLong(ConfigKey name, boolean useDefault) {
        if (useDefault) {
            return getLong(name.getPropertyKey(),
                getDefaultValueAs(name, Long.class));
        }
        return getLong(name.getPropertyKey());
    }

    @Override
    public Long getLong(String name, Long defaultValue) {
        Long result = getLong(name);
        if (result == null) {
            result = defaultValue;
        }
        return result;
    }

    @Override
    public void setLong(String name, Long value) {
        set(name, String.valueOf(value));
    }

    @Override
    public void setLong(ConfigKey name, Long value) {
        set(name.getPropertyKey(), String.valueOf(value));
    }

    @Override
    public Float getFloat(String name) {
        Float result = null;
        String value = getTrimmed(name);
        if (value != null) {
            result = Float.valueOf(value);
        }
        return result;
    }

    @Override
    public Float getFloat(ConfigKey name, boolean useDefault) {
        if (useDefault) {
            return getFloat(name.getPropertyKey(),
                    getDefaultValueAs(name, Float.class));
        }
        return getFloat(name.getPropertyKey());
    }

    @Override
    public Float getFloat(String name, Float defaultValue) {
        Float result = getFloat(name);
        if (result == null) {
            result = defaultValue;
        }
        return result;
    }

    @Override
    public void setFloat(String name, Float value) {
        set(name, String.valueOf(value));
    }

    @Override
    public void setFloat(ConfigKey name, Float value) {
        set(name.getPropertyKey(), String.valueOf(value));
    }

    @Override
    public Boolean getBoolean(String name) {
        Boolean result = null;
        String value = getTrimmed(name);
        if (value != null) {
            result = Boolean.valueOf(value);
        }
        return result;
    }

    @Override
    public Boolean getBoolean(ConfigKey name, boolean useDefault) {
        if (useDefault) {
            return getBoolean(name.getPropertyKey(),
                    (Boolean) name.getDefaultValue());
        }
        return getBoolean(name.getPropertyKey());
    }

    @Override
    public Boolean getBoolean(String name, Boolean defaultValue) {
        Boolean result = getBoolean(name);
        if (result == null) {
            result = defaultValue;
        }
        return result;
    }

    @Override
    public void setBoolean(String name, Boolean value) {
        set(name, String.valueOf(value));
    }

    @Override
    public void setBoolean(ConfigKey name, Boolean value) {
        set(name.getPropertyKey(), String.valueOf(value));
    }

    @Override
    public List<String> getList(String name) {
        List<String> results = null;
        ConfigObject co = properties.get(name);
        if (co != null) {
            results = co.getListValues();
        } else {
            for (Config config : configs) {
                results = config.getList(name);
                if (results != null) {
                    break;
                }
            }
        }
        return results;
    }

    @Override
    public List<String> getList(String name, String[] defaultValue) {
        List<String> results = getList(name);
        if (results == null) {
            results = Arrays.asList(defaultValue);
        }
        return results;
    }

    @Override
    public List<String> getList(ConfigKey name) {
        if (name.getDefaultValue() != null) {
            return getList(name.getPropertyKey(), (String[]) name.getDefaultValue());
        }
        return getList(name.getPropertyKey());
    }

    @Override
    public Config getConfig(String name) {
        Config result = null;
        ConfigObject co = properties.get(name);
        if (co != null) {
            result = co.getConfigValue();
        } else {
            for (Config config : configs) {
                result = config.getConfig(name);
                if (result != null) {
                    break;
                }
            }
        }

        return result;
    }

    @Override
    public Config getConfig(ConfigKey name) {
        return getConfig(name.getPropertyKey());
    }

    @Override
    public Class<?> getClass(String name) throws ClassNotFoundException {
        Class<?> result = null;

        String valueString = getString(name);
        if (valueString != null) {
            Class<?> cls = Class.forName(name);
            result = cls;
        }

        return result;
    }

    @Override
    public Class<?> getClass(String name, Class<?> defaultValue)
            throws ClassNotFoundException {
        Class<?> result = getClass(name);
        if (result == null) {
            result = defaultValue;
        }
        return result;
    }

    @Override
    public Class<?> getClass(ConfigKey name, boolean useDefault)
            throws ClassNotFoundException {
        if (useDefault) {
            return getClass(name.getPropertyKey(),
                    (Class<?>) name.getDefaultValue());
        }
        return getClass(name.getPropertyKey());
    }

    @Override
    public <T> T getInstance(String name) throws ClassNotFoundException {
        return getInstance(name, null);
    }

    @Override
    public <T> T getInstance(ConfigKey name) throws ClassNotFoundException {
        return getInstance(name.getPropertyKey());
    }

    @Override
    public <T> T getInstance(String name, Class<T> xface) throws ClassNotFoundException {
        T result = null;

        Class<?> cls = getClass(name, null);
        if (xface != null && !xface.isAssignableFrom(cls)) {
            throw new RuntimeException(cls + " does not implement " + xface);
        }
        try {
            result = (T) cls.newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Failed to create instance with class " + cls.getName());
        }

        return result;
    }


    @Override
    public void setString(String name, String value) {
        set(name, value);
    }

    @Override
    public void setString(ConfigKey name, String value) {
        set(name.getPropertyKey(), value);
    }

    protected void set(String name, String value) {
        ConfigObject co = new ConfigObject(value);
        set(name, co);
    }

    protected void set(String name, Config value) {
        ConfigObject co = new ConfigObject(value);
        set(name, co);
    }

    protected void set(String name, ConfigObject value) {
        this.properties.put(name, value);
    }

    protected void add(Config config) {
        if (config != null) {
            if (this == config) {
                throw new IllegalArgumentException(
                        "You can not add a config to itself");
            }
            this.configs.add(config);
        }
    }
}
