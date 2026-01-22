# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/).

## [Unreleased]

### Added
- User and group endpoints for REST API parity
  - `GET /api/v1/users` - List all users
  - `GET /api/v1/users/{name}` - Get user details
  - `GET /api/v1/users/{name}/groups` - Get groups containing user
  - `GET /api/v1/groups` - List all groups
  - `GET /api/v1/groups/{name}` - Get group details including members
- Direct `/health` endpoint returning `{"status": "ok"}` (previously redirected to actuator)
- `UserNotFoundException` and `GroupNotFoundException` for proper error handling
- `UserInfo` and `GroupInfo` model classes
- `UserGroupService` interface and `UserGroupServiceImpl` implementation

### Fixed
- `/api/v1/objects/{objectId}` now correctly resolves names for dm_group, dm_user, and dm_relation objects using type-specific name attributes
- Objects without permission support (non-sysobjects) no longer fail when accessed via `/api/v1/objects/{objectId}`
- `/api/v1/groups/{name}` endpoint now properly returns `users_names` and `groups_names` repeating attributes using `getValueCount()` + `getRepeatingString()` pattern
- `/api/v1/objects/{id}` endpoint now correctly extracts repeating attributes as arrays instead of single values
