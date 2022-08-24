# 2. Omit Quarkus neo4j extension

Date: 2022-07-18

## Status

Accepted

## Context

Quarkus provides an extension to work with Neo4j databases. However, given that we are working with an embedded neo4j instance and 
that the extension uses the less powerful neo4j Driver approach renders the extension useless for this usecase.

## Decision

Quarkus neo4j extension will not be used

## Consequences

* Configure the dependencies to neo4j graphdatabse as implmentation dependency
* We may miss some features that are offered b the extension. However, at this point there is no such known feature. 