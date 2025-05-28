# SpaceTravel Project (FINAL)

## Task #1 - Add Ticket Entity

Add a new entity - `Ticket`. Write Hibernate mapping for working with this entity.

Note that this entity has one-to-many mappings. For example, one client can have many tickets. Accordingly, the `Ticket` entity should not have a client identifier, but rather a field of type `Client` with correctly configured mapping. The same applies to the starting and destination planets.

## Task #2 - Add CRUD Service for Tickets

Write a CRUD service for working with tickets.

Verify that your code works correctly. In particular, check the following scenarios:

- Cannot save a ticket for a non-existent or null client
- Cannot save a ticket for a non-existent or null planet

## Implementation Notes

- Ensure proper Hibernate entity relationships are established
- Use appropriate JPA annotations for entity mapping
- Implement validation for required fields and relationships
- Test edge cases and error scenarios thoroughly