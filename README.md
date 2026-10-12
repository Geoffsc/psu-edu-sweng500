# SWENG500 Car Pool

> **Archived capstone project.** This repository preserves the source and supporting material for a historical master's capstone project. It is provided for study and reference, not as a maintained or deployable carpool service.

The project was developed for Penn State's SWENG500 course by Eric Borisow, Geoff Scholl, and Ty Huynh. The team documentation describes a mobile carpool application for members of an organization or community: users could find and create trips, coordinate drivers and passengers, and work with saved locations. The original project space was hosted on [Assembla](https://app.assembla.com/spaces/psu-edu-sweng500/).

## What is in this repository

The repository contains two BlackBerry mobile client projects and a Java web-service backend:

- [rimapp/](rimapp/) — the primary BlackBerry client, written against the BlackBerry Java APIs. Its screens support signing in, searching for trips, creating trips, viewing trip details, joining trips, and managing options. The client calls the backend using generated JSR-172 SOAP stubs.
- [bbapp/](bbapp/) — an additional BlackBerry MIDlet prototype, separate from the main `rimapp` application.
- [webapp/](webapp/) — the Java web application and SOAP services. Apache Axis2 publishes the carpool and security services; Spring wires the application components, and Hibernate-backed service classes access users, trips, organizations, and addresses.
- [resources/db scripts/](resources/db%20scripts/) — SQL schema and sample-data scripts. The numbered schema files represent differing schema snapshots; check the matching Hibernate mappings before choosing one.
- [docs/](docs/) — requirements, analysis/design, test plan, presentation, project planning, and the team's development notes.

## Capabilities represented in the code

The mobile and service code covers these main workflows:

- Register and authenticate users.
- Search trips and retrieve trip details, including dates, start/end locations, organization, and participants.
- Create or update trips and add or update trip participants.
- Search for similar trips and locations near a latitude/longitude within a distance.
- Retrieve user profiles and maintain saved places/addresses.

The documentation records the team's choice of a BlackBerry-specific client and the MGMaps mapping approach after evaluating mobile mapping options. The checked-in tree also includes location/directions-related screens and location services. This is a description of the archived scope, not a claim that every feature works against a currently available server.

## Architecture

```text
BlackBerry client (rimapp)
        | SOAP over HTTP (JSR-172 client stubs)
        v
Apache Axis2 services (webapp)
        | Spring-managed service layer
        v
Hibernate / relational database
```

Service contracts and generated client/server bindings are included in the repository. The web service definitions are in [webapp/src/resources/](webapp/src/resources/) and under the deployed service descriptors in [webapp/WebContent/WEB-INF/services/](webapp/WebContent/WEB-INF/services/). Generated BlackBerry client code is under [rimapp/src/edu/psu/sweng500/carpool/device/services/generated/](rimapp/src/edu/psu/sweng500/carpool/device/services/generated/).

## Historical technology stack

- Java ME / JSR-172 and Research In Motion (RIM) BlackBerry Java APIs for the mobile clients
- Apache Ant build files; the web build targets Java 5 bytecode
- Apache Axis2 SOAP services (the checked-in build properties refer to Axis2 1.3)
- Spring Framework, Hibernate 3, and a MySQL-oriented relational schema
- Apache Tomcat deployment configuration (the checked-in local configuration refers to Tomcat 5)

These are historical dependencies, not recommended versions for a new application. The repository does not provide a modern, reproducible build environment or a currently supported server deployment.

## Building and running

There is no turnkey run command. The Ant files and local property files are tied to the original development machines and hosting setup; the server build expects legacy Axis2, application-server, and library installations. The BlackBerry client likewise requires a compatible historical RIM SDK/toolchain and an available SOAP backend.

For investigation, start with:

1. Review [docs/](docs/) for the original goals, design decisions, and test plan.
2. Inspect [webapp/build.xml](webapp/build.xml), [webapp/build.properties](webapp/build.properties), [webapp/local-dev.properties](webapp/local-dev.properties), and [webapp/service-local-dev.properties](webapp/service-local-dev.properties) to understand the old Ant configuration. Paths and required properties may be missing or machine-specific.
3. Review [webapp/WebContent/WEB-INF/settings/](webapp/WebContent/WEB-INF/settings/) and the Hibernate mappings alongside the SQL scripts before setting up a database.
4. Build or run only in an isolated environment after replacing endpoint URLs, credentials, database settings, and other environment-specific values.

The service stubs contain historical endpoint addresses, and some local configuration and sample data contain credentials or personal data. Do not connect to the old endpoints, reuse embedded credentials, or load the seed data into a live system. Sanitize these values before testing or redistributing the code.

## Archive and security notes

- This is legacy academic software; some files are prototypes, generated artifacts, or incomplete implementations. Current compatibility and test status are unknown.
- The archived application is **not suitable for production**. Review authentication, password handling, transport security, logging, input validation, dependencies, and stored sample data before any reuse.
- The requirements, design, and test documents describe project intent and planned verification; they should not be interpreted as proof that every requirement passed or every feature was completed.
- The repository includes a GPL notice in service source files. Review file-level notices and the original project terms before redistributing the whole archive.

## Further reading

- [Group documentation](docs/sweng500-group_documentation.doc) — development history, tool/API choices, implementation notes, and lessons learned.
- [Requirements](docs/requirement/sweng-500-requirements.doc)
- [Analysis and design](docs/design/sweng500-analysis_and_design-final_doc.doc)
- [Test plan](docs/test%20plan/sweng-500-Carpool-TestPlan.doc)
- [Database scripts](resources/db%20scripts/)