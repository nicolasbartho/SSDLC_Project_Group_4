---

---
---

## 📋 **Kanban Board** *(Template pour GitHub Projects ou Trello)*

### **Option 1: GitHub Projects (Recommandé)**
1. **Créer un projet** dans votre repo GitHub:
   - Allez dans **`Projects`** > **`New Project`** > **`Board`** (Kanban).
   - Nom: `SSDLC Project - Group 01`.

2. **Colonnes à créer**:
   | **To Do**               | **In Progress**          | **Review**               | **Done**               |
   |-------------------------|--------------------------|--------------------------|------------------------|
   | [Initialiser le repo]   | [DFD + STRIDE]           | [SAST Configuration]     | [Repo structure]       |
   | [Prototype backend]     | [Frontend Login Page]    | [DAST Configuration]     | [README.md]            |
   | [User/Role Models]      | [Password Hashing]      | [Fix SAST Issues]        |                        |
   | [Book CRUD Endpoints]   | [Input Validation]      | [Fix DAST Issues]        |                        |
   | [Loan Request Logic]    | [Error Handling]        | [CI/CD Pipeline Draft]   |                        |
   | [Logging Setup]         | [Admin Dashboard]       | [Security Report]        |                        |
   | [SAST Setup]            | [Unit Tests]             | [Presentation Slides]    |                        |
   | [DAST Setup]            | [Integration Tests]      | [Live Demo Rehearsal]    |                        |

3. **Ajouter des issues** pour chaque tâche et les assigner aux membres.

---

### **Option 2: Kanban en Markdown** *(si vous préférez un fichier dans le repo)*
Créez un fichier `KANBAN.md` avec ce contenu:

```markdown
# 📋 **Kanban - SSDLC Project Group 01**
*Mise à jour: 08/07/2026*

---

## 🟡 **To Do** *(À faire)*
| Tâche                     | Assigné à          | Priorité | Échéance   |
|---------------------------|--------------------|----------|------------|
| Initialiser le repo Git   | Nicolas            | ⭐⭐⭐     | 05/07 |
| Créer le DFD              | Aurélien           | ⭐⭐⭐     | 06/07      |
| Configurer Spring Security| Rémi               | ⭐⭐⭐     | 06/07      |
| Mettre en place H2 DB     | Seydina            | ⭐⭐       | 07/07      |
| STRIDE Analysis           | Aurélien           | ⭐⭐⭐     | 07/07      |
| Prototype Login Page      | Nicolas            | ⭐⭐⭐     | Aujourd'hui      |
| Configurer SonarQube      | Rémi               | ⭐⭐       | Aujourd'hui      |
| Configurer OWASP ZAP      | Seydina            | ⭐⭐       | Aujourd'hui      |

---

## 🟠 **In Progress** *(En cours)*
| Tâche                     | Assigné à | % Complété | Blocker |
|---------------------------|-----------|------------|---------|
| *Aucune pour l'instant*   | -         | -          | -       |

---
## 🟢 **Review** *(En revue)*
| Tâche                     | Assigné à | Revu par | Statut |
|---------------------------|-----------|----------|--------|
| *Aucune pour l'instant*   | -         | -        | -      |

---
## ✅ **Done** *(Terminé)*
| Tâche                     | Assigné à | Date      | Lien PR/Commit |
|---------------------------|-----------|-----------|----------------|
| *Aucune pour l'instant*   | -         | -         | -              |

---
## 📌 **Backlog** *(Si temps supplémentaire)*
- Ajouter un système de notifications par email
- Implémenter un rate limiting pour les tentatives de login
- Ajouter des tests de charge
