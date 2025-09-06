# gage-adventure-editor
# Generic Adventure Game Editor (GAGE) 🧭

> A robust, modern desktop application for creating classic text-based adventure games, built with a focus on clean, scalable, and professional software architecture.

This project is a semi-complete, from-scratch game editor built in Java using the Swing GUI toolkit. It serves as a practical exercise in applying core software design principles to build a maintainable and feature-rich desktop application.

![GAGE Main Interface](https://github.com/UncleBrick/gage-adventure-editor/raw/main/images/GAGE-SCREENSHOT-MAIN-PANEL-MDI.PNG)
---

## Key Features ✨

* **Detailed Room Editor:** Create and manage game rooms with a dynamic, data-driven tag system.
* **Comprehensive Object Editor:** Design game items with a clean, tabbed interface for managing a wide array of properties and flags.
* **Dynamic UI Generation:** The UI for flags is generated dynamically from central constants files, perfectly demonstrating the **DRY (Don't Repeat Yourself)** principle.
* **Human-Readable Data:** All game data is saved in a clean, organized JSON format, managed by the Gson library.
* **Professional UI/UX:** Features a consistent look and feel, smart UI controls that prevent user error, and a "warn on close" system to prevent data loss.

---

## Core Design Principles 🏗️

This project was built with a strong emphasis on writing high-quality, professional code. The architecture is guided by the following principles:

* **SOLID Principles:** The entire application is structured around a clean, layered architecture that strictly adheres to the five SOLID principles:
    * **S**ingle Responsibility Principle
    * **O**pen/Closed Principle
    * **L**iskov Substitution Principle
    * **I**nterface Segregation Principle
    * **D**ependency Inversion Principle
* **DRY (Don't Repeat Yourself):** Logic and configuration (such as UI flags) are defined in a single, authoritative place to avoid redundancy and improve maintainability.
* **Clean Code:** The codebase follows strict rules to ensure clarity and professionalism, including **no wildcard imports** and **no magic numbers or strings**.

---

## Built With 🛠️

* **Java 24**
* **Java Swing** (for the Graphical User Interface)
* **Gson** (for JSON data serialization)
* **Git & GitHub** (for version control)
