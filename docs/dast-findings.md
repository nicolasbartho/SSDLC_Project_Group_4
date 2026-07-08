# DAST Findings — OWASP ZAP Baseline Scan

Scan run: GitHub Actions run `28929681902`, ZAP `zap-baseline.py` (stable),
target `http://localhost:8080`, 6 URLs crawled.

**Result:** `FAIL-NEW: 0`, `WARN-NEW: 11`, `PASS: 59`. No high-severity
findings; 11 WARN-level items reviewed below.

> Screenshots to add for submission: the GitHub Actions job summary, and
> the `zap-report` artifact (`report_html.html`) opened in a browser,
> before and after the fixes in this document.

## Fixed in code (2+ required by the brief)

| # | ZAP rule | Finding | Fix |
|---|---|---|---|
| 1 | 10038 | Content-Security-Policy header not set on `/`, `/login`, `/register` | Added a restrictive CSP (`default-src 'self'`, `object-src 'none'`, `frame-ancestors 'none'`, ...) in `SecurityConfig.filterChain()` |
| 2 | 10063 | Permissions-Policy header not set | Added `Permissions-Policy: geolocation=(), camera=(), microphone=(), payment=()` via a `StaticHeadersWriter` in `SecurityConfig` |
| 3 | 10054 | Session cookie missing `SameSite` attribute (seen on 401 responses) | Set `server.servlet.session.cookie.same-site: lax` and `http-only: true` in `application.yml` |

### Before / after example — Content-Security-Policy (10038)

**Before** (raw ZAP output, this run):

```
WARN-NEW: Content Security Policy (CSP) Header Not Set [10038] x 5
	http://localhost:8080 (200 OK)
	http://localhost:8080/login (200 OK)
	http://localhost:8080/login?error (200 OK)
	http://localhost:8080/register (200 OK)
	http://localhost:8080/register (200 OK)
```

**Fix** (`SecurityConfig.java`):

```java
.contentSecurityPolicy(csp -> csp.policyDirectives(
        "default-src 'self'; script-src 'self'; style-src 'self' 'unsafe-inline'; " +
        "img-src 'self'; object-src 'none'; frame-ancestors 'none'; " +
        "base-uri 'self'; form-action 'self'"
))
```

**After:** re-run the pipeline and confirm `10038` no longer appears in
`WARN-NEW` in the new `report_html.html` (attach screenshot).

## Reviewed and downgraded (false positive / accepted low priority)

Documented with justification in [`.zap/rules.tsv`](../.zap/rules.tsv)
rather than fixed, per the assignment's "explain clearly why a finding is
a false positive or low priority" option:

| ZAP rule | Finding | Why |
|---|---|---|
| 10031 | User Controllable HTML Element Attribute (potential XSS) on `/register` | Thymeleaf's `th:field` HTML-escapes all output by default; the codebase contains no `th:utext` (raw/unescaped output), so reflected input cannot break out of the attribute context. |
| 10027 | Suspicious Comments on `/login` | Manual review of `login.html`/`register.html` found no debug/TODO/FIXME comments; matches generic HTML boilerplate. |
| 10094 | Base64 Disclosure | Matches the CSRF token / session identifier, which are expected opaque values present on every authenticated-flow page, not a leaked secret. |
| 10111 / 10112 | Authentication Request / Session Management Response Identified | Informational confirmations that ZAP recognised the login flow — not vulnerabilities. |
| 90004 | Cross-Origin-Embedder-Policy Header Missing | Relevant for cross-origin resource isolation (e.g. `SharedArrayBuffer`); this app has no such usage. Accepted as low priority for this assignment's scope. |
| 90005 | Sec-Fetch-Dest Header is Missing | This is a *request* header sent by the browser, not something the server configures. |
| 10049 | Non-Storable Content | Informational; the affected routes (`/login`, `/register`, missing `robots.txt`/`sitemap.xml`) carry no sensitive data. |

## Next scan

After merging the fixes above, re-run the `Secure CI/CD Pipeline` workflow
and update the counts at the top of this document (target: `WARN-NEW`
should drop from 11 to ≤ 8, with the three fixed rules no longer appearing
and only the seven documented/accepted ones remaining as `INFO`).
