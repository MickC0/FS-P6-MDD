# MDD Frontend Setup Guide

Ce guide explique comment installer, configurer et lancer l’application frontend Angular de MDD (Monde de Dév).

---

## 🚀 À propos du projet

MDD est un réseau social pour développeurs, permettant de :
- S’abonner à des topics (JavaScript, Java, Python, Web3, …)
- Consulter un fil d’actualité personnalisé
- Rédiger des articles (posts)
- Commenter les articles de la communauté

Le frontend Angular se connecte à l’API Spring Boot (port `3002`, contexte `/api`).

---

## 🛠 Tech Stack

| Outil                   | Version / Détails                |
|-------------------------|----------------------------------|
| **Framework**           | Angular CLI v19.0.7              |
| **Langage**             | TypeScript / ES2023              |
| **Node.js**             | 22.14.0                          |
| **npm**                 | 11.2.0                           |
| **UI Library**          | Angular Material                 |
| **Gestionnaire d’état** | RxJS                             |
| **Styles globaux**      | SCSS                             |

---

## 📋 Prérequis

Avant de commencer, assurez-vous d’avoir installé :
- **Node.js** 22.14.0 (`node -v`)
- **npm** 11.2.0 (`npm -v`)
- **Angular CLI** 19+ (`npm install -g @angular/cli`)
- Un **serveur backend** MDD opérationnel sur `http://localhost:3002/api`

---

## 🔧 Installation & Configuration

#### 1️⃣ Clone the Project
```sh
git clone <repository_url>
cd front
```

#### 1️⃣ Installation des dépendances
```sh
npm install
```
## ▶️ Lancement en mode développement

```sh
npm run start
```

## 📦 Compilation & déploiement

```sh
npm run build
```

Les artefacts sont générés dans dist/front.

Déployez ce dossier sur votre serveur statique (Nginx, Apache…).
