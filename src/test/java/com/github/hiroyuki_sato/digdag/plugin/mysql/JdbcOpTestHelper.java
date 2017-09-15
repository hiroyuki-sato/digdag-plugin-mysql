package com.github.hiroyuki_sato.digdag.plugin.mysql;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Scopes;
import io.digdag.client.config.Config;
import io.digdag.client.config.ConfigFactory;
import io.digdag.core.agent.ConfigEvalEngine;
import io.digdag.spi.TaskRequest;
import io.digdag.spi.TemplateEngine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class JdbcOpTestHelper
{
    private final ObjectMapper mapper = new ObjectMapper().registerModule(new GuavaModule());
    private final ConfigFactory configFactory = new ConfigFactory(mapper);
    private final Injector injector = Guice.createInjector(new MyModule());

    static class MyModule implements Module
    {
        @Override
        public void configure(Binder binder)
        {
            binder.bind(TemplateEngine.class).to(ConfigEvalEngine.class).in(Scopes.SINGLETON);
        }
    }

    public Injector injector()
    {
        return injector;
    }

    public Path projectPath()
            throws IOException
    {
        return Files.createTempDirectory("jdbc_op_test");
    }

    public Config createConfig(Map<String, ?> configInput)
            throws IOException
    {
        return configFactory.create(configInput);
    }

    public TaskRequest createTaskRequest(Map<String, Object> configInput, Optional<Map<String, Object>> lastState)
            throws IOException
    {
        return OperatorTestingUtils.newTaskRequest()
                .withConfig(createConfig(configInput))
                .withLocalConfig(createConfig(ImmutableMap.of()))
                .withLastStateParams(createConfig(lastState.or(ImmutableMap.of())));
    }

    public ConfigFactory getConfigFactory()
    {
        return configFactory;
    }
}
