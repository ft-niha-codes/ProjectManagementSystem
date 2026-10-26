package com.projectmanagement.projecttaskmanagementsystem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DatabaseSchemaFix implements ApplicationRunner {

    private static final Logger logger =
            LoggerFactory.getLogger(DatabaseSchemaFix.class);

    private final JdbcTemplate jdbcTemplate;

    public DatabaseSchemaFix(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(ApplicationArguments args) {
        logger.info("Checking project_team_members table...");

        jdbcTemplate.execute("""
            CREATE TABLE IF NOT EXISTS project_team_members (
                team_id BIGINT NOT NULL,
                team_member_id BIGINT NOT NULL,
                PRIMARY KEY (team_id, team_member_id),
                CONSTRAINT fk_ptm_team
                    FOREIGN KEY (team_id)
                    REFERENCES project_teams(id)
                    ON DELETE CASCADE,
                CONSTRAINT fk_ptm_member
                    FOREIGN KEY (team_member_id)
                    REFERENCES team_members(id)
                    ON DELETE CASCADE
            )
            """);

        logger.info(
                "Successfully checked/created project_team_members table."
        );
    }
}package com.projectmanagement.projecttaskmanagementsystem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DatabaseSchemaFix implements ApplicationRunner {

    private static final Logger logger =
            LoggerFactory.getLogger(DatabaseSchemaFix.class);

    private final JdbcTemplate jdbcTemplate;

    public DatabaseSchemaFix(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(ApplicationArguments args) {
        logger.info("Checking project_team_members table...");

        jdbcTemplate.execute("""
            CREATE TABLE IF NOT EXISTS project_team_members (
                team_id BIGINT NOT NULL,
                team_member_id BIGINT NOT NULL,
                PRIMARY KEY (team_id, team_member_id),
                CONSTRAINT fk_ptm_team
                    FOREIGN KEY (team_id)
                    REFERENCES project_teams(id)
                    ON DELETE CASCADE,
                CONSTRAINT fk_ptm_member
                    FOREIGN KEY (team_member_id)
                    REFERENCES team_members(id)
                    ON DELETE CASCADE
            )
            """);

        logger.info(
                "Successfully checked/created project_team_members table."
        );
    }
}