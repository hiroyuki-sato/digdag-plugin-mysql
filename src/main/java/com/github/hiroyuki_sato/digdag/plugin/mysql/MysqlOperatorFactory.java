package com.github.hiroyuki_sato.digdag.plugin.mysql;

import io.digdag.client.config.Config;
import io.digdag.spi.Operator;
import io.digdag.spi.OperatorContext;
import io.digdag.spi.OperatorFactory;
import io.digdag.spi.SecretProvider;
import io.digdag.spi.TemplateEngine;
import io.digdag.standards.operator.jdbc.AbstractJdbcJobOperator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MysqlOperatorFactory
        implements OperatorFactory
{
    private static final String OPERATOR_TYPE = "mysql";

    private final TemplateEngine templateEngine;
    private final Config systemConfig;

    private static Logger logger = LoggerFactory.getLogger(MysqlOperatorFactory.class);

    public MysqlOperatorFactory(Config systemConfig,TemplateEngine templateEngine)
    {
        this.templateEngine = templateEngine;
        this.systemConfig = systemConfig;
    }

    @Override
    public String getType()
    {
        return OPERATOR_TYPE;
    }

    @Override
    public Operator newOperator(OperatorContext context)
    {
        return new MysqlOperator(systemConfig,context,templateEngine);
    }

    static class MysqlOperator
            extends AbstractJdbcJobOperator<MysqlConnectionConfig>
    {
        MysqlOperator(Config systemConfig,OperatorContext context, TemplateEngine templateEngine)
        {
            super(systemConfig,context, templateEngine);
        }

        @Override
        protected MysqlConnectionConfig configure(SecretProvider secrets, Config params)
        {
            return MysqlConnectionConfig.configure(secrets, params);
        }

        @Override
        protected MysqlConnection connect(MysqlConnectionConfig connectionConfig)
        {
            return MysqlConnection.open(connectionConfig);
        }

        @Override
        protected String type()
        {
            return OPERATOR_TYPE;
        }

        @Override
        protected SecretProvider getSecretsForConnectionConfig()
        {
            return context.getSecrets().getSecrets(type());
        }
    }
}
