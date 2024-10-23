# Use the latest Liquibase image as the base image
FROM liquibase/liquibase:latest

# Add your Liquibase changelog file to the /liquibase/changelog directory
COPY ./changelog/. /liquibase/changelog

# Command to execute when the container starts
# CMD ["update", "--help"]
CMD liquibase --sql-log-level=INFO --log-level=INFO update && tail -f /dev/null