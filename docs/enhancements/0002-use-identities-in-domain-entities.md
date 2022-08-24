# use identities in domain entities

Date: 2022-07-22

## Context

Components are currently matched by their respective name - which may be ambiguous, 
e.g. whith evolved components having the same name as their origin. When creating relationships in the graph model, 
components should be matched by theier exact id. 

## Consequences
- Introduce CRUD semantics on repository implementations
- Adhere more strictly to DDD principles
