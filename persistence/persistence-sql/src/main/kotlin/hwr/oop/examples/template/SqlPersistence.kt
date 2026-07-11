package hwr.oop.examples.template

import com.zaxxer.hikari.HikariDataSource
import hwr.oop.examples.dominion.GameInstance
import liquibase.Liquibase
import liquibase.Scope
import liquibase.database.DatabaseFactory
import liquibase.database.jvm.JdbcConnection
import liquibase.logging.core.NoOpLogService
import liquibase.resource.ClassLoaderResourceAccessor
import liquibase.ui.LoggerUIService
import org.jetbrains.exposed.v1.jdbc.Database
import javax.sql.DataSource

class SqlPersistence(private val dataSource: DataSource) : DominionPersistence {
	
	constructor(jdbcUrl: String, username: String, password: String) : this(
		HikariDataSource().apply {
			setJdbcUrl(jdbcUrl)
			setUsername(username)
			setPassword(password)
		}
	)
	
	init {
		runLiquibaseMigrations()
		Database.connect(dataSource)
	}
	
	private fun runLiquibaseMigrations() {
		System.setProperty("liquibase.command.update.showSummary", "OFF")
		val scopeAttrs = mapOf(
			Scope.Attr.logService.name to NoOpLogService(),
			Scope.Attr.ui.name to LoggerUIService(),
		)
		Scope.child(scopeAttrs) {
			dataSource.connection.use { connection ->
				val database = DatabaseFactory.getInstance()
					.findCorrectDatabaseImplementation(JdbcConnection(connection))
				Liquibase(
					"db/changelog/db.changelog-master.yaml",
					ClassLoaderResourceAccessor(),
					database
				).update("")
			}
		}
	}

	override fun load(gameId: String): GameInstance {
		TODO("Not yet implemented")
	}

	override fun save(game: GameInstance) {
		TODO("Not yet implemented")
	}

}

