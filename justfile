mvnw := if os() == "windows" { "./mvnw.cmd" } else { "./mvnw" }

default:
    just --list

# Build all modules including tests, and install to local Maven repository
build:
    {{ mvnw }} clean install

# Run all tests across all modules
test:
    {{ mvnw }} verify

# Compile all modules and test sources without running tests
compile:
    {{ mvnw }} clean test-compile

# Run the Spring Boot service (requires: just build)
service:
    {{ mvnw }} spring-boot:run -pl applications/service

# Run the CLI, passing any extra arguments to the CLI code (requires: just build)

# Usage: just cli [args...]
cli *args:
    java -jar applications/cli/target/cli-0.1.0.jar {{ args }}

# Start the local PostgreSQL database in the background (requires: Docker)
start-db:
    docker compose --project-directory tools/localenv/postgresql up -d

# Stop the local PostgreSQL database
stop-db:
    docker compose --project-directory tools/localenv/postgresql down

# Stop the local PostgreSQL database and delete all data
discard-db:
    docker compose --project-directory tools/localenv/postgresql down -v

# Run PITest mutation testing on the core module
mutation-test:
    {{ mvnw }} -pl core org.pitest:pitest-maven:mutationCoverage

# Open the latest PITest mutation testing report in the default browser
mutation-test-open:
    {{ if os() == "windows" { "start core/target/pit-reports/index.html" } else if os() == "macos" { "open core/target/pit-reports/index.html" } else { "xdg-open core/target/pit-reports/index.html" } }}
