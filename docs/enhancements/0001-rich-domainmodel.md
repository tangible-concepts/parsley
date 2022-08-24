# Rich domainmodel

Date: 2022-07-22

## Context

The domainmodel is too much influenced by the input model (onlinewardleymaps) and the graph model of the database. 
It should be totally independent. e.g. Inertia is an entity on its own, referencing the movement of an evolved component 
(not beeing a property of any component)

## Consequences
Redesign the domainmodel, rafactoring the whole application

