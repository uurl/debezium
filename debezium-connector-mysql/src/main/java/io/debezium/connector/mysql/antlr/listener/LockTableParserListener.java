/*
 * Copyright Debezium Authors.
 *
 * Licensed under the Apache Software License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */

package io.debezium.connector.mysql.antlr.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.debezium.connector.mysql.antlr.MySqlAntlrDdlParser;
import io.debezium.ddl.parser.mysql.generated.MySqlParser;
import io.debezium.ddl.parser.mysql.generated.MySqlParserBaseListener;

/**
 * Parser listener that is parsing MySQL LOCK TABLE statements.
 */
public class LockTableParserListener extends MySqlParserBaseListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(LockTableParserListener.class);
    private final MySqlAntlrDdlParser parser;

    public LockTableParserListener(MySqlAntlrDdlParser parser) {
        this.parser = parser;
    }

    /*
     * @Override
     * public void enterDropTable(MySqlParser.DropTableContext ctx) {
     * Interval interval = new Interval(ctx.start.getStartIndex(), ctx.tables().start.getStartIndex() - 1);
     * String prefix = ctx.start.getInputStream().getText(interval);
     * ctx.tables().tableName().forEach(tableNameContext -> {
     * TableId tableId = parser.parseQualifiedTableId(tableNameContext.fullId());
     * parser.databaseTables().removeTable(tableId);
     * parser.signalDropTable(tableId, prefix + tableId.toQuotedString('`')
     * + (ctx.dropType != null ? " " + ctx.dropType.getText() : ""));
     * });
     * super.enterDropTable(ctx);
     * }
     */

    @Override
    public void enterLockTables(MySqlParser.LockTablesContext ctx) {
        // LOGGER.warn("enterLock: " + ctx.tableName().fullId());
        ctx.lockTableElement().forEach(table -> {
            LOGGER.warn("enterLockTables: " + table.uid() + " - " + table.tableName());
        });
        super.enterLockTables(ctx);
    }

    @Override
    public void exitLockTables(MySqlParser.LockTablesContext ctx) {
        ctx.lockTableElement().forEach(table -> {
            LOGGER.warn("exitLockTables: " + table.uid() + " - " + table.tableName());
        });
        super.exitLockTables(ctx);
    }

    @Override
    public void enterUnlockTables(MySqlParser.UnlockTablesContext ctx) {
        super.enterUnlockTables(ctx);
    }

    @Override
    public void exitUnlockTables(MySqlParser.UnlockTablesContext ctx) {
        super.exitUnlockTables(ctx);
    }
}
