**Project Overview** 
-
Assessment project for Digital Tolk. Includes database layer and token based authentication.

**Setup Instructions**   
-
- Postman
- Java should be installed
- Gradle should be installed
- Postgresql either installed or running in docker
- Configure database connection
- Use provided queries(resources/db_scripts) to create tables and indexes
- Just run the application from IDE

**How to use**
-
- Create token using /token endpoint with in memory user:
    username:mali password:test
- Use response token for other requests

**Design Choices**
-
*Database Layer*

- Translation table to store all translations with content
- Uses an auto-incremented id as the primary key for simple joins and references
- Used @ElementCollection for tags rather than a separate entity with relationships
- CollectionTable creates a clean join table automatically
- updatedAt defaults to current time on creation (important for cache updates)
- Added indexes on columns that are used in queries

*Authentication*

- As required JWT authentication implemented
- Implemented in-memory user store for simplicity in this prototype
- All routes except /token are authenticated

*Cache Architecture*

- In-memory cache implemented using ConcurrentHashMap for thread-safe operations
- Organized by locale for efficient partitioning of translation sets
- Stores complete translations per locale along with last update timestamp
 
Cache Strategy:
- Lazy Loading: Only loads translations for a locale when first requested
- Partial Refresh: When cache exists, only fetches translations updated since last cache refresh
- Write-Through: Updates cache immediately when new translations are added/updated

*Performance Considerations*

Optimizations:
- Avoids full cache reloads by tracking update timestamps
- Minimizes database queries through partial refresh mechanism
- Concurrent access safe for high-throughput scenarios

Memory Efficiency:
- Stores only active locales in memory
- Flattens translation structure to simple key-value pairs for cache storage

Cache Invalidation

Time-Based:
- Tracks last update time for each locale's translations
- Only queries for changes since last cache update

On-Demand:
- Entire locale cache can be refreshed by calling getFreshTranslations()
- Individual translation updates automatically refresh the affected locale cache

**API Design**
-
Restful Structure

- Base path /api/translations clearly identifies the resource
- Follows REST conventions for CRUD operations

Pagination & Filtering

- Implements pagination with defaults (page=0, size=20)
- Enforces maximum page size (100 records) to prevent overfetching
- Supports filtering by multiple criteria (tags, keys, content)
- Dedicated endpoint for locale-specific exports
- Returns flat key-value structure optimized for client consumption
- Leverages the cache system for performance

DTO Patterns

- Uses separate DTOs (TranslationDto) for input
- Prevents entity overexposure