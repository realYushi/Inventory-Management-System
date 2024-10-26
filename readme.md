<!-- Improved compatibility of back to top link: See: https://github.com/othneildrew/Best-README-Template/pull/73 -->

<a id="readme-top"></a>

<!--
*** Thanks for checking out the Best-README-Template. If you have a suggestion
*** that would make this better, please fork the repo and create a pull request
*** or simply open an issue with the tag "enhancement".
*** Don't forget to give the project a star!
*** Thanks again! Now go create something AMAZING! :D
-->

<!-- PROJECT SHIELDS -->
<!--
*** I'm using markdown "reference style" links for readability.
*** Reference links are enclosed in brackets [ ] instead of parentheses ( ).
*** See the bottom of this document for the declaration of the reference variables
*** for contributors-url, forks-url, etc. This is an optional, concise syntax you may use.
*** https://www.markdownguide.org/basic-syntax/#reference-style-links
-->

[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![MIT License][license-shield]][license-url]
[![LinkedIn][linkedin-shield]][linkedin-url]

<!-- PROJECT LOGO -->
<br />
<div align="center">
  <a href="https://github.com/realYushi/Inventory-Management-System">
    <img src="image/logo.webp" alt="Logo" width="80" height="80">
  </a>

<h3 align="center">Inventory Management System</h3>

  <p align="center">
    An advanced tool designed to simplify the complexities of inventory management.
    <br />
    <a href="https://github.com/realYushi/Inventory-Management-System"><strong>Explore the docs »</strong></a>
    <br />
    <br />
    <a href="https://github.com/realYushi/Inventory-Management-System">View Demo</a>
    ·
    <a href="https://github.com/realYushi/Inventory-Management-System/issues/new?labels=bug&template=bug-report---.md">Report Bug</a>
    ·
    <a href="https://github.com/realYushi/Inventory-Management-System/issues/new?labels=enhancement&template=feature-request---.md">Request Feature</a>
  </p>
</div>

<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
        <li><a href="#architecture">Architecture</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#roadmap">Roadmap</a></li>
    <li><a href="#license">License</a></li>
    <li><a href="#contact">Contact</a></li>
    <li><a href="#acknowledgments">Acknowledgments</a></li>
  </ol>
</details>

<!-- ABOUT THE PROJECT -->

https://github.com/user-attachments/assets/3d6fc573-3c93-4331-8179-b140dcfbc6c0

## About The Project

The Inventory Management System is a personal project developed to handle the intricacies of managing inventory across various categories and suppliers. It offers a detailed and intuitive interface for monitoring product details, transactions, and stock levels, ensuring that inventory management is both efficient and straightforward.

This system is particularly useful for small to medium-sized business owners looking to digitize their inventory processes and enhance operational efficiency.

### Architecture

The project follows a layered architecture with JPA/Hibernate integration:

#### Model Layer
- **Entities**: 
  - Product: Core product information management
  - InventoryTransaction: Tracks inventory movements
- **Interfaces**:
  - IProduct: Product entity contract
  - IInventoryTransaction: Transaction entity contract
  - IFinancialSummary: Financial calculations interface
  - IInventorySummary: Inventory status interface

#### Repository Layer
Handles data persistence using JPA/Hibernate:
- **IProductRepository**: 
  - CRUD operations for products
  - Product listing and search
- **IInventoryTransactionRepository**:
  - Transaction recording
  - Historical data management

#### Database Integration
- Uses JPA/Hibernate for ORM
- EntityManagerFactory managed by HibernateUtil
- Persistence unit: "Inventory-Management-Unit"

Key Features:
- Complete CRUD operations for inventory management
- Transaction tracking and history
- Financial calculations and reporting
- Stock level monitoring
- Interface-based design for maintainability

Design Patterns:
- Repository Pattern for data access
- Interface Segregation Principle
- Dependency Injection

<p align="right">(<a href="#readme-top">back to top</a>)</p>

### Built With

- [Java](https://java.com)
- [Maven](https://maven.apache.org/)
- [Hibernate](https://hibernate.org/)
- [JPA](https://jakarta.ee/specifications/persistence/)

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- GETTING STARTED -->

## Getting Started

To get a local copy up and running follow these simple steps.

### Prerequisites

This project uses Maven to manage dependencies. Ensure Maven is installed:

```sh
mvn -v
```

### Installation

1. Clone the repo
   ```sh
   git clone https://github.com/realYushi/Inventory-Management-System.git
   ```
2. Install Maven dependencies
   ```sh
   mvn install
   ```
3. Configure your database connection in `persistence.xml`

4. Run the application using Maven
   ```sh
   mvn exec:java
   ```

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- USAGE EXAMPLES -->

## Usage

The Inventory Management System provides a robust backend for inventory management with the following key features:

### Product Management
- Create, read, update, and delete products
- Track product details including:
  - Product ID
  - Name
  - Quantity
  - Unit
  - Price

### Inventory Transactions
- Record and track inventory movements
- Transaction details include:
  - Transaction ID
  - Product ID
  - Quantity
  - Date
  - Price

### Financial Tracking
- Calculate and monitor:
  - Total sales
  - Total costs
  - Net profit
  - Price adjustments

### Inventory Analytics
- Monitor low stock products
- Track recent transactions
- Generate inventory summaries
- Financial performance metrics

The system uses JPA/Hibernate for reliable data persistence and provides a solid foundation for building inventory management solutions.

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- ROADMAP -->

## Roadmap

- [ ] Add GUI support
- [ ] Implement Database support for data persistence
- [ ] Integrate with external APIs for order management

See the [open issues](https://github.com/realYushi/Inventory-Management-System/issues) for a full list of proposed features (and known issues).

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- LICENSE -->

## License

Distributed under the MIT License. See `LICENSE.txt` for more information.

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- CONTACT -->

## Contact

Yushi Cui - realYushi@gmail.com

LinkedIn: [https://www.linkedin.com/in/yushi-cui-6043aa285/](https://www.linkedin.com/in/yushi-cui-6043aa285/)

Project Link: [https://github.com/realYushi/Inventory-Management-System](https://github.com/realYushi/Inventory-Management-System)

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- ACKNOWLEDGMENTS -->

## Acknowledgments

- [Lanterna](https://github.com/mabe02/lanterna)
- [Maven](https://maven.apache.org/)
- [Gson](https://github.com/google/gson)

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->

[contributors-shield]: https://img.shields.io/github/contributors/realYushi/Inventory-Management-System.svg?style=for-the-badge
[contributors-url]: https://github.com/realYushi/Inventory-Management-System/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/realYushi/Inventory-Management-System.svg?style=for-the-badge
[forks-url]: https://github.com/realYushi/Inventory-Management-System/network/members
[stars-shield]: https://img.shields.io/github/stars/realYushi/Inventory-Management-System.svg?style=for-the-badge
[stars-url]: https://github.com/realYushi/Inventory-Management-System/stargazers
[issues-shield]: https://img.shields.io/github/issues/realYushi/Inventory-Management-System.svg?style=for-the-badge
[issues-url]: https://github.com/realYushi/Inventory-Management-System/issues
[license-shield]: https://img.shields.io/github/license/realYushi/Inventory-Management-System.svg?style=for-the-badge
[license-url]: https://github.com/realYushi/Inventory-Management-System/blob/master/LICENSE.txt
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555
[linkedin-url]: https://www.linkedin.com/in/yushi-cui-6043aa285/
