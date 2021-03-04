package com.myservice.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.data.cassandra.config.AbstractReactiveCassandraConfiguration
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean
import org.springframework.data.cassandra.config.SchemaAction
import org.springframework.data.cassandra.repository.config.EnableReactiveCassandraRepositories

@Configuration
@EnableReactiveCassandraRepositories
class CassandraConfig(
        @Value("\${spring.data.cassandra.keyspaceName}") private val keyspace: String,
        @Value("\${spring.data.cassandra.username}") private val username: String,
        @Value("\${spring.data.cassandra.password}") private val password: String,
        @Value("\${spring.data.cassandra.contactPoint}") private val contactPoint: String
) : AbstractReactiveCassandraConfiguration() {

    override fun getKeyspaceName(): String {
        return keyspace
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
        return listOf("com.myservice.dal.dto").toTypedArray()
    }

    override fun cluster(): CassandraClusterFactoryBean {
        val clusterFactoryBean = super.cluster()
        clusterFactoryBean.setUsername(username)
        clusterFactoryBean.setPassword(password)
        clusterFactoryBean.setContactPoints(contactPoint)
        return clusterFactoryBean
    }
}
