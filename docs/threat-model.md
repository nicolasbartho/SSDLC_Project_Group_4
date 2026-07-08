# Threat Model – Mini Library Management Web Application

## 1. Data Flow Diagram (description)

External entities:
- **User** (registered borrower)
- **Librarian/Admin**

Processes:
- **P1 – Auth Service** (register/login)
- **P2 – Book Catalogue Service** (list/search/CRUD)
- **P3 – Borrow Request Service** (create/approve/reject)

Data stores:
- **DS1 – User DB** (credentials, roles)
- **DS2 – Book DB** (catalogue)
- **DS3 – Borrow Request DB**

Trust boundaries:
- **TB1**: Browser ↔ Application server (untrusted network / public internet)
- **TB2**: Application server ↔ Database (internal network)
- **TB3**: User-role vs Librarian-role privilege boundary inside the app

> A visual DFD (drawn in draw.io / Mermaid) should be exported to
> `docs/dfd.png` and referenced here for the final submission.

## 2. STRIDE analysis

| DFD element | STRIDE category | Threat description | Risk level | Proposed mitigation |
|---|---|---|---|---|
| Login form (P1, TB1) | Spoofing | Attacker submits credential-stuffed/brute-forced logins to impersonate a user | High | Strong password hashing (BCrypt), account lockout / rate limiting, audit logging of failed logins |
| User DB (DS1) | Information Disclosure | Password hashes or emails exfiltrated via SQL injection or DB compromise | High | Parameterized queries via JPA, least-privilege DB account, encryption at rest |
| Borrow Request Service (P3) | Tampering | A user modifies the request payload to approve their own borrow request | High | Server-side RBAC (`@PreAuthorize`), never trust client-supplied status field |
| Admin book management (P2, TB3) | Elevation of Privilege | A regular user calls `/api/admin/books` directly, bypassing the UI | High | URL + method-level RBAC enforced server-side, deny-by-default rule |
| Registration endpoint (P1) | Repudiation | A user denies having registered/borrowed a book after a dispute | Medium | Security audit log (username + timestamp) for registration, login, and admin actions |
| Search endpoint (P2) | Tampering / Injection | Malicious input in the search query attempts SQL/NoSQL injection | Medium | Bean Validation on input length/pattern + JPA parameterized derived queries |
| Session cookie (TB1) | Spoofing / Info Disclosure | Session hijacking via XSS or missing `Secure`/`HttpOnly` flags | Medium | `HttpOnly`, `Secure`, `SameSite` cookies; CSP header; output encoding via Thymeleaf |
| Error responses (all processes) | Information Disclosure | Stack traces or internal exception details returned to the client | Medium | Centralised `GlobalExceptionHandler` returning generic messages + correlation id |
| Book catalogue write ops (P2) | Denial of Service | Librarian account compromised is used to mass-delete books | Low–Medium | Audit logging, ability to restore from backups, rate limiting on admin endpoints |

## 3. Top 3 prioritized threats

1. **Elevation of Privilege on admin endpoints** – highest impact (full catalogue/borrow-request compromise); mitigated via server-side RBAC at both URL and method level (defence in depth).
2. **Spoofing via credential attacks on login** – directly compromises user accounts; mitigated via BCrypt hashing, generic error messages (no user enumeration), and audit logging.
3. **Tampering with borrow-request approval flow** – a User forging an "approved" status; mitigated by never accepting a client-supplied status and instead deriving it server-side from the authenticated principal's role.

## 4. Notes for the team

- Extend this table with the remaining scenarios found in the DAST/SAST phase.
- Keep this document updated as new endpoints are added — threat modeling is meant to evolve with the design, not be a one-off exercise.
