package com.`fun`.myservice.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.data.cassandra.config.AbstractReactiveCassandraConfiguration
import org.springframework.data.cassandra.config.SchemaAction
import org.springframework.data.cassandra.repository.config.EnableReactiveCassandraRepositories

@Configuration
@EnableReactiveCassandraRepositories
class CassandraConfig(@Value("\${spring.data.cassandra.keyspaceName}") val keyspace: String): AbstractReactiveCassandraConfiguration() {

    override fun getKeyspaceName(): String {
        return keyspace;
    }

    override fun getStartupScripts(): MutableList<String> {
        val keySpaceScript = ("CREATE KEYSPACE IF NOT EXISTS "
                + keyspace
                + " WITH durable_writes = true"
                + " AND replication = {'class' : 'SimpleStrategy', 'replication_factor' : 1};")

        return mutableListOf(keySpaceScript)
    }

    override fun getShutdownScripts(): MutableList<String> {
        return mutableListOf("DROP KEYSPACE IF EXISTS $keyspace;")
    }

    override fun getSchemaAction(): SchemaAction {
        return SchemaAction.CREATE_IF_NOT_EXISTS;
    }

    override fun getEntityBasePackages(): Array<String> {
        return listOf("com.fun.myservice.dal.dto").toTypedArray()
    }


}
