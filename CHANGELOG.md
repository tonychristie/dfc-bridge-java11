# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/).

## [1.1.0-java11] - 2026-01-24

### Added
- User and group endpoints for REST API parity (#270)
  - `GET /api/v1/users` - List all users
  - `GET /api/v1/users/{name}` - Get user details
  - `GET /api/v1/users/{name}/groups` - Get groups containing user
  - `GET /api/v1/groups` - List all groups
  - `GET /api/v1/groups/{name}` - Get group details including members
- Direct `/health` endpoint returning `{"status": "ok"}` (#270)

### Fixed
- `/api/v1/objects/{objectId}` now correctly resolves names for dm_group, dm_user, and dm_relation objects (#270)
- `/api/v1/groups/{name}` endpoint now properly returns all values of repeating attributes (#276)
- `/api/v1/objects/{id}` endpoint now correctly extracts repeating attributes as arrays (#276)
- `setObjectAttribute` now supports repeating attributes (#294)
