# Computer Management Service
## TODOs

- [ ] Pagination and Filtering
- [ ] DTOs without sensitive data
- [ ] Controller Tests
- [ ] Add Swagger or sth like that
- [ ] Authentication
- [ ] Authorisation for admin endpoints (maybe separate admin endpoints)

## Initial Structure Ideas
* Počítače
  * Konfigurace - Typ
  * Lokace
  * místnost
* Místnosti
  * Lokace
  * Fakulta
  * Gps 
* Fakulta


## CRUD + API 
* Public endpoints (used by Reservation/Frontend):
  * Get all PCs (with optional filters: by faculty, by room, by type).
  * Get details of a single PC.
  * Get list of types, faculties, rooms.
* Admin endpoints:
  * Create/update/delete PCs.
  * Create/update/delete types.
  * Create/update/delete rooms.

## Use Cases
* Pridani PC
* Odstranění PC
  * Ověření že není žádná rezervace na PC 
  * Odstranění PC
* Pridani/Odstranění místnosti 


## Integration notes
* Vrat vsechny PC per fakultu / mistnost